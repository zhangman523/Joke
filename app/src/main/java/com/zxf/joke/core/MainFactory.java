package com.zxf.joke.core;

/**
 * Created by zhangman on 16/3/29 10:24.
 * Email: zhangman523@126.com
 */
public class MainFactory {
  public static final String HOST = "http://japi.juhe.cn/joke";

  public static final String JOKE_KEY = "b52f0a798e8672a186c096af7261dc48";
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
