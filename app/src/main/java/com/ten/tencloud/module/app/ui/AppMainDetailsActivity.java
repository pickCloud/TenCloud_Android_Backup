package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.flexbox.FlexboxLayout;
import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.bean.DeploymentBean;
import com.ten.tencloud.bean.PathBean;
import com.ten.tencloud.bean.RulesBean;
import com.ten.tencloud.bean.ServiceBean;
import com.ten.tencloud.bean.ServiceBriefBean;
import com.ten.tencloud.broadcast.RefreshBroadCastHandler;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.constants.IntentKey;
import com.ten.tencloud.listener.OnRefreshListener;
import com.ten.tencloud.module.app.adapter.MicroserviceAdapter;
import com.ten.tencloud.module.app.adapter.RvAppAdapter;
import com.ten.tencloud.module.app.contract.AppDetailContract;
import com.ten.tencloud.module.app.contract.AppServiceContract;
import com.ten.tencloud.module.app.contract.SubApplicationContract;
import com.ten.tencloud.module.app.presenter.AppDetailPresenter;
import com.ten.tencloud.module.app.presenter.AppServicePresenter;
import com.ten.tencloud.module.app.presenter.SubApplicationPresenter;
import com.ten.tencloud.utils.glide.GlideUtils;
import com.ten.tencloud.widget.CircleImageView;
import com.ten.tencloud.widget.blur.BlurBuilder;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 主应用详情
 */
public class AppMainDetailsActivity extends BaseActivity implements AppDetailContract.View, SubApplicationContract.View, AppServiceContract.View {

    @BindView(R.id.iv_logo)
    CircleImageView mIvLogo;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.fbl_label)
    FlexboxLayout mFblLabel;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.tv_check_time)
    TextView mTvCheckTime;
    @BindView(R.id.rec_microservice)
    RecyclerView mRecMicroservice;
    @BindView(R.id.rl_image)
    RelativeLayout mRlImage;
    @BindView(R.id.rec_child_app)
    RecyclerView mRecChildApp;
    @BindView(R.id.btn_toolbox)
    Button mBtnToolbox;
    @BindView(R.id.tv_ingress_name)
    TextView mTvIngressName;
    @BindView(R.id.tv_ingress_rule)
    TextView mTvIngressRule;
    private RvAppAdapter mRvAppAdapter;
    private MicroserviceAdapter mMicroserviceAdapter;
    private int mAppId;
    private AppDetailPresenter mAppDetailPresenter;
    private AppBean mAppBean;
    private SubApplicationPresenter mSubApplicationPresenter;
    private AppServicePresenter mAppServicePresenter;
    private ServiceBean mIngressInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_main_details);

        ButterKnife.bind(this);
        initTitleBar(true, "主应用详情");

        mAppId = getIntent().getIntExtra(IntentKey.APP_ID, -1);

        RefreshBroadCastHandler mAppHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.APP_LIST_CHANGE_ACTION);
        RefreshBroadCastHandler mAppDelete = new RefreshBroadCastHandler(RefreshBroadCastHandler.APP_LIST_CHANGE_DELETE);
        mAppDelete.registerReceiver(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                setResult(RESULT_OK);
                finish();
            }
        });
        mAppHandler.registerReceiver(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSubApplicationPresenter.getAppSubApplicationList(mAppId);
                mAppServicePresenter.getServiceList(mAppId);

            }
        });

        mRecMicroservice.setLayoutManager(new LinearLayoutManager(this));
        mMicroserviceAdapter = new MicroserviceAdapter();
        mRecMicroservice.setAdapter(mMicroserviceAdapter);
        mMicroserviceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ServiceBean serviceBean = (ServiceBean) adapter.getData().get(position);
                Intent intent = new Intent(mContext, AppServiceDetailsActivity.class);
                intent.putExtra(IntentKey.SERVICE_ID, serviceBean.getId());
                intent.putExtra(IntentKey.APP_ID, serviceBean.getApp_id());
                startActivity(intent);
            }
        });

        mRecChildApp.setLayoutManager(new LinearLayoutManager(this));
        mRvAppAdapter = new RvAppAdapter();
        mRecChildApp.setAdapter(mRvAppAdapter);
        mRvAppAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AppBean appBean = (AppBean) adapter.getData().get(position);
                Intent intent = new Intent(mContext, AppSubDetailActivity.class);
                intent.putExtra(IntentKey.APP_SUB_ID, appBean.getId());
                intent.putExtra(IntentKey.APP_ID, appBean.getMaster_app());
                startActivityForResult(intent, Constants.SUB_APP_DETAILS);
            }
        });


        mAppDetailPresenter = new AppDetailPresenter();
        mAppDetailPresenter.attachView(this);

        mSubApplicationPresenter = new SubApplicationPresenter();
        mSubApplicationPresenter.attachView(this);

        mAppServicePresenter = new AppServicePresenter();
        mAppServicePresenter.attachView(this);

        mSubApplicationPresenter.getAppSubApplicationList(mAppId);
        mAppDetailPresenter.getAppById(mAppId);
        mAppDetailPresenter.getAppServiceBriefById(mAppId);

        mAppServicePresenter.getServiceList(mAppId);
        mAppServicePresenter.ingressInfo(mAppId, 1);


    }

    @OnClick({R.id.rl_basic_detail, R.id.tv_check_time, R.id.btn_toolbox, R.id.tv_ingress_details, R.id.tv_rule_setting})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.rl_basic_detail:
                intent = new Intent(this, AppAddActivity.class);
                intent.putExtra(IntentKey.APP_ID, mAppBean.getId());
                startActivity(intent);
                break;
            case R.id.tv_check_time:
                break;
            case R.id.btn_toolbox:
