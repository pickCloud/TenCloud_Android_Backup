package com.ten.tencloud.module.server.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.ten.tencloud.base.view.BasePager;

import java.util.List;

/**
 * Created by lxq on 2017/11/29.
 */

public class VpServerDetailAdapter extends PagerAdapter {

    private List<BasePager> datas;
    private String[] titles;

    public VpServerDetailAdapter(List<BasePager> datas, String[] titles) {
        this.datas = datas;
        this.titles = titles;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BasePager pager = datas.get(position);
        container.addView(pager);
        return pager;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
