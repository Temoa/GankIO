package com.temoa.gankio.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.temoa.gankio.CommonFragment;
import com.temoa.gankio.Constants;

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
        switch (position) {
            case 0:
                return new CommonFragment().getInstance(Constants.TYPE_ANDROID);
            case 1:
                return new CommonFragment().getInstance(Constants.TYPE_IOS);
            case 2:
                return new CommonFragment().getInstance(Constants.TYPE_WEB);
            default:
                return null;
        }
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
