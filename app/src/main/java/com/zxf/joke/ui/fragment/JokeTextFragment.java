package com.zxf.joke.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.Bind;
import com.zxf.joke.R;
import com.zxf.joke.presenter.JokeTextPresenter;
import com.zxf.joke.ui.view.IJokeView;

/**
 * Created by zhangman on 16/3/29 14:30.
 * Email: zhangman523@126.com
 */
public class JokeTextFragment extends BaseSwipeRefreshFragment<JokeTextPresenter>
    implements IJokeView {

  @Bind(R.id.recyclerView) RecyclerView recyclerView;

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    initViews();
  }

  private void initViews() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
    recyclerView.setLayoutManager(layoutManager);
  }

  @Override protected void initPresenter() {
    mPresenter = new JokeTextPresenter(this);
  }

  @Override protected int getLayout() {
    return R.layout.fragment_joke;
  }

  @Override protected void onRefreshStarted() {
    // TODO: 16/3/29  getData
  }

  @Override protected boolean prepareRefresh() {
    return super.prepareRefresh();
  }

  @Override public void showEmptyView() {

  }

  @Override public void showErrorView(Throwable throwable) {
    throwable.printStackTrace();
  }
}
