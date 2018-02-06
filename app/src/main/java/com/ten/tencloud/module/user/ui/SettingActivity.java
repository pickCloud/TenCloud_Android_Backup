package com.ten.tencloud.module.user.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.module.login.contract.LogoutContract;
import com.ten.tencloud.module.login.presenter.LogoutPresenter;
import com.ten.tencloud.widget.dialog.CommonDialog;

import butterknife.OnClick;

public class SettingActivity extends BaseActivity implements LogoutContract.View {

    private AlertDialog mDialog;
    private LogoutPresenter mLogoutPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_setting);
        initTitleBar(true, "设置");
        mLogoutPresenter = new LogoutPresenter();
        mLogoutPresenter.attachView(this);
    }

    @OnClick({R.id.ll_logout, R.id.ll_account})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_logout:
//                if (mDialog == null)
//                    mDialog = new AlertDialog.Builder(this)
//                            .setPositiveButton("退出", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    mLogoutPresenter.logout();
//                                    dialog.dismiss();
//                                }
//                            })
//                            .setNegativeButton("取消", null)
//                            .setMessage("是否退出登录？")
//                            .create();
//                mDialog.show();

                new CommonDialog(mContext)
                        .setMessage("是否退出登录？")
                        .setPositiveButton("确认", new CommonDialog.OnButtonClickListener() {
                            @Override
                            public void onClick(Dialog dialog) {
                                mLogoutPresenter.logout();
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.ll_account:
                startActivityNoValue(this, SettingAccountActivity.class);
                break;
        }
    }

    @Override
    public void logoutSuccess() {
        TenApp.getInstance().jumpLoginActivity();
    }
}
