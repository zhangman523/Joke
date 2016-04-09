package com.zxf.joke.core;

import com.zxf.joke.BuildConfig;

/**
 * Created by zhangman on 16/3/29 10:24.
 * Email: zhangman523@126.com
 */
public class MainFactory {
  public static final String HOST = "http://japi.juhe.cn/joke";

  public static final String JOKE_KEY = BuildConfig.JUHE_KEY;

  public static final int PAGE_SIZE = 20;

  public static Joke mJoke;
  protected static final Object monitor = new Object();

  public static Joke getJokeInstance() {
    synchronized (monitor) {
      if (mJoke == null) {
        mJoke = new MainRetrofit().getService();
      }
      return mJoke;
    }
  }
}
