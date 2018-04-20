package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.bean.DeploymentBean;
import com.ten.tencloud.bean.ImageBean;
import com.ten.tencloud.bean.ServiceBean;
import com.ten.tencloud.bean.TaskBean;
import com.ten.tencloud.broadcast.RefreshBroadCastHandler;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.listener.OnRefreshListener;
import com.ten.tencloud.module.app.adapter.RvAppDetailImageAdapter;
import com.ten.tencloud.module.app.adapter.RvAppDetailTaskAdapter;
import com.ten.tencloud.module.app.adapter.RvAppServiceAdapter;
import com.ten.tencloud.module.app.adapter.RvAppServiceDeploymentAdapter;
import com.ten.tencloud.module.app.contract.AppDetailContract;
import com.ten.tencloud.module.app.contract.AppImageContract;
import com.ten.tencloud.module.app.presenter.AppDetailPresenter;
import com.ten.tencloud.module.app.presenter.AppImagePresenter;
import com.ten.tencloud.utils.UiUtils;
import com.ten.tencloud.utils.glide.GlideUtils;
import com.ten.tencloud.widget.CircleImageView;
import com.ten.tencloud.widget.blur.BlurBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenxh@10.com on 2018/3/27.
 */
public class AppDetailActivity extends BaseActivity implements AppDetailContract.View, AppImageContract.View {

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
    @BindView(R.id.tv_deploy_more)
    TextView mTvDeployMore;
    @BindView(R.id.rv_app_detail_deployment)
    RecyclerView mRvAppDetailDeploy;
    @BindView(R.id.tv_service_more)
    TextView mTvServiceMore;
    @BindView(R.id.rv_app_detail_service)
    RecyclerView mRvAppDetailService;
    @BindView(R.id.tv_image_more)
    TextView mTvImageMore;
    @BindView(R.id.rv_image)
    RecyclerView mRvImage;
    @BindView(R.id.tv_task_more)
    TextView mTvTaskMore;
    @BindView(R.id.rv_task)
    RecyclerView mRvTask;
    @BindView(R.id.rl_basic_detail)
    ConstraintLayout mRlBasic;
    @BindView(R.id.iv_logo)
    CircleImageView mIvLogo;
    @BindView(R.id.rl_image)
    RelativeLayout mRlImage;
    @BindView(R.id.scroll_view)
    NestedScrollView mScrollView;

    private RvAppServiceDeploymentAdapter mDeploymentAdapter;
    private RvAppServiceAdapter mServiceAdapter;
    private RvAppDetailImageAdapter mImageAdapter;
    private RvAppDetailTaskAdapter mTaskAdapter;

    private ArrayList<DeploymentBean> mDeploymentBeans;
    private ArrayList<ServiceBean> mServiceBeans;
    private ArrayList<ImageBean> mImageBeans;
    private ArrayList<TaskBean> mTaskBeans;
    private AppDetailPresenter mAppDetailPresenter;
    private int mAppId;

