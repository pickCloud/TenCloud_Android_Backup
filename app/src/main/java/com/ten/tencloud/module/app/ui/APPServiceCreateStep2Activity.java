package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.bean.AppServiceYAMLBean;
import com.ten.tencloud.constants.IntentKey;
import com.ten.tencloud.even.FinishActivityEven;
import com.ten.tencloud.module.app.contract.AppServiceYamlContract;
import com.ten.tencloud.module.app.presenter.AppServiceYamlPresenter;
import com.ten.tencloud.widget.StatusSelectPopView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

public class APPServiceCreateStep2Activity extends BaseActivity implements AppServiceYamlContract.View{

    //    @BindView(R.id.tv_service_type)
//    TextView mTvSourceType;
    //    @BindView(R.id.tv_pod)
//    TextView mTvPodLabel;
//    @BindView(R.id.et_pod_tag)
//    EditText metPodTag;

    @BindColor(R.color.text_color_899ab6)
    int mTextColor899ab6;
    @BindColor(R.color.text_color_556278)
    int mTextColor556278;
    @BindColor(R.color.colorPrimary)
    int mColorPrimary;
    @BindColor(R.color.default_bg)
    int mDefaultBg;
    @BindColor(R.color.line_color_2f3543)
    int mLineColor2f3543;
    @BindView(R.id.spv_status)
    StatusSelectPopView mSpvStatus;
    @BindView(R.id.tv_see_example)
    TextView mTvSeeExample;
    @BindView(R.id.ll_one)
    LinearLayout mLlOne;
    @BindView(R.id.et_ip)
    EditText mEtIp;
    @BindView(R.id.et_port)
    EditText mEtPort;
    @BindView(R.id.et_namespace)
    EditText mEtNamespace;
    @BindView(R.id.et_external_name)
    EditText mEtExternalName;
    @BindView(R.id.et_selector_label)
    EditText mEtSelectorLabel;
    @BindView(R.id.ll_three)
    LinearLayout mLlThree;
    @BindView(R.id.ll_two)
    LinearLayout mLlTwo;

    private AppBean mAppBean;
    private int mPos;
    private String mServiceName;
    private String mServiceTag;
    private AppServiceYamlPresenter mAppServiceYamlPresenter;
    private boolean isYaml;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_appservice_create_step2);

        mAppBean = getIntent().getParcelableExtra(IntentKey.APP_ITEM);
        mServiceName = getIntent().getStringExtra(IntentKey.SERVICE_NAME);
        mServiceTag = getIntent().getStringExtra(IntentKey.SERVICE_TAG);

        mAppServiceYamlPresenter = new AppServiceYamlPresenter();
        mAppServiceYamlPresenter.attachView(this);


        initTitleBar(true, "创建服务", "下一步", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
        initView();
        initData();
    }

    private void initView() {

        List<String> statusTitles = new ArrayList<>();
        statusTitles.add("集群内应用，通过标签选择");
        statusTitles.add("集群外，通过IP:端口 映射服务");
        statusTitles.add("集群外，通过别名映射服务");

        mSpvStatus.initData(statusTitles);
        mSpvStatus.setOnSelectListener(new StatusSelectPopView.OnSelectListener() {
            @Override
            public void onSelect(int pos) {
                mPos = pos;
                switch (pos) {
                    case 0:
                        mLlOne.setVisibility(View.VISIBLE);
                        mLlTwo.setVisibility(View.GONE);
                        mLlThree.setVisibility(View.GONE);
                        findViewById(R.id.tv_tip).setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        mLlOne.setVisibility(View.GONE);
                        mLlTwo.setVisibility(View.VISIBLE);
                        mLlThree.setVisibility(View.GONE);
                        findViewById(R.id.tv_tip).setVisibility(View.GONE);
                        break;
                    case 2:
                        mLlOne.setVisibility(View.GONE);
                        mLlTwo.setVisibility(View.GONE);
                        mLlThree.setVisibility(View.VISIBLE);
                        findViewById(R.id.tv_tip).setVisibility(View.GONE);
                        break;
                        default:
                }

            }
        });

    }

    private void initData() {

    }

    private void next() {

        int service_source = mPos;
        Intent intent = null;
        intent = new Intent(this, APPServiceCreateStep3Activity.class);
        intent.putExtra(IntentKey.SERVICE_SOURCE, service_source);

        intent.putExtra(IntentKey.APP_ITEM, mAppBean);
        intent.putExtra(IntentKey.SERVICE_NAME, mServiceName);
        intent.putExtra(IntentKey.SERVICE_TAG, mServiceTag);

        switch (mPos){
            case 0://集群内应用，通过标签选择
                String selector_label = mEtSelectorLabel.getText().toString();
                if (ObjectUtils.isEmpty(selector_label)){
                    showMessage("请输入标签");
                    return;
                }
                if (TextUtils.isEmpty(selector_label) || !selector_label.contains("=")){
                    showMessage("标签格式错误");
                    return;
                }

                intent.putExtra(IntentKey.SELECTOR_LABEL, selector_label);
                startActivity(intent);

                break;
            case 1://集群外，通过映射服务
                String ip = mEtIp.getText().toString();
                if (ObjectUtils.isEmpty(ip)){
                    showMessage("请输入IP地址");
                    return;
                }
                String port = mEtPort.getText().toString();
                if (ObjectUtils.isEmpty(port)){
                    showMessage("请输入端口号");
                    return;
                }

                intent.putExtra(IntentKey.SERVICE_IP, ip);
                intent.putExtra(IntentKey.SERVICE_PORT, port);
                startActivity(intent);
                break;
            case 2://集群外，通过别名映射服务

                String namespace = mEtNamespace.getText().toString();
                String externalName = mEtExternalName.getText().toString();
                if (ObjectUtils.isEmpty(externalName)){
                    showMessage("请输入externalName");
                    return;
                }

                AppServiceYAMLBean appServiceYAMLBean = new AppServiceYAMLBean();
                appServiceYAMLBean.setService_name(mServiceName);
                appServiceYAMLBean.setApp_name(mAppBean.getName());
                appServiceYAMLBean.setApp_id(mAppBean.getId());

                String[] split = mServiceTag.split(",");
                Map<String, String> map = new HashMap<>();
                for (String s : split) {
                    String[] str = s.split("=");
                    map.put(str[0], str[1]);
                }
                appServiceYAMLBean.setLabels(map);

                appServiceYAMLBean.setService_source(mPos + 1);
//                appServiceYAMLBean.setService_type(mPos + 1);

                appServiceYAMLBean.setExternalName(externalName);
                appServiceYAMLBean.setNamespace(namespace);
                isYaml = false;

                mAppServiceYamlPresenter.generateYAML(appServiceYAMLBean);

//                intent.putExtra(IntentKey.SERVICE_NAMESPACE, namespace);
//                intent.putExtra(IntentKey.EXTERNAL_NAME, externalName);

                break;
                default:
                    break;
        }


    }

    @OnClick({R.id.tv_yaml, R.id.tv_see_example})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_yaml://跳过直接步骤4
