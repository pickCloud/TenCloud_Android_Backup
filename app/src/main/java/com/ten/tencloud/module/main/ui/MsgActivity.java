package com.ten.tencloud.module.main.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSFragmentPagerAdapter;
import com.ten.tencloud.base.view.BaseActivity;

import butterknife.BindView;

public class MsgActivity extends BaseActivity {

    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.vp_msg)
    ViewPager mVpMsg;

    String[] titles = {"最新消息", "历史消息"};
    private CJSFragmentPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_msg);
        initTitleBar(true, "消息盒子");
        initView();
    }

    private void initView() {
        mVpMsg.setOffscreenPageLimit(2);
        mPagerAdapter = new CJSFragmentPagerAdapter(getFragmentManager(), titles);
        MsgFragment newFragment = new MsgFragment();
        newFragment.putArgument("status", "0");
        mPagerAdapter.addFragment(newFragment);
        MsgFragment historyFragment = new MsgFragment();
        historyFragment.putArgument("status", "0");
        mPagerAdapter.addFragment(historyFragment);
        mVpMsg.setAdapter(mPagerAdapter);
        mTab.setupWithViewPager(mVpMsg);
    }
}
