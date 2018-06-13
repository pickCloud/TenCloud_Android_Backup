package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.bean.MicroserviceBean;
import com.ten.tencloud.bean.ServiceBriefBean;
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
public class AppMainPageDetailsActivity extends BaseActivity implements AppDetailContract.View, SubApplicationContract.View{

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
    private RvAppAdapter mRvAppAdapter;
    private MicroserviceAdapter mMicroserviceAdapter;
    private int mAppId;
    private AppListContract mAppListContract;
    private AppDetailPresenter mAppDetailPresenter;
    private AppBean mAppBean;
    private SubApplicationPresenter mSubApplicationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main_page_details);
        ButterKnife.bind(this);

        mAppId = getIntent().getIntExtra("id", -1);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRecMicroservice.setLayoutManager(gridLayoutManager);
        mMicroserviceAdapter = new MicroserviceAdapter();
        mRecMicroservice.setAdapter(mMicroserviceAdapter);

        mRecChildApp.setLayoutManager(new LinearLayoutManager(this));
        mRvAppAdapter = new RvAppAdapter(this);
        mRecChildApp.setAdapter(mRvAppAdapter);


        mAppDetailPresenter = new AppDetailPresenter();
        mAppDetailPresenter.attachView(this);

        mSubApplicationPresenter = new SubApplicationPresenter();
        mSubApplicationPresenter.attachView(this);

        mSubApplicationPresenter.getAppSubApplicationList(mAppId);
        mAppDetailPresenter.getAppById(mAppId);
        mAppDetailPresenter.getAppServiceBriefById(mAppId);

    }

    @OnClick({R.id.rl_basic_detail, R.id.tv_check_time, R.id.btn_toolbox})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_basic_detail:
                break;
            case R.id.tv_check_time:
                break;
            case R.id.btn_toolbox:
                BlurBuilder.snapShotWithoutStatusBar(this);
                Intent intent = new Intent(this, MainPageToolBoxActivity.class);
//                intent.putExtra("appBean", mAppBean);
                startActivity(intent);
                overridePendingTransition(0, 0);
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
}
