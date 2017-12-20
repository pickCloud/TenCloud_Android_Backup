package com.ten.tencloud.module.user.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseActivity;

import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    private AlertDialog mDialog;

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
                if (mDialog == null)
                    mDialog = new AlertDialog.Builder(this)
                            .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    TenApp.getInstance().jumpLoginActivity();
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("取消", null)
                            .setMessage("是否退出登录？")
                            .create();
                mDialog.show();
                break;
            case R.id.ll_account:
                startActivityNoValue(this, SettingAccountActivity.class);
                break;
        }
    }
}
