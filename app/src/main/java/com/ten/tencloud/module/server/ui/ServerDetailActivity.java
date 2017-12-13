package com.ten.tencloud.module.server.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.base.view.BasePager;
import com.ten.tencloud.module.server.adapter.VpServerDetailAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ServerDetailActivity extends BaseActivity {

    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.vp_server_detail)
    ViewPager mVpDetail;

    private String mServerName;
    private String mServerId;
    private String[] mTitles;
    private VpServerDetailAdapter mAdapter;
    private List<BasePager> mPagers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_server_detail);
        initData();
        initView();
    }

    private void initData() {
        mServerName = getIntent().getStringExtra("name");
        mServerId = getIntent().getStringExtra("id");
        mTitles = getResources().getStringArray(R.array.tabServerDetail);
    }

    private void initView() {
        initTitleBar(true, mServerName);
        mPagers = new ArrayList<>();
        mPagers.add(new ServerDetailMonitorPager(this).putArgument("id", mServerId).putArgument("name", mServerName));
        mPagers.add(new ServerDetailBasicPager(this).putArgument("id", mServerId));
        mPagers.add(new ServerDetailConfigPager(this).putArgument("id", mServerId));
        mPagers.add(new ServerDetailDockerPager(this).putArgument("id", mServerId));
        mPagers.add(new ServerDetailLogPager(this).putArgument("id", mServerId));
        mAdapter = new VpServerDetailAdapter(mPagers, mTitles);
        mVpDetail.setAdapter(mAdapter);
        mPagers.get(0).init();
        mVpDetail.setOffscreenPageLimit(mTitles.length);
        mTab.setupWithViewPager(mVpDetail);
        mVpDetail.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position != 0)
                    mPagers.get(position).init();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ServerDetailBasicPager.CODE_CHANGE_NAME) {
            initTitleBar(true, data.getStringExtra("name"));
        }
        for (BasePager pager : mPagers) {
            pager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        for (BasePager pager : mPagers) {
            pager.onActivityDestroy();
        }
        super.onDestroy();
    }
}