//                BlurBuilder.snapShotWithoutStatusBar(this);
//                intent = new Intent(this, MainPageToolBoxActivity.class);
//                intent.putExtra(IntentKey.APP_ITEM, mAppBean);
//                startActivityForResult(intent, Constants.APP_DETAILS_DEL);
//                overridePendingTransition(0, 0);

                MainPageToolBoxActivity mainPageToolBoxActivity = new MainPageToolBoxActivity();
                Bundle bundle = new Bundle();
                bundle.putParcelable(IntentKey.APP_ITEM, mAppBean);
                mainPageToolBoxActivity.setArguments(bundle);
                mainPageToolBoxActivity.show(getSupportFragmentManager(), "blur_sample");
                break;
            case R.id.tv_ingress_details:
//                if (mIngressInfo == null)
//                    return;

                intent = new Intent(this, IngressDetailsActivity.class);
                intent.putExtra(IntentKey.INGRESS_INFO, mIngressInfo);
                intent.putExtra(IntentKey.APP_ID, mAppBean.getId());
                startActivity(intent);
                break;
            case R.id.tv_rule_setting:
                intent = new Intent(this, IngressModificationRuleActivity.class);
                intent.putExtra(IntentKey.INGRESS_INFO, mIngressInfo);
                intent.putExtra(IntentKey.APP_ID, mAppBean.getId());
                startActivity(intent);
                break;
        }
    }

    @Override
    public void showAppDetail(AppBean appBean) {
        mAppBean = appBean;
        if (!TextUtils.isEmpty(appBean.getLogo_url())) {
            GlideUtils.getInstance().loadCircleImage(mContext, mIvLogo, appBean.getLogo_url(), R.mipmap.icon_app_photo);
        }
        if (!TextUtils.isEmpty(appBean.getName())) {
            mTvName.setText(appBean.getName());
        }

        String label_name = appBean.getLabel_name();
        if (!TextUtils.isEmpty(label_name)) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            mFblLabel.removeAllViews();
            String[] labels = label_name.split(",");
            for (String label : labels) {
                TextView tvLabel = (TextView) inflater.inflate(R.layout.item_app_service_label_default, mFblLabel, false);
                tvLabel.setText(label);
                mFblLabel.addView(tvLabel);
            }
        }
    }

    @Override
    public void showServiceBriefDetails(ServiceBriefBean serverBatchBean) {

    }

    @Override
    public void showSubApplicationList(List<AppBean> appBeans) {
        mRvAppAdapter.setNewData(appBeans);
        if (ObjectUtils.isEmpty(appBeans)) {
            mRvAppAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.include_empty, null));
        }
    }

    @Override
    public void showSubApplicationDetails(AppBean appBean) {

    }

    @Override
    public void showDeploymentLatestDetails(DeploymentBean appBean) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAppDetailPresenter.detachView();
        mSubApplicationPresenter.detachView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.SUB_APP_DETAILS) {
                mSubApplicationPresenter.getAppSubApplicationList(mAppId);

            } else if (requestCode == Constants.APP_DETAILS_DEL) {
                setResult(RESULT_OK);
                finish();
            }
        }
    }

    @Override
    public void showServiceDetails(ServiceBean serviceBean) {

    }

    @Override
    public void showServiceList(List<ServiceBean> serviceBeans) {
        mMicroserviceAdapter.setNewData(serviceBeans);

        if (ObjectUtils.isEmpty(serviceBeans)) {
            mMicroserviceAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.include_empty, null));
        }
    }

    @Override
    public void showResult(Object o) {

    }

    @Override
    public void showIngressInfo(ServiceBean ingressInfo) {
        if (ingressInfo == null)
            return;
        mIngressInfo = ingressInfo;
        mTvIngressName.setText(ingressInfo.getName());

        if (!ObjectUtils.isEmpty(ingressInfo.getRules())) {
            StringBuffer stringBuffer = new StringBuffer();

            for (int i = 0; i < ingressInfo.getRules().size(); i++) {

                RulesBean rulesBean = ingressInfo.getRules().get(i);
                stringBuffer.append(rulesBean.host).append("\n");
                for (PathBean pathBean : rulesBean.paths) {
                    stringBuffer.append("   " + pathBean.path + " => ")
                            .append("" + pathBean.serviceName + ":")
                            .append("" + pathBean.servicePort + "\n");
                }

            }

            mTvIngressRule.setText(stringBuffer.toString());

        }

    }
}
