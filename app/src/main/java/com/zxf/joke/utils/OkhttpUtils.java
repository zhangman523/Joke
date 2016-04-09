package com.zxf.joke.utils;

import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import cn.edu.zafu.coreprogress.helper.ProgressHelper;
import cn.edu.zafu.coreprogress.listener.ProgressResponseListener;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.zxf.joke.data.Constant;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zhangman on 16/4/9 10:06.
 * Email: zhangman523@126.com
 */
public class OkhttpUtils {
  public static Observable<String> downloadFile(final String url) {
    Observable<String> observable = Observable.create(new Observable.OnSubscribe<Response>() {
      @Override public void call(final Subscriber<? super Response> subscriber) {
        Request request = new Request.Builder().url(url).build();
        ProgressHelper.addProgressResponseListener(new OkHttpClient(),
            new ProgressResponseListener() {
              @Override
              public void onResponseProgress(long bytesRead, long contentLength, boolean done) {
                Log.e("TAG", (100 * bytesRead) / contentLength + "% done");
              }
            }).newCall(request).enqueue(new Callback() {
          @Override public void onFailure(Request request, IOException e) {
            subscriber.onError(e);
          }

          @Override public void onResponse(Response response) throws IOException {
            subscriber.onNext(response);
            subscriber.onCompleted();
          }
        });
      }
    }).map(new Func1<Response, String>() {
      @Override public String call(Response response) {
        ResponseBody responseBody = response.body();
        String path = Constant.DATA_DIR;

        path += System.currentTimeMillis() + "." + UrlUtils.parseSuffix(url);
        OutputStream outputStream = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
          outputStream = new FileOutputStream(new File(path));
          is = responseBody.byteStream();
          bis = new BufferedInputStream(is);
          bos = new BufferedOutputStream(outputStream);
          int n = 0;
          while ((n = bis.read()) != -1) {
            bos.write(n);
          }
        } catch (IOException e) {
          e.printStackTrace();
        } finally {
          if (bis != null) {
            try {
              bis.close();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
          if (bos != null) {
            try {
              bos.close();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        }
        return path;
      }
    }).subscribeOn(Schedulers.io())// 指定 subscribe() 发生在 IO 线程
        .observeOn(AndroidSchedulers.mainThread())// 指定 Subscriber 的回调发生在主线程
        ;
    return observable;
  }
}