//                intent = new Intent(this, APPServiceCreateStep4Activity.class);
////        intent.putExtra(IntentKey.APP_ITEM, mAppBean);
//                intent.putExtra(IntentKey.APP_ID, mAppBean.getId());
//                intent.putExtra(IntentKey.APP_NAME, mAppBean.getName());
//                intent.putExtra(IntentKey.SERVICE_SOURCE, mPos + 1);
//                intent.putExtra(IntentKey.SERVICE_NAME, mServiceName);
////        intent.putExtra(IntentKey.SERVICE_TYPE, mPos + 1);
//                startActivity(intent);

                isYaml = true;
                AppServiceYAMLBean appServiceYAMLBean = new AppServiceYAMLBean();
                appServiceYAMLBean.setService_name(mServiceName);
                appServiceYAMLBean.setApp_name(mAppBean.getName());
                appServiceYAMLBean.setApp_id(mAppBean.getId());
                appServiceYAMLBean.setGet_default(1);
                String[] split = mServiceTag.split(",");
                Map<String, String> map = new HashMap<>();
                for (String s : split) {
                    String[] str = s.split("=");
                    map.put(str[0], str[1]);
                }
                appServiceYAMLBean.setLabels(map);

//                appServiceYAMLBean.setService_source(mPos + 1);

                mAppServiceYamlPresenter.generateYAML(appServiceYAMLBean);
                break;
            case R.id.tv_see_example:
                intent = new Intent(this, AppServiceExampleActivity.class);
                intent.putExtra(IntentKey.APP_ID, mAppBean.getId());
                startActivity(intent);
                break;
                default:

        }
    }

    @Override
    protected boolean isBindEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finish(FinishActivityEven finishActivityEven){
        finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAppServiceYamlPresenter != null){
            mAppServiceYamlPresenter.detachView();
            mAppServiceYamlPresenter = null;
        }

    }

    @Override
    public void showYAML(String yaml) {
        Intent intent = new Intent(this, APPServiceCreateStep4Activity.class);
        intent.putExtra(IntentKey.YAML, yaml);
        intent.putExtra(IntentKey.APP_ID, mAppBean.getId());
        intent.putExtra(IntentKey.SERVICE_NAME, mServiceName);
        intent.putExtra(IntentKey.APP_NAME, mAppBean.getName());
        if (isYaml){
            startActivity(intent);
            return;
        }
//        intent.putExtra(IntentKey.APP_ITEM, mAppBean);
        intent.putExtra(IntentKey.SERVICE_SOURCE, mPos + 1);
//        intent.putExtra(IntentKey.SERVICE_TYPE, mPos + 1);
        startActivity(intent);
    }
}
