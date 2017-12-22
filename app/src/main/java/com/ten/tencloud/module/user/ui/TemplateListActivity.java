package com.ten.tencloud.module.user.ui;

import android.os.Bundle;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;

public class TemplateListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_template_list);
    }
}
