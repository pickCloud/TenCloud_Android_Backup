package com.ten.tencloud.module.user.ui;

import android.os.Bundle;
import android.view.View;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.utils.Utils;

import butterknife.OnClick;

/**
 * 企业异议
 */
public class CompanyObjectionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_company_objection);
        initTitleBar(true, "提交企业异议");
    }

    @OnClick({R.id.tv_phone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_phone:
                Utils.callPhone(this, "05922232326");
                break;
        }
    }
}
