package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppContainerBean;
import com.ten.tencloud.utils.UiUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class AppK8sRegularDeployAddPortActivity extends BaseActivity {

    public static final int RESULT_CODE_ADD_PORT = 2222;

    @BindView(R.id.ll_layout)
    LinearLayout mLlLayout;

    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_k8s_regular_deploy_add_port);
        mInflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        initTitleBar(true, "添加端口", "确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
        initView();
        initData();
    }

    private void submit() {
        int childCount = mLlLayout.getChildCount();
        ArrayList<AppContainerBean.Port> ports = new ArrayList<>();
        for (int i = 0; i < childCount; i++) {
            View childView = mLlLayout.getChildAt(i);
            EditText etName = childView.findViewById(R.id.et_name);
            EditText etProtocol = childView.findViewById(R.id.et_protocol);
            EditText etPort = childView.findViewById(R.id.et_port);
            AppContainerBean.Port port = new AppContainerBean.Port();
            port.setName(etName.getText().toString().trim().toLowerCase());
            port.setProtocol(etProtocol.getText().toString().trim().toUpperCase());
            port.setContainerPort(Integer.parseInt(etPort.getText().toString().trim()));
            ports.add(port);
        }
        Intent data = new Intent();
        data.putParcelableArrayListExtra("ports", ports);
        setResult(RESULT_CODE_ADD_PORT, data);
        finish();
    }

    private void initView() {
        mLlLayout.removeAllViews();
        UiUtils.addTransitionAnim(mLlLayout);//添加动效
    }

    private void initData() {

    }

    private void createLayoutView() {
        final View view = mInflater.inflate(R.layout.layout_app_k8s_add_port, mLlLayout, false);
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
