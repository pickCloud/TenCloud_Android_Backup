package com.ten.tencloud.module.app.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.ServiceBean;
import com.ten.tencloud.broadcast.RefreshBroadCastHandler;
import com.ten.tencloud.constants.IntentKey;
import com.ten.tencloud.listener.OnRefreshListener;
import com.ten.tencloud.module.app.adapter.ServiceEndAdapter;
import com.ten.tencloud.module.app.contract.AppServiceContract;
import com.ten.tencloud.module.app.model.AppCreateServiceModel;
import com.ten.tencloud.module.app.presenter.AppServicePresenter;
import com.ten.tencloud.widget.blur.BlurBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 服务详情
 */
public class AppServiceDetailsActivity extends BaseActivity implements AppServiceContract.View {

    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_id)
    TextView mTvId;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.rl_basic_detail)
    ConstraintLayout mRlBasicDetail;
    @BindView(R.id.tv_service_sourcing)
    TextView mTvServiceSourcing;
    @BindView(R.id.tv_service_tag)
    TextView mTvServiceTag;
    @BindView(R.id.tv_exposure_chamber)
    TextView mTvExposureChamber;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.tv_status_desc)
    TextView mTvStatusDesc;
    @BindView(R.id.tv_service_access)
    TextView mTvServiceAccess;
    @BindView(R.id.rv_service_end)
    RecyclerView mRvServiceEnd;
    @BindView(R.id.tv_yaml)
    TextView mTvYaml;
    @BindView(R.id.scroll_view)
    NestedScrollView mScrollView;
    private AppServicePresenter mAppServicePresenter;
    private RefreshBroadCastHandler mAppRefreshHandler;
    private ServiceBean mServiceBean;
    private ServiceEndAdapter mServiceEndAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_service_details);
        initTitleBar(true, "服务详情");

//        StatusDialog mStatusDialog = new StatusDialog(mContext);
//        mStatusDialog.show();

        mRvServiceEnd.setLayoutManager(new LinearLayoutManager(this));
        mServiceEndAdapter = new ServiceEndAdapter();
        mRvServiceEnd.setAdapter(mServiceEndAdapter);

        mAppServicePresenter = new AppServicePresenter();
        mAppServicePresenter.attachView(this);

        mAppServicePresenter.getServiceDetails(getIntent().getIntExtra(IntentKey.APP_ID, 0),
                getIntent().getIntExtra(IntentKey.SERVICE_ID, 0), 1);

        mAppRefreshHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.APP_LIST_CHANGE_ACTION);

        RefreshBroadCastHandler serviceDeleteHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.APP_SERVICE_DELETE_ACTION);
        serviceDeleteHandler.registerReceiver(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAppServicePresenter.deleteService(getIntent().getIntExtra(IntentKey.APP_ID, 0),
                        getIntent().getIntExtra(IntentKey.SERVICE_ID, 0));
            }
        });

