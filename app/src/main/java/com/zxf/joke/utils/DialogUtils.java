package com.zxf.joke.utils;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import com.zxf.joke.presenter.CustomDialogPresenter;

/**
 * Created by zhangman1 on 16/4/9.
 */
public class DialogUtils {
  /**
   * show a custom dialog use a local html file
   *
   * @param dialogTitle title
   * @param htmlFileName file name
   */
  public static void showCustomDialog(Context context, FragmentManager fragmentManager,
      String dialogTitle, String htmlFileName, String tag) {
    int accentColor = AndroidUtils.getAccentColor(context);
    CustomDialogPresenter.create(dialogTitle, htmlFileName, accentColor).show(fragmentManager, tag);
  }
}
