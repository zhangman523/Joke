package com.zxf.joke.data;

import android.os.Environment;

/**
 * Created by zhangman on 16/4/9 10:32.
 * Email: zhangman523@126.com
 */
public class Constant {
  public static String DATA_DIR =
      Environment.getExternalStorageDirectory().getAbsolutePath() + "/com.zxf.joke/";
  public static String FIRST_INIT = "FIRST_INIT";
}
