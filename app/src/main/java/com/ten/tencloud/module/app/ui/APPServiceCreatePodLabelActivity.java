package com.ten.tencloud.module.app.ui;

import android.os.Bundle;
import android.view.View;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;

public class APPServiceCreatePodLabelActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_appservice_create_pod_label);

        initTitleBar(true, "选择要访问的pod标签", "确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
