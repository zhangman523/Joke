package com.zxf.joke.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import butterknife.Bind;
import com.zxf.joke.R;
import com.zxf.joke.data.entity.JokeImage;
import com.zxf.joke.presenter.JokeImagePresenter;
import com.zxf.joke.ui.adapter.JokeImageAdapter;
import com.zxf.joke.ui.view.IJokeView;
import com.zxf.joke.utils.OkhttpUtils;
import java.io.File;
import java.util.List;
import rx.Subscriber;

/**
 * Created by zhangman on 16/3/31 10:28.
 * Email: zhangman523@126.com
 */
public class JokeImageFragment extends BaseSwipeRefreshFragment<JokeImagePresenter>
    implements IJokeView<JokeImage>, JokeImageAdapter.IOnItemClickListener {
  @Bind(R.id.recyclerView) RecyclerView recyclerView;

  private JokeImageAdapter mAdapter;
  /**
   * the flag of has more data or not
   */
  private boolean mHasMoreData = true;

  /**
   * the flag to district whether scroll bottom load more data or not
   * default is load more data
   */
  boolean isLoadMore = true;
  private ProgressDialog progressDialog;

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    initViews();
  }

  private void initViews() {
    final LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
    recyclerView.setLayoutManager(layoutManager);
    mAdapter = new JokeImageAdapter(this);
    mAdapter.setIOnItemClickListener(this);
    recyclerView.setAdapter(mAdapter);
    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        boolean isBottom =
            layoutManager.findLastCompletelyVisibleItemPosition() >= mAdapter.getItemCount() - 4;
        if (!mSwipeRefreshLayout.isRefreshing() && isBottom && mHasMoreData && isLoadMore) {
          showRefresh();
          mPresenter.getData();
        } else if (!mHasMoreData) {
          hasNoMoreData();
        }
      }
    });
    mPresenter.getData();
  }

  @Override protected void onRefreshStarted() {
    mPresenter.getLastJoke();
  }

  @Override protected void initPresenter() {
    mPresenter = new JokeImagePresenter(this);
  }

  @Override protected int getLayout() {
    return R.layout.fragment_joke;
  }

  @Override public void showEmptyView() {
    Snackbar.make(recyclerView, "段子在路上...", Snackbar.LENGTH_SHORT).show();
  }

  @Override public void showErrorView(Throwable throwable) {
    throwable.printStackTrace();
    final Snackbar errorSnack =
        Snackbar.make(recyclerView, R.string.error_index_load, Snackbar.LENGTH_INDEFINITE);
    errorSnack.getView().setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        errorSnack.dismiss();
        onRefreshStarted();
      }
    });
    errorSnack.show();
  }

  @Override public void fillData(List<JokeImage> data) {
    mAdapter.update(data);
  }

  @Override public void appendMoreDataToView(List<JokeImage> data) {
    mAdapter.loadMoreData(data);
  }

  @Override public void hasNoMoreData() {
    mHasMoreData = false;
    Snackbar.make(recyclerView, "没有跟多段子了", Snackbar.LENGTH_LONG)
        .setAction("返回顶部", new View.OnClickListener() {
          @Override public void onClick(View v) {
            (recyclerView.getLayoutManager()).smoothScrollToPosition(recyclerView, null, 0);
          }
        })
        .show();
  }

  @Override public void OnItemClick(int position) {

  }

  @Override public void OnShareClick(final int position) {
    progressDialog = ProgressDialog.show(mActivity, "分享中...", null, false, false);
    OkhttpUtils.downloadFile(mAdapter.getItem(position).url).subscribe(new Subscriber<String>() {
      @Override public void onCompleted() {

      }

      @Override public void onError(Throwable e) {
        e.printStackTrace();
      }

      @Override public void onNext(String s) {
        progressDialog.dismiss();
        shareImage(s);
      }
    });
  }

  private void shareImage(String url) {
    String imagePath = url;
    //由文件得到uri
    Uri imageUri = Uri.fromFile(new File(imagePath));
    Log.d("share", "uri:" + imageUri);
    Intent shareIntent = new Intent();
    shareIntent.setAction(Intent.ACTION_SEND);
    shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
    shareIntent.setType("image/*");
    startActivity(Intent.createChooser(shareIntent, "分享到"));
  }
}
