package com.zxf.joke.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;
import com.zxf.joke.R;
import com.zxf.joke.presenter.CustomDialogPresenter;
import com.zxf.joke.ui.view.ICustomDialog;

/**
 * Created by zhangman on 16/4/9 15:00.
 * Email: zhangman523@126.com
 */
public class CustomWebViewDialog extends DialogFragment implements ICustomDialog {
  private CustomDialogPresenter mPresenter;
  private WebView mWebView;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mPresenter = new CustomDialogPresenter(this);
  }

  @NonNull @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
    View customView;
    try {
      customView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_webview, null);
      mWebView = (WebView) customView.findViewById(R.id.webview);
      mWebView.addJavascriptInterface(new WebAppInterface(getActivity()), "Android");
    } catch (InflateException e) {
      e.printStackTrace();
      throw new IllegalStateException("This device does not support Web Views.");
    }
    return mPresenter.makeOkDialog(this, customView);
  }

  public class WebAppInterface {
    Context mContext;

    public WebAppInterface(Context c) {
      mContext = c;
    }

    @JavascriptInterface public void showToast(String message) {
      Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }
  }
}
