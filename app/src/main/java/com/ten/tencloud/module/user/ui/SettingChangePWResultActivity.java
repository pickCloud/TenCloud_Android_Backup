package com.ten.tencloud.module.user.ui;

import android.os.Bundle;
import android.view.View;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.module.login.ui.ForgetStep01Activity;

import butterknife.BindView;

public class SettingChangePWResultActivity extends BaseActivity {

    @BindView(R.id.ll_success)
    View mLlSuccess;
    @BindView(R.id.ll_failed)
    View mLlFailed;

    private boolean mIsSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_setting_change_pwresult);
        initTitleBar(true, "修改密码");
        mIsSuccess = getIntent().getBooleanExtra("isSuccess", false);
        mLlSuccess.setVisibility(mIsSuccess ? View.VISIBLE : View.GONE);
        mLlFailed.setVisibility(!mIsSuccess ? View.VISIBLE : View.GONE);
    }

    public void retry(View view) {
        finish();
    }

    public void forgetPW(View view) {
        startActivityNoValue(this, ForgetStep01Activity.class);
    }

    public void btnOk(View view) {
        finish();
    }
}
