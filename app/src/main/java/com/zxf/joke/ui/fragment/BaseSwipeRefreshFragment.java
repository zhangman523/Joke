package com.zxf.joke.ui.fragment;

import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import butterknife.Bind;
import com.zxf.joke.R;
import com.zxf.joke.presenter.BasePresenter;
import com.zxf.joke.ui.view.ISwipeRefreshView;

/**
 * Created by zhangman on 16/3/29 16:44.
 * Email: zhangman523@126.com
 */
public abstract class BaseSwipeRefreshFragment<P extends BasePresenter> extends BaseFragment<P>
    implements ISwipeRefreshView {
  @Bind(R.id.swipe_refresh_layout) protected SwipeRefreshLayout mSwipeRefreshLayout;

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    initPresenter();
  }

  private void initSwipeLayout() {
    mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark,
        R.color.colorAccent);
    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {
        if (prepareRefresh()) {
          onRefreshStarted();
        } else {
          //产生一个加载数据的假象
          hideRefresh();
        }
      }
    });
  }

  /**
   * check data status
   *
   * @return return true indicate it should load data really else indicate don't refresh
   */
  protected boolean prepareRefresh() {
    return true;
  }

  /**
   * the method of get data
   */
  protected abstract void onRefreshStarted();

  @Override public void hideRefresh() {
    // 防止刷新消失太快，让子弹飞一会儿. do not use lambda!!
    mSwipeRefreshLayout.postDelayed(new Runnable() {
      @Override public void run() {
        if (mSwipeRefreshLayout != null) {
          mSwipeRefreshLayout.setRefreshing(false);
        }
      }
    }, 1000);
  }

  @Override public void showRefresh() {
    mSwipeRefreshLayout.setRefreshing(true);
  }

  /**
   * check refresh layout is refreshing
   *
   * @return if the refresh layout is refreshing return true else return false
   */
  @CheckResult protected boolean isRefreshing() {
    return mSwipeRefreshLayout.isRefreshing();
  }

  @Override public void getDataFinish() {
    hideRefresh();
  }
}
