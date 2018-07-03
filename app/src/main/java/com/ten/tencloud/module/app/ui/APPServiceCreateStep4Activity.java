package com.ten.tencloud.module.app.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.constants.IntentKey;
import com.ten.tencloud.module.app.model.AppCreateServiceModel;
import com.ten.tencloud.widget.dialog.AppK8sDeployDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class APPServiceCreateStep4Activity extends BaseActivity {

    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.btn_start)
    Button mBtnStart;
    @BindView(R.id.ll_failed)
    View mLlFailed;
    @BindView(R.id.ll_success)
    View mLlSuccess;

    private String mName;
    private AppBean mAppBean;

    private AppCreateServiceModel mAppCreateServiceModel;
    private AppK8sDeployDialog mAppK8sDeployDialog;

    private String mYamlCode = "";

    private String mYaml;
    private int mServerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_k8s_regular_deploy_step3);
        initTitleBar(true, "创建服务");
        mName = getIntent().getStringExtra("name");
        mYaml = getIntent().getStringExtra("yaml");

        mAppBean = getIntent().getParcelableExtra(IntentKey.APP_ITEM);

        initView();
        initData();
    }

    private void initView() {
        mBtnStart.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(mYaml)) {
            mEtCode.setText(mYaml);
        } else {
            mEtCode.setText(mYamlCode);
        }
        mEtCode.setSelection(mEtCode.getText().toString().length());
    }

    private void initData() {
        mAppCreateServiceModel = new AppCreateServiceModel(new AppCreateServiceModel.OnAppServiceListener() {
            @Override
            public void onSuccess() {
                showLogDialog("部署成功", true);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mMakeStatus = 0;
                        mAppK8sDeployDialog.setStatus(true);
                        mBtnStart.setVisibility(View.GONE);
                        mLlFailed.setVisibility(View.GONE);
                        mLlSuccess.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onFailure(String message) {
                showLogDialog(message, true);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mMakeStatus = 1;
                        mAppK8sDeployDialog.setStatus(false);
                        mBtnStart.setVisibility(View.GONE);
                        mLlFailed.setVisibility(View.VISIBLE);
                        mLlSuccess.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onMessage(String text) {
                showLogDialog(text, true);
            }
        });
    }

    @OnClick({R.id.btn_start, R.id.tv_log, R.id.btn_view_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                startDeploy();
                break;
            case R.id.tv_log:
                showLogDialog("", false);
                break;
            case R.id.btn_view_image:
                Intent intent = new Intent(this, AppServiceDetailsActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void startDeploy() {
        String yaml = mEtCode.getText().toString();
        Map<String, Object> map = new HashMap<>();
        map.put("app_id", mAppBean.getId());
        map.put("app_name", mAppBean.getName());
        map.put("service_name", mName);
        map.put("service_type", mServerId);
        map.put("service_source", mServerId);
        map.put("yaml", yaml);
        String json = TenApp.getInstance().getGsonInstance().toJson(map);
        mAppCreateServiceModel.connect();
        mAppCreateServiceModel.send(json);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mBtnStart.setText("创建中...请稍候...");
                mBtnStart.setEnabled(false);
            }
        });
        mMakeStatus = -1;
        showLogDialog("正在创建中...请稍候...\n", true);
    }

    //构建状态
    private int mMakeStatus = -1; // -1 初始化状态 0 成功 1 失败

    private void showLogDialog(final String msg, final boolean isAdd) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mAppK8sDeployDialog == null) {
                    mAppK8sDeployDialog = new AppK8sDeployDialog(mContext, "正在创建中...请稍候");
                    mAppK8sDeployDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            if (mMakeStatus == 1) {
                                mBtnStart.setVisibility(View.VISIBLE);
                                mLlFailed.setVisibility(View.GONE);
                                mLlSuccess.setVisibility(View.GONE);
                                mBtnStart.setEnabled(true);
                                mBtnStart.setText("重新创建");
                            }
                        }
                    });
                }
                if (!mAppK8sDeployDialog.isShowing()) {
                    mAppK8sDeployDialog.show();
                }
                mAppK8sDeployDialog.setLog(msg, isAdd);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAppCreateServiceModel.close();
        mAppCreateServiceModel.onDestroy();
        mAppCreateServiceModel = null;
    }
}
