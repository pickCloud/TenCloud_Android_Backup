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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.flexbox.FlexboxLayout;
import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.bean.DeploymentBean;
import com.ten.tencloud.bean.MicroserviceBean;
import com.ten.tencloud.bean.ServiceBriefBean;
import com.ten.tencloud.broadcast.RefreshBroadCastHandler;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.constants.IntentKey;
import com.ten.tencloud.listener.OnRefreshListener;
import com.ten.tencloud.module.app.adapter.MicroserviceAdapter;
import com.ten.tencloud.module.app.adapter.RvAppAdapter;
import com.ten.tencloud.module.app.contract.AppDetailContract;
import com.ten.tencloud.module.app.contract.AppListContract;
import com.ten.tencloud.module.app.contract.SubApplicationContract;
import com.ten.tencloud.module.app.presenter.AppDetailPresenter;
import com.ten.tencloud.module.app.presenter.SubApplicationPresenter;
import com.ten.tencloud.utils.glide.GlideUtils;
import com.ten.tencloud.widget.CircleImageView;
import com.ten.tencloud.widget.blur.BlurBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 主应用详情
 */
public class AppMainDetailsActivity extends BaseActivity implements AppDetailContract.View, SubApplicationContract.View {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_main_details);

        ButterKnife.bind(this);
        initTitleBar(true, "主应用详情");

        mAppId = getIntent().getIntExtra(IntentKey.APP_ID, -1);

        RefreshBroadCastHandler mAppHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.APP_LIST_CHANGE_ACTION);
        mAppHandler.registerReceiver(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSubApplicationPresenter.getAppSubApplicationList(mAppId);
            }
        });

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRecMicroservice.setLayoutManager(new LinearLayoutManager(this));
        mMicroserviceAdapter = new MicroserviceAdapter();
        mRecMicroservice.setAdapter(mMicroserviceAdapter);
        mMicroserviceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, AppServiceDetailsActivity.class);
//                intent.putExtra(IntentKey.APP_SUB_ID, appBean.getId());
//                intent.putExtra(IntentKey.APP_ID, appBean.getMaster_app());
                startActivity(intent);
            }
        });

        mRecChildApp.setLayoutManager(new LinearLayoutManager(this));
        mRvAppAdapter = new RvAppAdapter(this);
        mRecChildApp.setAdapter(mRvAppAdapter);
        mRvAppAdapter.setOnItemClickListener(new CJSBaseRecyclerViewAdapter.OnItemClickListener<AppBean>() {
            @Override
            public void onObjectItemClicked(AppBean appBean, int position) {
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

        mSubApplicationPresenter.getAppSubApplicationList(mAppId);
        mAppDetailPresenter.getAppById(mAppId);
        mAppDetailPresenter.getAppServiceBriefById(mAppId);

        //测试
        mMicroserviceAdapter.addData(new MicroserviceBean());
        mMicroserviceAdapter.addData(new MicroserviceBean());
        mMicroserviceAdapter.addData(new MicroserviceBean());

    }

    @OnClick({R.id.rl_basic_detail, R.id.tv_check_time, R.id.btn_toolbox, R.id.tv_ingress_details})
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
                BlurBuilder.snapShotWithoutStatusBar(this);
                intent = new Intent(this, MainPageToolBoxActivity.class);
                intent.putExtra(IntentKey.APP_ITEM, mAppBean);
                startActivityForResult(intent, Constants.APP_DETAILS_DEL);
                overridePendingTransition(0, 0);
                break;
            case R.id.tv_ingress_details:
                intent = new Intent(this, IngressDetailsActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void showAppDetail(AppBean appBean) {
        mAppBean = appBean;
        if (!TextUtils.isEmpty(appBean.getLogo_url()))
            GlideUtils.getInstance().loadCircleImage(mContext, mIvLogo, appBean.getLogo_url(), R.mipmap.icon_app_photo);
        if (!TextUtils.isEmpty(appBean.getName()))
            mTvName.setText(appBean.getName());

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
        mRvAppAdapter.setDatas(appBeans);
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

}
