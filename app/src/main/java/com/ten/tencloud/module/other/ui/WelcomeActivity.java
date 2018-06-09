package com.ten.tencloud.module.other.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSFragmentPagerAdapter;
import com.ten.tencloud.base.view.BaseActivity;

import butterknife.BindView;

public class WelcomeActivity extends BaseActivity {

    String[] titles = {"", "", "", ""};

    @BindView(R.id.vp_content)
    ViewPager mVpContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_welcome);
        hideToolBar();
        CJSFragmentPagerAdapter adapter = new CJSFragmentPagerAdapter(getSupportFragmentManager(), titles);
        adapter.addFragment(new WelcomeFragment().putArgument("page", 0));
        adapter.addFragment(new WelcomeFragment().putArgument("page", 1));
        adapter.addFragment(new WelcomeFragment().putArgument("page", 2));
        adapter.addFragment(new WelcomeFragment().putArgument("page", 3));
        mVpContent.setAdapter(adapter);
    }
}
