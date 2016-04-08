package com.zxf.joke.ui.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import butterknife.Bind;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.zxf.joke.R;
import com.zxf.joke.data.entity.JokeImage;
import com.zxf.joke.presenter.JokeImagePresenter;
import com.zxf.joke.ui.adapter.JokeImageAdapter;
import com.zxf.joke.ui.view.IJokeView;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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

      @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        switch (newState) {
          case RecyclerView.SCROLL_STATE_SETTLING:
            //Log.i("Main","用户在手指离开屏幕之前，由于滑了一下，视图仍然依靠惯性继续滑动");
            Glide.with(JokeImageFragment.this).pauseRequests();
            //刷新
            break;
          case RecyclerView.SCROLL_STATE_IDLE:
            //Log.i("Main", "视图已经停止滑动");
            Glide.with(JokeImageFragment.this).resumeRequests();
            break;
          case RecyclerView.SCROLL_STATE_DRAGGING:
            //Log.i("Main","手指没有离开屏幕，视图正在滑动");
            Glide.with(JokeImageFragment.this).resumeRequests();
            break;
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
    Observable.create(new Observable.OnSubscribe<File>() {
      @Override public void call(Subscriber<? super File> subscriber) {
        try {
          File file = Glide.with(mActivity)
              .load(mAdapter.getItem(position).url)
              .downloadOnly(500, 500)
              .get();
          subscriber.onNext(file);
          subscriber.onCompleted();
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (ExecutionException e) {
          e.printStackTrace();
        }
      }
    }).map(new Func1<File, String>() {
      @Override public String call(File file) {
        return file.getPath();
      }
    }).subscribeOn(Schedulers.io())// 指定 subscribe() 发生在 IO 线程
        .observeOn(AndroidSchedulers.mainThread())// 指定 Subscriber 的回调发生在主线程
        .subscribe(new Subscriber<String>() {
          @Override public void onCompleted() {

          }

          @Override public void onError(Throwable e) {
            e.printStackTrace();
          }

          @Override public void onNext(String s) {
            Log.e("--path---", s);
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
