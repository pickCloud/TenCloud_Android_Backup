package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppBean;

public class AppK8sRegularDeployStep1Activity extends BaseActivity {

    private AppBean mAppBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_k8s_regular_deploy_step1);
        initTitleBar(true, "kubernetes常规部署", "下一步", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AppK8sRegularDeployStep2Activity.class);
                startActivity(intent);
            }
        });
        mAppBean = getIntent().getParcelableExtra("appBean");
    }
}
