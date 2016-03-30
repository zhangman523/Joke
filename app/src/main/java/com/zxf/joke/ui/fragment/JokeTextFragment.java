package com.zxf.joke.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    initViews();
  }

  private void initViews() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
    recyclerView.setLayoutManager(layoutManager);
    mAdapter = new JokeTextAdapter();
    mAdapter.setOnJokeTextItemClick(this);
    recyclerView.setAdapter(mAdapter);
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

  @Override public void OnItemClick(int position) {
    // TODO: 16/3/30
  }

  @Override public void OnShareClick(int position) {

  }

  @Override public void fillData(List<JokeText> data) {
    mAdapter.update(data);
  }

  @Override public void appendMoreDataToView(List<JokeText> data) {
    
  }

  @Override public void hasNoMoreData() {

  }
}
