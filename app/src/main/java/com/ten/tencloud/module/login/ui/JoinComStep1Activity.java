package com.ten.tencloud.module.login.ui;

import android.os.Bundle;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;

public class JoinComStep1Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_join_com_step1);
        initTitleBar(false, "邀请加入");
    }
}
