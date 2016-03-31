package com.zxf.joke.ui;

import android.app.Application;
import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.squareup.okhttp.OkHttpClient;
import java.io.InputStream;

/**
 * Created by zhangman on 16/3/31 13:38.
 * Email: zhangman523@126.com
 */
public class JokeApp extends Application {
  @Override public void onCreate() {
    super.onCreate();

    Glide.get(this)
        .register(GlideUrl.class, InputStream.class,
            new OkHttpUrlLoader.Factory(new OkHttpClient()));
  }

  @Override public void onTerminate() {
    super.onTerminate();
    Glide.get(this).unregister(GlideUrl.class, InputStream.class);
  }
}
