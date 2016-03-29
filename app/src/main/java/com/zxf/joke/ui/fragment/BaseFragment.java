package com.zxf.joke.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.umeng.analytics.MobclickAgent;
import com.zxf.joke.presenter.BasePresenter;

/**
 * Created by zhangman on 16/3/29 14:35.
 * Email: zhangman523@126.com
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment {

  protected P mPresenter;
  protected Activity mActivity;

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    this.mActivity = activity;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(getLayout(), null);
    ButterKnife.bind(this, view);
    initPresenter();
    checkPresenterIsNull();
    return view;
  }

  @Override public void onResume() {
    super.onResume();
    MobclickAgent.onPageStart(getClass().getSimpleName()); //统计页面
  }

  @Override public void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd(getClass().getSimpleName());
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  @Override public void onDetach() {
    super.onDetach();
    mActivity = null;
  }

  protected abstract void initPresenter();

  protected abstract int getLayout();

  private void checkPresenterIsNull() {
    if (mPresenter == null) {
      throw new IllegalStateException("please init mPresenter in initPresenter() method ");
    }
  }
}
