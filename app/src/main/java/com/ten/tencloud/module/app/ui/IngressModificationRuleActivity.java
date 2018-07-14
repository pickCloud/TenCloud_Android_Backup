package com.ten.tencloud.module.app.ui;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.ExampleBean;
import com.ten.tencloud.bean.RulesBean;
import com.ten.tencloud.bean.ServiceBean;
import com.ten.tencloud.bean.ServicePortBean;
import com.ten.tencloud.constants.IntentKey;
import com.ten.tencloud.module.app.contract.AppServiceRulesContract;
import com.ten.tencloud.module.app.presenter.AppServiceRulesPresenter;
import com.ten.tencloud.utils.UiUtils;
import com.ten.tencloud.widget.StatusSelectPopView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 修改Ingress规则
 */
public class IngressModificationRuleActivity extends BaseActivity implements AppServiceRulesContract.View{

    @BindView(R.id.ll_layout)
    LinearLayout mLlLayout;
    @BindView(R.id.ll_add_port)
    LinearLayout mLlAddPort;
    private int mAppId;
    private AppServiceRulesPresenter mAppServiceRulesPresenter;
    private List<ServicePortBean> mServicePortBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_ingress_modification_rule);
        initTitleBar(true, "修改Ingress规则", "确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();

            }
        });

        UiUtils.addTransitionAnim(mLlLayout);//添加动效

        mAppId = getIntent().getIntExtra(IntentKey.APP_ID, 0);


        mAppServiceRulesPresenter = new AppServiceRulesPresenter();
        mAppServiceRulesPresenter.attachView(this);
        mAppServiceRulesPresenter.servicePort(mAppId);

    }

    //保存View
    private SparseArray<View> views = new SparseArray<>();
    //初始化Key，用于记录动态创建的View
    private int initKey = 1000;

    private void createLayoutView() {
        final int key = initKey;

        final View view = getLayoutInflater().inflate(R.layout.layout_app_ingress_rule_port, mLlLayout, false);
        View removePort = view.findViewById(R.id.ll_remove_port);
        StatusSelectPopView spvProtocol = view.findViewById(R.id.spv_match);

        List<String> statusTitles = new ArrayList<>();
        for (ServicePortBean portBean:mServicePortBeanList){
            statusTitles.add(portBean.name);

        }
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
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> mapList = new ArrayList<>();
        map.put("app_id", mAppId);
        for (int i = 1000; i < initKey; i++) {
            View childView = views.get(i);
            EditText etHost = childView.findViewById(R.id.et_host);
            EditText etPath = childView.findViewById(R.id.et_path);
            TextView tvStatus = childView.findViewById(R.id.spv_match).findViewById(R.id.tv_status);
            String host = etHost.getText().toString().trim();
            String path = etPath.getText().toString().trim();
            String status = tvStatus.getText().toString().trim();

            if (ObjectUtils.isEmpty(host)){
                ToastUtils.showShort("请输入host");
                return;
            }
            if (ObjectUtils.isEmpty(path)){
                ToastUtils.showShort("请输入path");
                return;
            }

            Map<String, Object> objectMap = new HashMap<>();

            List<Map<String, Object>> mapList1 = new ArrayList<>();
            Map<String, Object> objectMap1 = new HashMap<>();
            objectMap1.put("serviceName", status);
            for (ServicePortBean portBean:mServicePortBeanList){
                if (portBean.name.equals(status)){
                    objectMap1.put("servicePort", portBean.port);
                    break;
                }
            }
            objectMap1.put("path", path);
            mapList1.add(objectMap1);
            objectMap.put("host", host);
            objectMap.put("paths", mapList1);


            mapList.add(objectMap);
        }

        map.put("rules", mapList);
        mAppServiceRulesPresenter.ingressIngress(map);

    }

    @OnClick(R.id.ll_add_port)
    public void onViewClicked() {
        if (ObjectUtils.isEmpty(mServicePortBeanList)){
            ToastUtils.showShort("暂未获取到配置服务");
            return;
        }
        createLayoutView();

    }

    @Override
    public void showResult(Object o) {
        showMessage("操作成功");
        finish();

    }

    @Override
    public void servicePortList(List<ServicePortBean> servicePortBeanList) {
        mServicePortBeanList = servicePortBeanList;

        ServiceBean serviceBean = (ServiceBean) getIntent().getSerializableExtra(IntentKey.INGRESS_INFO);
        if (serviceBean != null){

            if (!ObjectUtils.isEmpty(serviceBean.getRules())){

                for (RulesBean rulesBean:serviceBean.getRules()){
                    final int key = initKey;

                    final View view = getLayoutInflater().inflate(R.layout.layout_app_ingress_rule_port, mLlLayout, false);
                    View removePort = view.findViewById(R.id.ll_remove_port);
                    StatusSelectPopView spvProtocol = view.findViewById(R.id.spv_match);

                    List<String> statusTitles = new ArrayList<>();
                    for (ServicePortBean portBean:mServicePortBeanList){
                        statusTitles.add(portBean.name);

                    }
                    spvProtocol.initData(statusTitles);

                    EditText etHost = view.findViewById(R.id.et_host);
                    EditText etPath = view.findViewById(R.id.et_path);
                    TextView tvStatus = view.findViewById(R.id.spv_match).findViewById(R.id.tv_status);

                    if (!ObjectUtils.isEmpty(rulesBean.host))
                        etHost.setText(rulesBean.host);
                    if (!ObjectUtils.isEmpty(rulesBean.paths) && rulesBean.paths.size() > 0){
                        etPath.setText(rulesBean.paths.get(0).path);
                        tvStatus.setText(rulesBean.paths.get(0).serviceName);
                    }

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


            }

        }
    }

    @Override
    public void podLabels(List<ExampleBean> exampleBeans) {

    }


}
