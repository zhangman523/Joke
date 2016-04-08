package com.zxf.joke.ui;

import android.app.Application;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by zhangman on 16/3/31 13:38.
 * Email: zhangman523@126.com
 */
public class JokeApp extends Application {
  @Override public void onCreate() {
    super.onCreate();
    Fresco.initialize(this);
  }

  @Override public void onTerminate() {
    super.onTerminate();
  }
}
