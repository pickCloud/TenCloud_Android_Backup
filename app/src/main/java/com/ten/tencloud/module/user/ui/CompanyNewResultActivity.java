package com.ten.tencloud.module.user.ui;

import android.os.Bundle;
import android.view.View;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;

import butterknife.BindView;

public class CompanyNewResultActivity extends BaseActivity {

    @BindView(R.id.ll_success)
    View mLlSuccess;
    @BindView(R.id.ll_failed)
    View mLlFailed;

    private boolean mIsSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_company_new_result);
        initTitleBar(true, "提交结果");
        mIsSuccess = getIntent().getBooleanExtra("isSuccess", false);
        mLlSuccess.setVisibility(mIsSuccess ? View.VISIBLE : View.GONE);
        mLlFailed.setVisibility(!mIsSuccess ? View.VISIBLE : View.GONE);
    }


    public void submitObjection(View view) {
        // TODO: 2017/12/22 提交企业异议
    }

    public void btnOk(View view) {
        finish();
    }
}
