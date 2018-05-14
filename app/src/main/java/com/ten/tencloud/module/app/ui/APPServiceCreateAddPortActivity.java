package com.ten.tencloud.module.app.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.utils.UiUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class APPServiceCreateAddPortActivity extends BaseActivity {

    @BindView(R.id.ll_layout)
    LinearLayout mLlLayout;
    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_appservice_create_add_port);
        initTitleBar(true, "添加端口", "确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mInflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        initView();
        initData();
    }

    private void initView() {
        mLlLayout.removeAllViews();
        UiUtils.addTransitionAnim(mLlLayout);//添加动效
    }

    private void initData() {

    }

    private void createLayoutView() {
        final View view = mInflater.inflate(R.layout.layout_app_service_add_port, mLlLayout, false);
        View removePort = view.findViewById(R.id.ll_remove_port);
        removePort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLlLayout.removeView(view);
            }
        });
        mLlLayout.addView(view);
    }

    @OnClick({R.id.ll_add_port})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_add_port:
                createLayoutView();
                break;
        }
    }
}
