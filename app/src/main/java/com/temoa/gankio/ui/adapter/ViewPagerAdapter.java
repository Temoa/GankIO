package com.temoa.gankio.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.temoa.gankio.ui.fragment.DetailFragment;

/**
 * Created by Temoa
 * on 2016/8/1 18:44
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private String[] titleList;

    public ViewPagerAdapter(FragmentManager fm, String[] titleList) {
        super(fm);
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return new DetailFragment().getInstance(titleList[position]);
    }

    @Override
    public int getCount() {
        return titleList == null ? 0 : titleList.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList == null ? null : titleList[position];
    }
}
