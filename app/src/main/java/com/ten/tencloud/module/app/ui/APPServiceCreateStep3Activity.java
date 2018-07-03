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
import com.ten.tencloud.module.app.contract.AppServiceYamlContract;
import com.ten.tencloud.module.app.presenter.AppServiceYamlPresenter;
import com.ten.tencloud.utils.UiUtils;
import com.ten.tencloud.widget.StatusSelectPopView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class APPServiceCreateStep3Activity extends BaseActivity implements AppServiceYamlContract.View {
    @BindView(R.id.spv_status)
    StatusSelectPopView mSpvStatus;
//    @BindView(R.id.tv_public_ip)
//    TextView mTtPublicIp;
    @BindView(R.id.ll_layout)
    LinearLayout mLlLayout;
    @BindView(R.id.ll_add_port)
    LinearLayout mLlAddPort;

//    @BindView(R.id.et_cluster_ip)
//    EditText et_cluster_ip;
//    @BindView(R.id.et_public_ip)
//    EditText et_public_ip;
//    //负载均衡
//    @BindView(R.id.et_balance_user)
//    EditText et_balance_user;
//    @BindView(R.id.et_balance_provider)
//    EditText et_balance_provider;
//    @BindView(R.id.tv_port)
//    TextView tv_port;


    private int mSourceType;
    private int mServiceType;
    private AppBean mAppBean;
    private List<AppServiceYAMLBean.Port> mPorts;
    private String mServiceName;
    private AppServiceYamlPresenter mAppServiceYamlPresenter;
    private String mPodTag;
    private int mPos;
//    private int currentPostion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_appservice_create_step3);
//        mSourceType = getIntent().getIntExtra("sourceType", 0);
//        mServiceType = getIntent().getIntExtra("serviceType", 0);
//        mAppBean = getIntent().getParcelableExtra("appBean");
//        mServiceName = getIntent().getStringExtra("serviceName");
//        mPodTag = getIntent().getStringExtra("podTag");

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

//        appServiceYAMLBean.set

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

        System.out.print(ports);

        Intent intent = new Intent(this, APPServiceCreateStep4Activity.class);
        intent.putExtra("yaml", "");
        startActivity(intent);

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == APPServiceCreateAddPortActivity.RESULT_CODE_ADD_PORT) {
//            mPorts = data.getParcelableArrayListExtra("ports");
//            if (mPorts == null || mPorts.size() == 0) {
//                return;
//            }
//            String port = "";
//            for (int i = 0; i < mPorts.size(); i++) {
//                port = port + "," + mPorts.get(i).getName();
//            }
//            port = port.replaceFirst(",", "");
//            if (TextUtils.isEmpty(port)) {
//                return;
//            }
//            tv_port.setText(port);
//        }
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
