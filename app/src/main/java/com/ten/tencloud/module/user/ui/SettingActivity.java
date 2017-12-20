package com.ten.tencloud.module.user.ui;

import android.os.Bundle;
import android.view.View;

import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseActivity;

import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_setting);
        initTitleBar(true, "设置");
    }

    @OnClick({R.id.ll_logout, R.id.ll_account})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_logout:
                TenApp.getInstance().jumpLoginActivity();
                break;
            case R.id.ll_account:
                startActivityNoValue(this, SettingAccountActivity.class);
                break;
        }
    }
}
