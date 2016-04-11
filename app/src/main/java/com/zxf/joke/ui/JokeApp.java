package com.zxf.joke.ui;

import android.app.Application;
import android.os.Environment;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.zxf.joke.BuildConfig;
import com.zxf.joke.data.Constant;
import java.io.File;
import net.youmi.android.AdManager;
import net.youmi.android.spot.SpotManager;

/**
 * Created by zhangman on 16/3/31 13:38.
 * Email: zhangman523@126.com
 */
public class JokeApp extends Application {
  @Override public void onCreate() {
    super.onCreate();
    Fresco.initialize(this);
    AdManager.getInstance(this)
        .init(BuildConfig.YOUMI_APP_ID, BuildConfig.YOUMI_SECRET, BuildConfig.DEBUG);
    SpotManager.getInstance(this).loadSpotAds();
    File file = new File(Constant.DATA_DIR);
    if (!file.exists()) file.mkdirs();
  }

  @Override public void onTerminate() {
    super.onTerminate();
  }
}
