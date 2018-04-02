package com.ten.tencloud.module.server.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;

public class ServerAddBatchStep1Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_server_add_batch_step1);
        initTitleBar(true, "批量添加云主机", "下一步", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, ServerAddBatchStep2Activity.class));
            }
        });
    }
}
