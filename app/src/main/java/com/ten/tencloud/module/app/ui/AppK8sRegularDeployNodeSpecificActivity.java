package com.ten.tencloud.module.app.ui;

import android.os.Bundle;
import android.view.View;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;

public class AppK8sRegularDeployNodeSpecificActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_k8s_regular_deploy_node_specific);
        initTitleBar(true, "特定节点", "确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {

    }
}