//        mServiceEndAdapter.addData(new ServiceBean());
//        mServiceEndAdapter.addData(new ServiceBean());
//        mServiceEndAdapter.addData(new ServiceBean());

    }

    @Override
    public void showServiceDetails(ServiceBean serviceBean) {
        mServiceBean = serviceBean;
        if (!ObjectUtils.isEmpty(serviceBean.getEndpoint().get("subsets"))){
            mServiceEndAdapter.setNewData((List<Map<String, List<Map<String, Object>>>>) serviceBean.getEndpoint().get("subsets"));
        }
        mTvName.setText(serviceBean.getName());
        mTvId.setText("Uid" + serviceBean.getId());
        mTvDate.setText("创建时间：" + serviceBean.getCreate_time());

        String sourceStr = "";
        if (serviceBean.getSource() == 1)
            sourceStr = "内部服务，通过标签选择";
        else if (serviceBean.getSource() == 2)
            sourceStr = "外部服务，通过IP映射";
        else if (serviceBean.getSource() == 3)
            sourceStr = "外部服务，通过别名映射";
        mTvServiceSourcing.setText(sourceStr);

        if (!ObjectUtils.isEmpty(serviceBean.getLabels())){
            StringBuffer stringBuffer = new StringBuffer();
            for (Map.Entry<String, Object> entry : serviceBean.getLabels().entrySet()) {
                stringBuffer.append(entry.getKey() + "=").append(entry.getValue()).append(",");
            }
            mTvServiceTag.setText(stringBuffer.toString());
        }

        if (!ObjectUtils.isEmpty(serviceBean.getType())) {
            if (Integer.valueOf(serviceBean.getType()) == 1)
                mTvExposureChamber.setText("集群内访问");
            else if (Integer.valueOf(serviceBean.getType()) == 2)
                mTvExposureChamber.setText("集群内外部可访问");
            else if (Integer.valueOf(serviceBean.getType()) == 3)
                mTvExposureChamber.setText("负载均衡器");
        }

        if (serviceBean.getState() == 1)
            mTvStatus.setText("未知");
        else if (serviceBean.getState() == 2)
            mTvStatus.setText("成功");
        else if (serviceBean.getState() == 3)
            mTvStatus.setText("失败");

        StringBuffer accessSb = new StringBuffer();
        if (!ObjectUtils.isEmpty(serviceBean.getAccess())){
            for(String s:serviceBean.getAccess())
                accessSb.append(s).append(",");
        }
        mTvServiceAccess.setText(accessSb.toString());

        if (!ObjectUtils.isEmpty(serviceBean.getEndpoint())){
            //服务后端
            StringBuffer serviceDesc = new StringBuffer();

            for (Map.Entry<String, Object> entry : serviceBean.getEndpoint().entrySet()) {
                serviceDesc.append(entry.getKey() + "=").append(entry.getValue()).append(",");
            }
//            mTvServiceDesc.setText(serviceDesc.toString());
        }


        mTvYaml.setText(serviceBean.getYaml());
    }

    @Override
    public void showServiceList(List<ServiceBean> serviceBeans) {

    }

    @Override
    public void showResult(Object o) {
        mAppRefreshHandler.sendBroadCast();
        finish();
    }

    @Override
    public void showIngressInfo(ServiceBean ingressInfo) {

    }

    public class StatusDialog extends Dialog {

        public StatusDialog(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉头
            Window window = getWindow();
            window.setGravity(Gravity.CENTER);
            window.setContentView(R.layout.dialog_service_rule);

            window.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, IngressModificationRuleActivity.class);
                    startActivity(intent);
                    cancel();
                }
            });

            window.setBackgroundDrawable(new BitmapDrawable());
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            setCancelable(false);
            setCanceledOnTouchOutside(false);
        }
    }

    private final static int SERVICE_TOOL_CODE = 0X1;

    @OnClick({R.id.tv_refresh, R.id.btn_toolbox})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_refresh:
                mAppServicePresenter.getServiceDetails(getIntent().getIntExtra(IntentKey.APP_ID, 0),
                        getIntent().getIntExtra(IntentKey.SERVICE_ID, 0), 1);
                break;
            case R.id.btn_toolbox:
                ServiceToolBoxActivity serviceToolBoxActivity = new ServiceToolBoxActivity();
                Bundle bundle = new Bundle();
                bundle.putString(IntentKey.YAML, mServiceBean.getYaml());
                bundle.putInt(IntentKey.APP_ID, mServiceBean.getApp_id());
                bundle.putString(IntentKey.APP_NAME, mServiceBean.getApp_name());
                bundle.putInt(IntentKey.SERVICE_SOURCE, mServiceBean.getSource());
                bundle.putString(IntentKey.SERVICE_NAME, mServiceBean.getName());
                bundle.putInt(IntentKey.SERVICE_TYPE, mServiceBean.getType());
                bundle.putInt(IntentKey.SERVICE_ID, mServiceBean.getId());
                serviceToolBoxActivity.setArguments(bundle);
                serviceToolBoxActivity.show(getSupportFragmentManager(), "blur_sample");

                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == SERVICE_TOOL_CODE){
                int intExtra = data.getIntExtra(IntentKey.SERVICE_TYPE, 0);
                if (intExtra == 2){//删除操作
                    mAppServicePresenter.deleteService(getIntent().getIntExtra(IntentKey.APP_ID, 0),
                            getIntent().getIntExtra(IntentKey.SERVICE_ID, 0));

                }else if(intExtra == 1 ){

//                    AppCreateServiceModel mAppCreateServiceModel = new AppCreateServiceModel(new AppCreateServiceModel.OnAppServiceListener() {
//                        @Override
//                        public void onSuccess(final String serviec_id) {
//
//                            mAppServicePresenter.getServiceDetails(getIntent().getIntExtra(IntentKey.APP_ID, 0),
//                                    getIntent().getIntExtra(IntentKey.SERVICE_ID, 0), 1);
//                        }
//
//                        @Override
//                        public void onFailure(String message) {
//
//                        }
//
//                        @Override
//                        public void onMessage(String text) {
//                        }
//                    });
//
////                    String yaml = mEtCode.getText().toString();
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("app_id", mServiceBean.getApp_id());
//                    map.put("app_name", mServiceBean.getName());
//                    map.put("service_name", mServiceBean.getName());
//                    map.put("service_id", mServiceBean.getId());
//                    map.put("service_type", mServiceBean.getType());
//                    map.put("service_source", mServiceBean.getSource());
//                    map.put("yaml", mServiceBean.getYaml());
//                    String json = TenApp.getInstance().getGsonInstance().toJson(map);
//                    mAppCreateServiceModel.connect();
//                    mAppCreateServiceModel.send(json);


                }

            }
        }
    }
}
