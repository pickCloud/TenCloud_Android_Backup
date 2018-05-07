package com.ten.tencloud.module.app.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.module.app.model.AppK8sDeployModel;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class AppK8sRegularDeployStep3Activity extends BaseActivity {

    @BindView(R.id.et_code)
    EditText mEtCode;

    private String mName;
    private AppBean mAppBean;

    private AppK8sDeployModel mAppK8sDeployModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_k8s_regular_deploy_step3);
        initTitleBar(true, "kubernetes常规部署");
        mName = getIntent().getStringExtra("name");
        mAppBean = getIntent().getParcelableExtra("appBean");
        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {
        mAppK8sDeployModel = new AppK8sDeployModel(new AppK8sDeployModel.OnAppK8sDeployListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(String message) {

            }

            @Override
            public void onMessage(String text) {

            }
        });
    }

    @OnClick({R.id.btn_start, R.id.tv_log})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:

                break;
            case R.id.tv_log:

                break;
        }
    }

    private void startDeploy() {
        String dockerFile = mEtCode.getText().toString();
        Map<String, Object> map = new HashMap<>();
        String json = TenApp.getInstance().getGsonInstance().toJson(map);
        mAppK8sDeployModel.connect();
        mAppK8sDeployModel.send(json);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                mBtnStart.setText("构建中...请稍候...");
//                mBtnStart.setEnabled(false);
            }
        });
//        mMakeStatus = -1;
//        showLogDialog("正在构建中...请稍候...\n", true);
    }
}
