package com.ten.tencloud.base.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.ten.tencloud.base.view.BasePager;

import java.util.List;

/**
 * Created by lxq on 2017/12/26.
 */

public class CJSVpPagerAdapter extends PagerAdapter {

    private String[] mTitles;
    private List<BasePager> mPagers;

    public CJSVpPagerAdapter(String[] titles, List<BasePager> pagers) {
        mTitles = titles;
        mPagers = pagers;
    }

    public CJSVpPagerAdapter(List<BasePager> pagers) {
        mPagers = pagers;
    }

    @Override
    public int getCount() {
        return mPagers.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mPagers.get(position));
        return mPagers.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles == null) {
            return super.getPageTitle(position);
        }
        return mTitles[position];
    }
}
