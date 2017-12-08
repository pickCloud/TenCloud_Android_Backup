package com.ten.tencloud.module.main.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSFragmentPagerAdapter;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.base.view.TempFragment;
import com.ten.tencloud.module.server.ui.ServerHomeFragment;
import com.ten.tencloud.utils.UiUtils;
import com.ten.tencloud.widget.navlayout.NavLayout;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    private String[] titles;
    private int[] iconRes = {R.mipmap.icon_nav01_normal, R.mipmap.icon_nav02_normal
            , R.mipmap.icon_nav03_normal, R.mipmap.icon_nav04_normal, R.mipmap.icon_nav05_normal};
    private int[] iconResSelected = {R.mipmap.icon_nav01_active, R.mipmap.icon_nav02_active
            , R.mipmap.icon_nav03_active, R.mipmap.icon_nav04_active, R.mipmap.icon_nav05_active};

    @BindView(R.id.nav_layout)
    NavLayout mNavLayout;
    @BindView(R.id.vp_content)
    ViewPager mVpContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_main);
        initTitleBar(false, R.string.server);
        init();
        initView();
    }

    private void init() {
        titles = new String[]{getResources().getString(R.string.server), getResources().getString(R.string.project)
                , getResources().getString(R.string.resource), getResources().getString(R.string.discover)
                , getResources().getString(R.string.mine)};
    }

    private void initView() {
        mNavLayout.setIconRes(iconRes)
                .setIconResSelected(iconResSelected)
                .setTextRes(titles)
                .setTextColor(getResources().getColor(R.color.text_color_899ab6))
                .setTextColorSelected(getResources().getColor(R.color.colorPrimary))
                .setMarginTop(UiUtils.dip2px(this, 1))
                .setTextSize(10)
                .setSelected(0);
        CJSFragmentPagerAdapter pagerAdapter = new CJSFragmentPagerAdapter(getFragmentManager(), titles);
        pagerAdapter.addFragment(new ServerHomeFragment());
        pagerAdapter.addFragment(new TempFragment());
        pagerAdapter.addFragment(new TempFragment());
        pagerAdapter.addFragment(new TempFragment());
        pagerAdapter.addFragment(new TempFragment());
        mVpContent.setOffscreenPageLimit(titles.length);
        mVpContent.setAdapter(pagerAdapter);
        mVpContent.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mNavLayout.setSelected(position);
                initTitleBar(false, titles[position]);
            }
        });
        mNavLayout.setOnItemSelectedListener(new NavLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int position) {
                mVpContent.setCurrentItem(position);
            }
        });
    }
}
