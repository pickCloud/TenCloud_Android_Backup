package com.ten.tencloud.module.app.ui;

import android.os.Bundle;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;

/**
 * Ingress详情
 */
public class IngressDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_ingress_details);
        initTitleBar(true, "Ingress详情");


    }
}
