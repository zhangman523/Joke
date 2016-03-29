package com.zxf.joke.ui.view;

/**
 * Created by zhangman on 16/3/29 16:45.
 * Email: zhangman523@126.com
 */
public interface ISwipeRefreshView extends IBaseView {
  void getDataFinish();

  void showEmptyView();

  void showErrorView(Throwable throwable);

  void showRefresh();

  void hideRefresh();
}
