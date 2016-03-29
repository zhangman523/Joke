package com.zxf.joke.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.zxf.joke.R;
import com.zxf.joke.presenter.JokeTextPresenter;
import com.zxf.joke.ui.view.IJokeView;

/**
 * Created by zhangman on 16/3/29 14:30.
 * Email: zhangman523@126.com
 */
public class JokeTextFragment extends BaseFragment implements IJokeView {

  @Bind(R.id.recyclerView) RecyclerView recyclerView;
  @Bind(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;

  @Override protected void initPresenter() {
    mPresenter = new JokeTextPresenter(this);
  }

  @Override protected int getLayout() {
    return R.layout.fragment_joke;
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // TODO: inflate a fragment view
    View rootView = super.onCreateView(inflater, container, savedInstanceState);
    ButterKnife.bind(this, rootView);
    return rootView;
  }


}
