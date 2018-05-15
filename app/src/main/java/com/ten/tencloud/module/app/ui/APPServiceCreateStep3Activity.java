package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.bean.AppServiceYAMLBean;
import com.ten.tencloud.module.app.contract.AppServiceYamlContract;
import com.ten.tencloud.module.app.presenter.AppServiceYamlPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class APPServiceCreateStep3Activity extends BaseActivity implements AppServiceYamlContract.View {

    @BindView(R.id.et_cluster_ip)
    EditText et_cluster_ip;
    @BindView(R.id.et_public_ip)
    EditText et_public_ip;
    //负载均衡
    @BindView(R.id.et_balance_user)
    EditText et_balance_user;
    @BindView(R.id.et_balance_provider)
    EditText et_balance_provider;
    @BindView(R.id.tv_port)
    TextView tv_port;


    private int mSourceType;
    private int mServiceType;
    private AppBean mAppBean;
    private List<AppServiceYAMLBean.Port> mPorts;
    private String mServiceName;
    private AppServiceYamlPresenter mAppServiceYamlPresenter;
    private String mPodTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_appservice_create_step3);
        mSourceType = getIntent().getIntExtra("sourceType", 0);
        mServiceType = getIntent().getIntExtra("serviceType", 0);
        mAppBean = getIntent().getParcelableExtra("appBean");
        mServiceName = getIntent().getStringExtra("serviceName");
        mPodTag = getIntent().getStringExtra("podTag");

        initTitleBar(true, "创建服务", "下一步", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
        mAppServiceYamlPresenter = new AppServiceYamlPresenter();
        mAppServiceYamlPresenter.attachView(this);
    }

    private void next() {
        String publicIp = et_public_ip.getText().toString();
        if (TextUtils.isEmpty(publicIp)) {
            showMessage("请填写外部IP");
            return;
        }
        if (mPorts == null || mPorts.size() == 0) {
            showMessage("请添加端口");
            return;
        }
        List<String> externalIps = new ArrayList<>();
        externalIps.add(et_public_ip.getText().toString());
        AppServiceYAMLBean serviceYAMLBean = new AppServiceYAMLBean();
        serviceYAMLBean.setApp_id(mAppBean.getId())
                .setApp_name(mAppBean.getName())
                .setService_name(mServiceName)
                .setService_source(mSourceType)
                .setService_type(mServiceType)
                .setPorts(mPorts)
                .setClusterIP(et_cluster_ip.getText().toString())
                .setProvider(et_balance_provider.getText().toString())
                .setLoadBalancerIP(et_balance_user.getText().toString())
                .setExternalIPs(externalIps);

        if (!TextUtils.isEmpty(mPodTag)) {
            String[] split = mPodTag.split(",");
            Map<String, String> map = new HashMap<>();
            for (String s : split) {
                String[] str = s.split("=");
                map.put(str[0], str[1]);
            }
            serviceYAMLBean.setPod_label(map);
        }
        mAppServiceYamlPresenter.generateYAML(serviceYAMLBean);
    }

    @OnClick({R.id.ll_port})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_port:
                Intent intent = new Intent(this, APPServiceCreateAddPortActivity.class);
                startActivityForResult(intent, 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == APPServiceCreateAddPortActivity.RESULT_CODE_ADD_PORT) {
            mPorts = data.getParcelableArrayListExtra("ports");
            if (mPorts == null || mPorts.size() == 0) {
                return;
            }
            String port = "";
            for (int i = 0; i < mPorts.size(); i++) {
                port = port + "," + mPorts.get(i).getName();
            }
            port = port.replaceFirst(",", "");
            if (TextUtils.isEmpty(port)) {
                return;
            }
            tv_port.setText(port);
        }
    }

    @Override
    public void showYAML(String yaml) {
        Intent intent = new Intent(this, APPServiceCreateStep4Activity.class);
        intent.putExtra("yaml", yaml);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAppServiceYamlPresenter.detachView();
        mAppServiceYamlPresenter = null;
    }
}
