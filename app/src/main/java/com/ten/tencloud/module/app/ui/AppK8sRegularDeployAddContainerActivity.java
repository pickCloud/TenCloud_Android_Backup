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

public class AppK8sRegularDeployAddContainerActivity extends BaseActivity {

    @BindView(R.id.ll_layout)
    LinearLayout mLlLayout;
    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_k8s_regular_deploy_add_container);
        mInflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        initTitleBar(true, "添加容器", "确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        initView();
        initData();
    }

    private void initView() {
        UiUtils.addTransitionAnim(mLlLayout);
    }

    private void initData() {

    }

    private void createLayoutView() {
        final View view = mInflater.inflate(R.layout.layout_app_k8s_add_container, mLlLayout, false);
        View removeContainer = view.findViewById(R.id.ll_remove_container);
        removeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLlLayout.removeView(view);
            }
        });
        mLlLayout.addView(view);
    }

    @OnClick({R.id.ll_add_container})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_add_container:
                createLayoutView();
                break;
        }
    }

}
