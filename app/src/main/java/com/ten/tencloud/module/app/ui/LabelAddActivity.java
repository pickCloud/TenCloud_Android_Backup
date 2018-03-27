package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.constants.Constants;

import java.util.ArrayList;

/**
 * Created by chenxh@10.com on 2018/3/27.
 */
public class LabelAddActivity extends BaseActivity{
    private ArrayList<String> mLabels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_service_label_add);
        initTitleBar(true, "新增多个标签", "确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putStringArrayListExtra("labels", mLabels);
                setResult(Constants.ACTIVITY_RESULT_CODE_FINISH, intent);
                finish();
            }
        });

        initView();
        initData();

    }

    private void initData() {
        mLabels = new ArrayList<>();
    }

    private void initView() {
    }
}
