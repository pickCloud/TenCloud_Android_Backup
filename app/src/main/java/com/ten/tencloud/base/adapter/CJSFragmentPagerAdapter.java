package com.ten.tencloud.base.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by lxq on 2017/11/20.
 */

public class CJSFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles;
    private List<Fragment> mFragments;

    public void setFragments(List<Fragment> fragments) {
        mFragments.clear();
        mFragments.addAll(fragments);
    }

    public void addFragment(Fragment fragment) {
        mFragments.add(fragment);
    }

    public CJSFragmentPagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.mTitles = titles;
        mFragments = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
