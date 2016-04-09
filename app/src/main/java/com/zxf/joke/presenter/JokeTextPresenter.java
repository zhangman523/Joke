package com.zxf.joke.presenter;

import com.zxf.joke.core.MainFactory;
import com.zxf.joke.data.JokeTextData;
import com.zxf.joke.data.entity.JokeText;
import com.zxf.joke.ui.view.IJokeView;
import java.util.List;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by zhangman on 16/3/29 14:55.
 * Email: zhangman523@126.com
 */
public class JokeTextPresenter extends BasePresenter<IJokeView> {
  private int currentPage = 1;

  public JokeTextPresenter(IJokeView view) {
    super(view);
  }

  public void getData() {
    mJoke.getJokeTextData(MainFactory.JOKE_KEY, currentPage, MainFactory.PAGE_SIZE)
        .observeOn(AndroidSchedulers.mainThread())
        .map(new Func1<JokeTextData, JokeTextData.Result>() {
          @Override public JokeTextData.Result call(JokeTextData jokeTextData) {
            return jokeTextData.result;
          }
        })
        .map(new Func1<JokeTextData.Result, List<JokeText>>() {
          @Override public List<JokeText> call(JokeTextData.Result result) {
            return result.data;
          }
        })
        .subscribe(new Subscriber<List<JokeText>>() {
          @Override public void onCompleted() {

          }

          @Override public void onError(Throwable e) {
            e.printStackTrace();
          }

          @Override public void onNext(List<JokeText> jokeTexts) {
            if (!jokeTexts.isEmpty()) {
              if (currentPage == 1) {
                mView.fillData(jokeTexts);
              } else {
                mView.appendMoreDataToView(jokeTexts);
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
