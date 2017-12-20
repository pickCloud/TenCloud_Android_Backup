package com.ten.tencloud.module.user.ui;

import android.os.Bundle;
import android.view.View;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;

public class CompanyNameSubmitActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_company_name_submit);
        initTitleBar(true, "提交结果");
    }

    public void btnOk(View view) {
        finish();
    }
}
