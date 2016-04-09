package com.zxf.joke.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import butterknife.Bind;
import com.zxf.joke.R;
import com.zxf.joke.presenter.MainPresenter;
import com.zxf.joke.ui.adapter.SectionsPagerAdapter;
import com.zxf.joke.ui.view.IMainView;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView {

  @Bind(R.id.container) protected ViewPager mViewPager;
  @Bind(R.id.tabs) protected TabLayout tabLayout;
  private SectionsPagerAdapter mSectionsPagerAdapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Create the adapter that will return a fragment for each of the three
    // primary sections of the activity.
    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

    // Set up the ViewPager with the sections adapter.
    mViewPager.setAdapter(mSectionsPagerAdapter);
    tabLayout.setupWithViewPager(mViewPager);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_about) {
      // TODO: 16/4/9  
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override protected void initPresenter() {
    mPresenter = new MainPresenter(this);
  }

  @Override protected int getLayout() {
    return R.layout.activity_main;
  }
}
