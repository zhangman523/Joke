package com.zxf.joke.presenter;

import com.zxf.joke.core.MainFactory;
import com.zxf.joke.data.JokeImageData;
import com.zxf.joke.data.entity.JokeImage;
import com.zxf.joke.ui.view.IJokeView;
import java.util.List;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by zhangman on 16/3/31 11:22.
 * Email: zhangman523@126.com
 */
public class JokeImagePresenter extends BasePresenter<IJokeView> {
  private int currentPage = 1;

  public JokeImagePresenter(IJokeView view) {
    super(view);
  }

  public void getData() {
    mJoke.getJokeImageData(MainFactory.JOKE_KEY, currentPage, MainFactory.PAGE_SIZE)
        .observeOn(AndroidSchedulers.mainThread())
        .map(new Func1<JokeImageData, JokeImageData.Result>() {
          @Override public JokeImageData.Result call(JokeImageData jokeImageData) {
            return jokeImageData.result;
          }
        })
        .map(new Func1<JokeImageData.Result, List<JokeImage>>() {
          @Override public List<JokeImage> call(JokeImageData.Result result) {
            return result.data;
          }
        })
        .subscribe(new Subscriber<List<JokeImage>>() {
          @Override public void onCompleted() {

          }

          @Override public void onError(Throwable e) {
            e.printStackTrace();
          }

          @Override public void onNext(List<JokeImage> jokeImages) {
            if (!jokeImages.isEmpty()) {
              if (currentPage == 1) {
                mView.fillData(jokeImages);
              } else {
                mView.appendMoreDataToView(jokeImages);
              }
              currentPage++;
            } else {
              mView.hasNoMoreData();
            }
            mView.getDataFinish();
          }
        });
  }

  public void getLastJoke() {
    currentPage = 1;
    getData();
  }
}
