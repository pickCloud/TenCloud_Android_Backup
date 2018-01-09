package com.ten.tencloud.module.user.ui;

import android.os.Bundle;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;

public class EmployeeJoinActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_employee_join);
        initTitleBar(true, "加入已有企业");
    }
}
