package com.ten.tencloud.module.server.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;

import butterknife.OnClick;

public class CostDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_cost_detail);
        initTitleBar(true, "费用详情");
    }

    @OnClick({R.id.ll_cost_1, R.id.ll_cost_2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_cost_1: {
                Intent intent = new Intent(this, CostInfoActivity.class);
                intent.putExtra("type", 0);
                startActivity(intent);
                break;
            }
            case R.id.ll_cost_2: {
                Intent intent = new Intent(this, CostInfoActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
            }
        }
    }
}
