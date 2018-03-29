package com.ten.tencloud.module.server.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSVpPagerAdapter;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.base.view.BasePager;
import com.ten.tencloud.utils.UiUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

public class ServerMonitorActivity extends BaseActivity {

    @BindColor(R.color.colorPrimary)
    int mColorPrimary;
    @BindColor(R.color.text_color_899ab6)
    int mColor899ab6;

    @BindView(R.id.indicator)
    MagicIndicator mIndicator;
    @BindView(R.id.vp_content)
    ViewPager mVpContent;

    private String mServerId;
    private String[] mTitles;

    private CJSVpPagerAdapter mAdapter;
    private List<BasePager> mPagers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_server_monitor);
        initTitleBar(true, "资源监控");
        initData();
        initView();
    }

    private void initData() {
        mServerId = getIntent().getStringExtra("serverId");
        mTitles = getResources().getStringArray(R.array.tabServerMonitor);
    }

    private void initView() {
        initIndicator();
        mPagers = new ArrayList<>();
        mPagers.add(new ServerMonitorPerformancePager(this).putArgument("id", mServerId));
        mPagers.add(new ServerMonitorNetPager(this).putArgument("id", mServerId));
        mPagers.add(new ServerDetailDockerPager(this).putArgument("id", mServerId));
        mPagers.add(new ServerDetailLogPager(this).putArgument("id", mServerId));
        mAdapter = new CJSVpPagerAdapter(mTitles, mPagers);
        mVpContent.setAdapter(mAdapter);
        mPagers.get(0).init();
        mVpContent.setOffscreenPageLimit(mTitles.length);
        mVpContent.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position != 0)
                    mPagers.get(position).init();
            }
        });
        ViewPagerHelper.bind(mIndicator, mVpContent);
    }

    private void initIndicator() {
        CommonNavigator mCommonNavigator = new CommonNavigator(this);
        CommonNavigatorAdapter mCommonNavigatorAdapter = new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(mColor899ab6);
                simplePagerTitleView.setSelectedColor(mColorPrimary);
                simplePagerTitleView.setText(mTitles[index]);
                simplePagerTitleView.setTextSize(14);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mVpContent.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(mColorPrimary);
                indicator.setLineHeight(UiUtils.dip2px(context, 2));
                return indicator;
            }
        };
        mCommonNavigator.setAdapter(mCommonNavigatorAdapter);
        mIndicator.setNavigator(mCommonNavigator);
    }

    @OnClick({R.id.btn_toolbox})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_toolbox: {
//                if (mServerToolBoxActivity == null) {
//                    mServerToolBoxActivity = new ServerToolBoxActivity(this);
//                }
//                mServerToolBoxActivity.show();
                break;
            }
        }
    }
}
