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
import com.ten.tencloud.even.DeployEven;
import com.ten.tencloud.module.app.model.AppK8sDeployModel;
import com.ten.tencloud.widget.dialog.AppK8sDeployDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class AppK8sRegularDeployStep3Activity extends BaseActivity {

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

    private AppK8sDeployModel mAppK8sDeployModel;
    private AppK8sDeployDialog mAppK8sDeployDialog;

    private String mYamlCode = "apiVersion: apps/v1 # for versions before 1.9.0 use apps/v1beta2\n" +
            "kind: Deployment\n" +
            "metadata:\n" +
            "  name: nginx-deployment-0508\n" +
            "spec:\n" +
            "  selector:\n" +
            "    matchLabels:\n" +
            "      app: nginx\n" +
            "  replicas: 2 # tells deployment to run 2 pods matching the template\n" +
            "  template: # create pods using pod definition in this template\n" +
            "    metadata:\n" +
            "      # unlike pod-nginx.yaml, the name is not included in the meta data as a unique name is\n" +
            "      # generated from the deployment name\n" +
            "      labels:\n" +
            "        app: nginx\n" +
            "    spec:\n" +
            "      containers:\n" +
            "      - name: nginx\n" +
            "        image: nginx:1.7.9\n" +
            "        ports:\n" +
            "        - containerPort: 80";
    private String mYaml;
    private int mServerId;
    private String mDeploymentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_k8s_regular_deploy_step3);
        initTitleBar(true, "常规部署");
        mName = getIntent().getStringExtra("name");
        mYaml = getIntent().getStringExtra("yaml");
        mServerId = getIntent().getIntExtra("serverId", -1);
        mAppBean = getIntent().getParcelableExtra("appBean");

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

        findViewById(R.id.btn_view_image).setOnClickListener(new View.OnClickListener() {//查看部署
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AppDeployDetailsActivity.class);
                intent.putExtra(IntentKey.APP_ID, mAppBean.getId());
                intent.putExtra(IntentKey.DEPLOYMENT_ID, Integer.valueOf(mDeploymentId));
                startActivity(intent);
                finish();
                EventBus.getDefault().post(new DeployEven());

            }
        });
    }

    private void initData() {
        mAppK8sDeployModel = new AppK8sDeployModel(new AppK8sDeployModel.OnAppK8sDeployListener() {
            @Override
            public void onSuccess(final String deployment_id) {
                showLogDialog("部署成功", true);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mDeploymentId = deployment_id.substring(deployment_id.indexOf(":") + 1, deployment_id.length());
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

    @OnClick({R.id.btn_start, R.id.tv_log})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                startDeploy();
                break;
            case R.id.tv_log:
                showLogDialog("", false);
                break;
        }
    }

    private void startDeploy() {
        String yaml = mEtCode.getText().toString();
        Map<String, Object> map = new HashMap<>();
        map.put("app_name", mAppBean.getName());
        map.put("deployment_name", mName);
        map.put("server_id", mServerId);
        map.put("app_id", mAppBean.getId());
        map.put("yaml", yaml);
        String json = TenApp.getInstance().getGsonInstance().toJson(map);
        mAppK8sDeployModel.connect();
        mAppK8sDeployModel.send(json);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mBtnStart.setText("部署中...请稍候...");
                mBtnStart.setEnabled(false);
            }
        });
        mMakeStatus = -1;
        showLogDialog("正在部署中...请稍候...\n", true);
    }

    //构建状态
    private int mMakeStatus = -1; // -1 初始化状态 0 成功 1 失败

    private void showLogDialog(final String msg, final boolean isAdd) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mAppK8sDeployDialog == null) {
                    mAppK8sDeployDialog = new AppK8sDeployDialog(mContext);
                    mAppK8sDeployDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            if (mMakeStatus == 1) {
                                mBtnStart.setVisibility(View.VISIBLE);
                                mLlFailed.setVisibility(View.GONE);
                                mLlSuccess.setVisibility(View.GONE);
                                mBtnStart.setEnabled(true);
                                mBtnStart.setText("重新部署");
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
        mAppK8sDeployModel.close();
        mAppK8sDeployModel.onDestroy();
        mAppK8sDeployModel = null;
    }
}
