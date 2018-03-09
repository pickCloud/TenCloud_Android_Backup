package com.ten.tencloud.module.server.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;

import butterknife.BindView;

public class ServerDetailBasicActivity extends BaseActivity {

    @BindView(R.id.ll_content)
    LinearLayout mLlContent;
    private String mServerId;
    private ServerDetailBasicPager mServerDetailBasicPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_server_detail_basic);
        initTitleBar(true, "基本信息");
        mServerId = getIntent().getStringExtra("id");
        initView();
    }

    private void initView() {
        mServerDetailBasicPager = new ServerDetailBasicPager(this);
        mServerDetailBasicPager.putArgument("id", mServerId);
        mLlContent.addView(mServerDetailBasicPager);
        mServerDetailBasicPager.init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mServerDetailBasicPager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mServerDetailBasicPager.onActivityDestroy();
    }
}
