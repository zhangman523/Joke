package com.zxf.joke.ui.adapter;

/**
 * Created by zhangman on 16/3/29 13:53.
 * Email: zhangman523@126.com
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

  public SectionsPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override public Fragment getItem(int position) {
    // getItem is called to instantiate the fragment for the given page.
    // Return a PlaceholderFragment (defined as a static inner class below).
    return null;
  }

  @Override public int getCount() {
    // Show 3 total pages.
    return 3;
  }

  @Override public CharSequence getPageTitle(int position) {
    switch (position) {
      case 0:
        return "SECTION 1";
      case 1:
        return "SECTION 2";
      case 2:
        return "SECTION 3";
    }
    return null;
  }
}