package com.zxf.joke.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.zxf.joke.R;
import com.zxf.joke.data.Constant;
import net.youmi.android.spot.SplashView;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;

/**
 * Created by zhangman1 on 16/4/10.
 */
public class SplashActivity extends AppCompatActivity {
  @Bind(R.id.splash_rl) RelativeLayout splashRl;
  @Bind(R.id.splash_bm) LinearLayout splashBm;
  @Bind(R.id.ad_frame) FrameLayout adFrame;

  private SplashView mSplashView;
  private SharedPreferences mSharedPreferences;
  private Handler mHandler = new Handler();
  private final Runnable mHidePart2Runnable = new Runnable() {
    @SuppressLint("InlinedApi") @Override public void run() {
      // Delayed removal of status and navigation bar

      // Note that some of these constants are new as of API 16 (Jelly Bean)
      // and API 19 (KitKat). It is safe to use them, as they are inlined
      // at compile-time and do nothing on earlier devices.
      splashRl.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
          | View.SYSTEM_UI_FLAG_FULLSCREEN
          | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
          | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
          | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
          | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
  };

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    ButterKnife.bind(this);
    mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    mHandler.post(mHidePart2Runnable);
    if (!mSharedPreferences.getBoolean(Constant.FIRST_INIT, true)) {
      splashBm.setVisibility(View.VISIBLE);
      mSplashView = new SplashView(this, SplashActivity.class);
      adFrame.addView(mSplashView.getSplashView());
      mSplashView.setIntent(new Intent(this, MainActivity.class));
      SpotManager.getInstance(this).showSplashSpotAds(this, mSplashView, new SpotDialogListener() {
        @Override public void onShowSuccess() {
          Log.d("TAG", "onShowSuccess");
        }

        @Override public void onShowFailed() {
          Log.d("TAG", "onShowFailed");
        }

        @Override public void onSpotClosed() {
          Log.d("TAG", "onSpotClosed");
        }

        @Override public void onSpotClick(boolean b) {
          Log.d("TAG", "onSpotClick");
        }
      });
    } else {
      mSharedPreferences.edit().putBoolean(Constant.FIRST_INIT, false).apply();
      //渐变展示启动屏
      AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
      aa.setDuration(2000);
      splashRl.startAnimation(aa);
      aa.setAnimationListener(new Animation.AnimationListener() {
        @Override public void onAnimationEnd(Animation arg0) {
          goMain();
        }

        @Override public void onAnimationRepeat(Animation animation) {
        }

        @Override public void onAnimationStart(Animation animation) {
        }
      });
    }
  }

  private void goMain() {
    startActivity(new Intent(SplashActivity.this, MainActivity.class));
    finish();
  }

  @OnClick({ R.id.splash_bm }) public void OnClick(View view) {
    switch (view.getId()) {
      case R.id.splash_bm:
        mSplashView.stopJumpIntent();
        goMain();
        break;
      default:
    }
  }
}
