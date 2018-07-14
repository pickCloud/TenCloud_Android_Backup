package com.ten.tencloud.module.app.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.ObjectUtils;
import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.broadcast.RefreshBroadCastHandler;
import com.ten.tencloud.constants.IntentKey;
import com.ten.tencloud.even.FinishActivityEven;
import com.ten.tencloud.module.app.model.AppCreateServiceModel;
import com.ten.tencloud.widget.dialog.AppK8sDeployDialog;

import org.greenrobot.eventbus.EventBus;

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

    private String mServiceName;
//    private AppBean mAppBean;

    private AppCreateServiceModel mAppCreateServiceModel;
    private AppK8sDeployDialog mAppK8sDeployDialog;

    private String mYamlCode = "";

    private String mYaml;
    private int mServiceType;
    private int mSourceType;
    private String mServiceId;
    private RefreshBroadCastHandler mAppRefreshHandler;
    private int mAppId;
    private String mAppName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_appservice_create_step4);
        initTitleBar(true, "创建服务");
        mServiceName = getIntent().getStringExtra(IntentKey.SERVICE_NAME);
        mAppName = getIntent().getStringExtra(IntentKey.APP_NAME);
        mYaml = getIntent().getStringExtra(IntentKey.YAML);
        mSourceType = getIntent().getIntExtra(IntentKey.SERVICE_SOURCE, -1);
        mServiceType = getIntent().getIntExtra(IntentKey.SERVICE_TYPE, -1);
        mAppId = getIntent().getIntExtra(IntentKey.APP_ID, -1);

        int serviceId = getIntent().getIntExtra(IntentKey.SERVICE_ID, -1);
        if (serviceId != -1) {
            mServiceId = serviceId + "";
        }
        if (!ObjectUtils.isEmpty(mServiceId)){
            mBtnStart.setText("更新服务");
        }

//        mAppBean = getIntent().getParcelableExtra(IntentKey.APP_ITEM);
        mAppRefreshHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.APP_LIST_CHANGE_ACTION);

        initView();
        initData();

    }

    private void initView() {
        mBtnStart.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(mYaml)) {
            mEtCode.setText(mYaml);
        }
//        else {
//            mEtCode.setText(mYamlCode);
//        }
        mEtCode.setSelection(mEtCode.getText().toString().length());
    }

    private void initData() {
        mAppCreateServiceModel = new AppCreateServiceModel(new AppCreateServiceModel.OnAppServiceListener() {
            @Override
            public void onSuccess(final String serviec_id) {
                showLogDialog("创建成功", true);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mServiceId = serviec_id.substring(serviec_id.indexOf(":") + 1, serviec_id.length());

                        mMakeStatus = 0;
                        mAppK8sDeployDialog.setServiceStatus(true);
                        mBtnStart.setVisibility(View.GONE);
                        mLlFailed.setVisibility(View.GONE);
                        mLlSuccess.setVisibility(View.VISIBLE);
                        findViewById(R.id.tv_edit).setVisibility(View.VISIBLE);
                        mEtCode.setFocusable(false);
                        mEtCode.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                        mEtCode.setClickable(false); // user navigates with wheel and selects widget
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
                        mAppK8sDeployDialog.setServiceStatus(false);
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

    @OnClick({R.id.btn_start, R.id.tv_log, R.id.btn_view_image, R.id.tv_edit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_edit:
                mEtCode.setFocusable(true);
                mEtCode.setFocusableInTouchMode(true); // user touches widget on phone with touch screen
                mEtCode.setClickable(true); // user navigates with wheel and selects widget
                mBtnStart.setVisibility(View.VISIBLE);
                mLlFailed.setVisibility(View.GONE);
                mLlSuccess.setVisibility(View.GONE);
                mBtnStart.setEnabled(true);
                mBtnStart.setText("重新创建");
                break;
            case R.id.btn_start:
                startDeploy();
                break;
            case R.id.tv_log:
                showLogDialog("", false);
                break;
            case R.id.btn_view_image:
                Intent intent = new Intent(this, AppServiceDetailsActivity.class);
                intent.putExtra(IntentKey.APP_ID, mAppId);
                intent.putExtra(IntentKey.SERVICE_ID, Integer.valueOf(mServiceId));
                startActivity(intent);
                EventBus.getDefault().post(new FinishActivityEven());
                mAppRefreshHandler.sendBroadCast();
                finish();
                break;
        }
    }

    private void startDeploy() {
        String yaml = mEtCode.getText().toString();
        Map<String, Object> map = new HashMap<>();
        map.put("app_id", mAppId);
        map.put("app_name", mAppName);
        map.put("service_name", mServiceName);
        if (mServiceType != -1) {
            map.put("service_type", mServiceType);
        }

        if (mSourceType != -1){
            map.put("service_source", mSourceType);
        }
        if (!ObjectUtils.isEmpty(mServiceId)) {
            map.put("service_id", mServiceId);
        }
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
