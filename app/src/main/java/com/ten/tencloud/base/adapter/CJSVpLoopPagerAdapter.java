package com.ten.tencloud.base.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.ten.tencloud.base.view.BasePager;

import java.util.List;

/**
 * Created by lxq on 2017/12/26.
 */

public class CJSVpLoopPagerAdapter extends PagerAdapter {

    private String[] mTitles;
    private List<BasePager> mPagers;

    public CJSVpLoopPagerAdapter(String[] titles, List<BasePager> pagers) {
        mTitles = titles;
        mPagers = pagers;
    }

    public CJSVpLoopPagerAdapter(List<BasePager> pagers) {
        mPagers = pagers;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BasePager child = mPagers.get(position % mPagers.size());
        container.addView(child);
        return child;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mPagers.get(position % mPagers.size()));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles == null) {
            return super.getPageTitle(position % mPagers.size());
        }
        return mTitles[position % mPagers.size()];
    }
}
