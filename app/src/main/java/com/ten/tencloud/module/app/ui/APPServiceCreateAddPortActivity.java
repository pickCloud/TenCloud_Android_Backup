package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppServiceYAMLBean;
import com.ten.tencloud.utils.UiUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class APPServiceCreateAddPortActivity extends BaseActivity {

    public static final int RESULT_CODE_ADD_PORT = 2222;

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
                submit();
            }
        });
        mInflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        initView();
        initData();
    }

    private void submit() {
        int childCount = mLlLayout.getChildCount();
        ArrayList<AppServiceYAMLBean.Port> ports = new ArrayList<>();
        for (int i = 0; i < childCount; i++) {
            View childView = mLlLayout.getChildAt(i);
            EditText etName = childView.findViewById(R.id.et_name);
            EditText etProtocol = childView.findViewById(R.id.et_protocol);
            EditText etPublicPort = childView.findViewById(R.id.et_public_port);
            EditText etTargetPort = childView.findViewById(R.id.et_target_port);
            EditText etNodePort = childView.findViewById(R.id.et_node_port);
            AppServiceYAMLBean.Port port = new AppServiceYAMLBean.Port();
            String name = etName.getText().toString().toLowerCase();
            if (TextUtils.isEmpty(name)) {
                showMessage("请输入端口名称");
                return;
            }
            port.setName(name);
            port.setProtocol(etProtocol.getText().toString().toUpperCase());
            String publicPort = etPublicPort.getText().toString();
            if (TextUtils.isEmpty(publicPort)) {
                showMessage("请输入端口号");
                return;
            }
            port.setPort(Integer.parseInt(publicPort));
            String targetPort = etTargetPort.getText().toString();
            if (TextUtils.isEmpty(targetPort)) {
                showMessage("请输入目标端口");
                return;
            }
            port.setTargetPort(Integer.parseInt(targetPort));
            String nodePort = etNodePort.getText().toString();
            if (!TextUtils.isEmpty(nodePort)) {
                port.setNodePort(Integer.parseInt(nodePort));
            }
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
