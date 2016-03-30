package com.zxf.joke.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Bind;
import com.zxf.joke.R;
import com.zxf.joke.data.entity.JokeText;
import com.zxf.joke.presenter.JokeTextPresenter;
import com.zxf.joke.ui.adapter.JokeTextAdapter;
import com.zxf.joke.ui.view.IJokeView;
import java.util.List;

/**
 * Created by zhangman on 16/3/29 14:30.
 * Email: zhangman523@126.com
 */
public class JokeTextFragment extends BaseSwipeRefreshFragment<JokeTextPresenter>
    implements IJokeView<JokeText>, JokeTextAdapter.OnJokeTextItemClick {

  @Bind(R.id.recyclerView) RecyclerView recyclerView;
  private JokeTextAdapter mAdapter;
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
    mAdapter = new JokeTextAdapter();
    mAdapter.setOnJokeTextItemClick(this);
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
  }

  @Override protected void initPresenter() {
    mPresenter = new JokeTextPresenter(this);
  }

  @Override protected int getLayout() {
    return R.layout.fragment_joke;
  }

  @Override protected void onRefreshStarted() {
    mPresenter.getData();
  }

  @Override protected boolean prepareRefresh() {
    return super.prepareRefresh();
  }

  @Override public void showEmptyView() {
    Snackbar.make(recyclerView, "段子在路上...", Snackbar.LENGTH_SHORT).show();
  }

  @Override public void showErrorView(Throwable throwable) {
    throwable.printStackTrace();
    final Snackbar errorSnack = Snackbar.make(recyclerView, R.string.error_index_load,Snackbar.LENGTH_INDEFINITE);
    errorSnack.getView().setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        errorSnack.dismiss();
        onRefreshStarted();
      }
    });
    errorSnack.show();
  }

  @Override public void OnItemClick(int position) {
    // TODO: 16/3/30
  }

  @Override public void OnShareClick(int position) {

  }

  @Override public void fillData(List<JokeText> data) {
    mAdapter.update(data);
  }

  @Override public void appendMoreDataToView(List<JokeText> data) {
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
}