    private AppBean mAppBean;
    private AppImagePresenter mAppImagePresenter;
    private RefreshBroadCastHandler mInfoBroadCastHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_service_app_detail);
        initTitleBar(true, "应用详情");

        mAppId = getIntent().getIntExtra("id", -1);
        mAppDetailPresenter = new AppDetailPresenter();
        mAppDetailPresenter.attachView(this);
        mAppImagePresenter = new AppImagePresenter();
        mAppImagePresenter.attachView(this);

        mInfoBroadCastHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.APP_INFO_CHANGE_ACTION);
        mInfoBroadCastHandler.registerReceiver(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });

        initDeploymentView();
        initServiceView();
        initImageView();
        initTaskView();
        initData();

    }

    private void initDeploymentView() {
        mRvAppDetailDeploy.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mDeploymentAdapter = new RvAppServiceDeploymentAdapter(this);
        mRvAppDetailDeploy.setAdapter(mDeploymentAdapter);

        ArrayList<DeploymentBean.Pod> pods = new ArrayList<>();
        pods.add(new DeploymentBean.Pod("预设Pod", 1));
        pods.add(new DeploymentBean.Pod("当前Pod", 1));
        pods.add(new DeploymentBean.Pod("更新Pod", 1));
        pods.add(new DeploymentBean.Pod("可用Pod", 1));
        pods.add(new DeploymentBean.Pod("运行时间", 6));
        ArrayList<DeploymentBean> deploymentBeans = new ArrayList<>();
        deploymentBeans.add(new DeploymentBean("kubernets-bootcamp", 1, pods, "2018-02-15  18:15:12", "AIUnicorn"));
        mDeploymentAdapter.setDatas(deploymentBeans);
    }

    private void initServiceView() {
        mRvAppDetailService.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mServiceAdapter = new RvAppServiceAdapter(this);
        mRvAppDetailService.setAdapter(mServiceAdapter);

        mServiceBeans = new ArrayList<>();
        mServiceBeans.add(new ServiceBean("service-example", "ClusterIp", "10.23.123.9", "<none>", "xxxx", "80/TCP，443/TCP", "2018-02-15  18:15:12", 0));
        mServiceAdapter.setDatas(mServiceBeans);
    }

    private void initImageView() {
        mRvImage.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mImageAdapter = new RvAppDetailImageAdapter(this);
        mRvImage.setAdapter(mImageAdapter);
    }

    private void initTaskView() {
        mRvTask.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mTaskAdapter = new RvAppDetailTaskAdapter(this);
        mRvTask.setAdapter(mTaskAdapter);

        mTaskBeans = new ArrayList<>();
        mTaskBeans.add(new TaskBean("构建镜像 - Djago v1.0.5", "80%", "2018-03-29  10:00:01", "2018-03-29  10:00:11", 0));
        mTaskBeans.add(new TaskBean("kubernests部署 - Djago v1.0.5", "100%", "2018-03-29  11:00:01", "2018-03-29  11:00:11", 1));
        mTaskBeans.add(new TaskBean("docker原生部署 - Djago v1.0.5", "XX%", "2018-03-29  12:00:01", "2018-03-29  12:00:11", 2));
        mTaskAdapter.setDatas(mTaskBeans);
    }

    private void initData() {
        mAppDetailPresenter.getAppById(mAppId);
        mAppImagePresenter.getAppImageById(mAppId + "");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent.getBooleanExtra("viewImage", false)) {
            int top = mRlImage.getTop();
            mScrollView.scrollTo(0, top);
        }
    }

    @OnClick({R.id.rl_basic_detail, R.id.tv_deploy_more, R.id.btn_toolbox,
            R.id.tv_service_more, R.id.tv_image_more, R.id.tv_task_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_basic_detail:
                startActivity(new Intent(this, AppAddActivity.class).putExtra("id", mAppId));
                break;
            case R.id.tv_deploy_more:
                startActivityNoValue(this, AppDeploymentListActivity.class);
                break;
            case R.id.tv_service_more:
                startActivityNoValue(this, AppServiceListActivity.class);
                break;
            case R.id.tv_image_more: {
                Intent intent = new Intent(this, AppImageListActivity.class);
                intent.putExtra("appId", mAppId);
                startActivity(intent);
                break;
            }
            case R.id.tv_task_more:
                startActivityNoValue(this, AppTaskListActivity.class);
                break;
            case R.id.btn_toolbox: {
                BlurBuilder.snapShotWithoutStatusBar(this);
                Intent intent = new Intent(this, AppToolBoxActivity.class);
                intent.putExtra("appBean", mAppBean);
                startActivity(intent);
                overridePendingTransition(0, 0);
                break;
            }
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
        switch (appBean.getStatus()) {
            case Constants.APP_STATUS_INIT:
                mTvStatus.setBackgroundResource(R.drawable.shape_app_status_init_round);
                mTvStatus.setTextColor(UiUtils.getColor(R.color.text_color_09bb07));
                mTvStatus.setText("初创建");
                break;
            case Constants.APP_STATUS_NORMAL:
                mTvStatus.setBackgroundResource(R.drawable.shape_app_status_normal_round);
                mTvStatus.setTextColor(UiUtils.getColor(R.color.text_color_48bbc0));
                mTvStatus.setText("正常");
                break;
            case Constants.APP_STATUS_ERROR:
                mTvStatus.setBackgroundResource(R.drawable.shape_app_status_error_round);
                mTvStatus.setTextColor(UiUtils.getColor(R.color.text_color_ef9a9a));
                mTvStatus.setText("异常");
                break;
        }
    }

    @Override
    public void showImages(List<ImageBean> images) {
        mImageAdapter.setDatas(images);
    }

    @Override
    public void showImageEmpty() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAppDetailPresenter.detachView();
        mAppImagePresenter.detachView();
        mInfoBroadCastHandler.unregisterReceiver();
        mInfoBroadCastHandler = null;
    }
}
