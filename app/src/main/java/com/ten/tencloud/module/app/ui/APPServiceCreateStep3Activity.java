package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.bean.AppContainerBean;
import com.ten.tencloud.bean.AppServiceYAMLBean;
import com.ten.tencloud.constants.IntentKey;
import com.ten.tencloud.even.FinishActivityEven;
import com.ten.tencloud.module.app.contract.AppServiceYamlContract;
import com.ten.tencloud.module.app.presenter.AppServiceYamlPresenter;
import com.ten.tencloud.utils.UiUtils;
import com.ten.tencloud.widget.StatusSelectPopView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class APPServiceCreateStep3Activity extends BaseActivity implements AppServiceYamlContract.View {
    @BindView(R.id.spv_status)
    StatusSelectPopView mSpvStatus;
    @BindView(R.id.ll_layout)
    LinearLayout mLlLayout;
    @BindView(R.id.ll_add_port)
    LinearLayout mLlAddPort;

    private int mSourceType;
    private AppBean mAppBean;
    private String mServiceName;
    private AppServiceYamlPresenter mAppServiceYamlPresenter;
    private String mServiceTag;
    private int mPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_appservice_create_step3);
        mAppBean = getIntent().getParcelableExtra(IntentKey.APP_ITEM);

        mSourceType = getIntent().getIntExtra(IntentKey.SERVICE_SOURCE, 0);
        mServiceName = getIntent().getStringExtra(IntentKey.SERVICE_NAME);
        mServiceTag = getIntent().getStringExtra(IntentKey.SERVICE_TAG);


        initTitleBar(true, "创建服务", "下一步", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
        mAppServiceYamlPresenter = new AppServiceYamlPresenter();
        mAppServiceYamlPresenter.attachView(this);

        initView();

    }

    private void initView() {
        UiUtils.addTransitionAnim(mLlLayout);//添加动效

        createLayoutView();

        List<String> statusTitles = new ArrayList<>();
        statusTitles.add("通过集群IP，仅集群内部访问");
        statusTitles.add("开放节点端口，集群外部可访问");
        statusTitles.add("通过负载均衡器，集群外部可访问");

        mSpvStatus.initData(statusTitles);
        mSpvStatus.setOnSelectListener(new StatusSelectPopView.OnSelectListener() {
            @Override
            public void onSelect(int pos) {
                mPos = pos;

            }
        });

    }

    //保存View
    private SparseArray<View> views = new SparseArray<>();
    //初始化Key，用于记录动态创建的View
    private int initKey = 1000;

    private void createLayoutView() {
        final int key = initKey;

        final View view = getLayoutInflater().inflate(R.layout.layout_app_service_add_port, mLlLayout, false);
        View removePort = view.findViewById(R.id.ll_remove_port);
        StatusSelectPopView spvProtocol = view.findViewById(R.id.spv_protocol);

        if (mSourceType == 0) {
            view.findViewById(R.id.rl_link_desc).setVisibility(View.GONE);
        }


        List<String> statusTitles = new ArrayList<>();
        statusTitles.add("TCP");
        statusTitles.add("UDP");
        spvProtocol.initData(statusTitles);

        views.put(key, view);

        removePort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLlLayout.removeView(view);
                initKey -- ;
            }
        });
        mLlLayout.addView(view);
        initKey++;

    }

    private void next() {

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

        appServiceYAMLBean.setService_source(mSourceType + 1);
        appServiceYAMLBean.setService_type(mPos + 1);

        switch (mSourceType){
            case 0://集群内
                String selector_label = getIntent().getStringExtra(IntentKey.SELECTOR_LABEL);
                String[] selectorLabelSplit = selector_label.split(",");
                Map<String, String> selectorLabelMap = new HashMap<>();
                for (String s : selectorLabelSplit) {
                    String[] str = s.split("=");
                    selectorLabelMap.put(str[0], str[1]);
                }
                appServiceYAMLBean.setSelector_label(selectorLabelMap);

                break;
            case 1://集群外，映射
                Map<String, String> externalIpMap = new HashMap<>();
                externalIpMap.put("ip", getIntent().getStringExtra(IntentKey.SERVICE_IP));
                externalIpMap.put("port", getIntent().getStringExtra(IntentKey.SERVICE_PORT));
                appServiceYAMLBean.setExternalIpMap(externalIpMap);
                break;
            case 2://集群外，别名映射
                appServiceYAMLBean.setExternalName(getIntent().getStringExtra(IntentKey.EXTERNAL_NAME));
                appServiceYAMLBean.setNamespace(getIntent().getStringExtra(IntentKey.SERVICE_NAMESPACE));
                break;
        }

        ArrayList<AppServiceYAMLBean.Port> ports = new ArrayList<>();


        for (int i = 1000; i < initKey; i++) {
            View childView = views.get(i);

            EditText etName = childView.findViewById(R.id.et_name);
            TextView tvProtocol = childView.findViewById(R.id.spv_protocol).findViewById(R.id.tv_status);

            EditText etPublicPort = childView.findViewById(R.id.et_public_port);
            EditText etTargetPort = childView.findViewById(R.id.et_target_port);

            String name = etName.getText().toString().trim();
            String protocol = tvProtocol.getText().toString().trim();
            String publicPort = etPublicPort.getText().toString().trim();
            String targetPort = etTargetPort.getText().toString().trim();

            if (ObjectUtils.isEmpty(name)){
                ToastUtils.showShort("请输入端口名称");
                return;
            }
            if (ObjectUtils.isEmpty(publicPort)){
                ToastUtils.showShort("请输入端口号");
                return;
            }
            if (ObjectUtils.isEmpty(targetPort)){
                ToastUtils.showShort("请输入目标端口号");
                return;
            }

            AppServiceYAMLBean.Port port = new AppServiceYAMLBean.Port();
            port.setName(name);
            port.setProtocol(protocol);
            port.setPort(Integer.valueOf(publicPort));
            port.setTargetPort(Integer.valueOf(targetPort));
            ports.add(port);

        }
        appServiceYAMLBean.setPorts(ports);
        mAppServiceYamlPresenter.generateYAML(appServiceYAMLBean);


    }

    @OnClick({R.id.ll_add_port})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_add_port:
                createLayoutView();
                break;
        }
    }

    @Override
    public void showYAML(String yaml) {
        Intent intent = new Intent(this, APPServiceCreateStep4Activity.class);
        intent.putExtra(IntentKey.YAML, yaml);
//        intent.putExtra(IntentKey.APP_ITEM, mAppBean);
        intent.putExtra(IntentKey.APP_ID, mAppBean.getId());
        intent.putExtra(IntentKey.APP_NAME, mAppBean.getName());
        intent.putExtra(IntentKey.SERVICE_SOURCE, mSourceType + 1);
        intent.putExtra(IntentKey.SERVICE_NAME, mServiceName);
        intent.putExtra(IntentKey.SERVICE_TYPE, mPos + 1);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAppServiceYamlPresenter.detachView();
        mAppServiceYamlPresenter = null;
    }

    @Override
    protected boolean isBindEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finish(FinishActivityEven finishActivityEven){
        finish();

    }

}
