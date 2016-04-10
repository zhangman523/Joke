package com.zxf.joke.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.zxf.joke.R;
import net.youmi.android.spot.SplashView;

/**
 * Created by zhangman1 on 16/4/10.
 */
public class SplashActivity extends AppCompatActivity {
  @Bind(R.id.splash_rl) RelativeLayout splashRl;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    ButterKnife.bind(this);
    //渐变展示启动屏
    AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
    aa.setDuration(2000);
    splashRl.startAnimation(aa);
    aa.setAnimationListener(new Animation.AnimationListener() {
      @Override public void onAnimationEnd(Animation arg0) {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
      }

      @Override public void onAnimationRepeat(Animation animation) {
      }

      @Override public void onAnimationStart(Animation animation) {
      }
    });
  }
}
