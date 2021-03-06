package com.zxf.joke.presenter;

import com.zxf.joke.core.Joke;
import com.zxf.joke.core.MainFactory;
import com.zxf.joke.ui.view.IBaseView;

/**
 * Created by zhangman on 16/3/29 10:16.
 * Email: zhangman523@126.com
 */
public class BasePresenter<GV extends IBaseView> {
  protected GV mView;

  public static final Joke mJoke = MainFactory.getJokeInstance();

  public BasePresenter(GV view) {
    this.mView = view;
  }

  public void onDestory() {//GC
    mView = null;
  }
}
