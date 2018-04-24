package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppBean;

import butterknife.OnClick;

public class AppK8sRegularDeployStep2Activity extends BaseActivity {

    private AppBean mAppBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_k8s_regular_deploy_step2);
        initTitleBar(true, "kubernetes常规部署", "下一步", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mAppBean = getIntent().getParcelableExtra("appBean");
    }

    @OnClick({R.id.ll_add_container, R.id.ll_select_node})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_add_container: {
                Intent intent = new Intent(this, AppK8sRegularDeployAddContainerActivity.class);
                startActivityForResult(intent, 0);
                break;
            }
            case R.id.ll_select_node: {
                Intent intent = new Intent(this, AppK8sRegularDeployNodeTypeActivity.class);
                startActivityForResult(intent, 0);
                break;
            }
        }
    }
}
