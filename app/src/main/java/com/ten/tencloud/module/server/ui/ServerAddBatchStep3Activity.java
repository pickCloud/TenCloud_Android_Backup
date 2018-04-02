package com.ten.tencloud.module.server.ui;

import android.os.Bundle;
import android.view.View;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;

public class ServerAddBatchStep3Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_server_add_batch_step3);
        initTitleBar(true, "批量添加云主机", "导入", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
