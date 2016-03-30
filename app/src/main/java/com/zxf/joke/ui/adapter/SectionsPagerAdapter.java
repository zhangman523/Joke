package com.zxf.joke.ui.adapter;

/**
 * Created by zhangman on 16/3/29 13:53.
 * Email: zhangman523@126.com
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.zxf.joke.ui.fragment.JokeTextFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
  private Fragment[] fragments = new Fragment[] { new JokeTextFragment(), new JokeTextFragment() };
  private String[] titles = new String[] { "段子", "趣图" };

  public SectionsPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override public Fragment getItem(int position) {
    // getItem is called to instantiate the fragment for the given page.
    // Return a PlaceholderFragment (defined as a static inner class below).
    return fragments[position];
  }

  @Override public int getCount() {
    // Show 3 total pages.
    return fragments.length;
  }

  @Override public CharSequence getPageTitle(int position) {
    return titles[position];
  }
}