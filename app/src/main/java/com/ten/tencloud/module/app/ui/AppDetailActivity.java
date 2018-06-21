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
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.google.android.flexbox.FlexboxLayout;
import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.bean.DeploymentBean;
import com.ten.tencloud.bean.ImageBean;
import com.ten.tencloud.bean.ServiceBean;
import com.ten.tencloud.bean.ServiceBriefBean;
import com.ten.tencloud.bean.TaskBean;
import com.ten.tencloud.broadcast.RefreshBroadCastHandler;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.constants.IntentKey;
import com.ten.tencloud.even.DeployEven;
import com.ten.tencloud.listener.OnRefreshListener;
import com.ten.tencloud.module.app.adapter.RvAppDetailImageAdapter;
import com.ten.tencloud.module.app.adapter.RvAppDetailTaskAdapter;
import com.ten.tencloud.module.app.adapter.RvAppServiceAdapter;
import com.ten.tencloud.module.app.adapter.RvAppServiceDeploymentAdapter;
import com.ten.tencloud.module.app.contract.AppDetailContract;
import com.ten.tencloud.module.app.contract.AppImageContract;
import com.ten.tencloud.module.app.contract.SubApplicationContract;
import com.ten.tencloud.module.app.presenter.AppDetailPresenter;
import com.ten.tencloud.module.app.presenter.AppImagePresenter;
import com.ten.tencloud.module.app.presenter.SubApplicationPresenter;
import com.ten.tencloud.utils.UiUtils;
import com.ten.tencloud.utils.glide.GlideUtils;
import com.ten.tencloud.widget.CircleImageView;
import com.ten.tencloud.widget.blur.BlurBuilder;
import com.ten.tencloud.widget.dialog.ServerSystemLoadDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 子应用详情
 * Created by chenxh@10.com on 2018/3/27.
 */
public class AppDetailActivity extends BaseActivity implements SubApplicationContract.View, AppImageContract.View{

    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.fbl_label)
    FlexboxLayout mFblLabel;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.iv_arrow)
    ImageView mIvArrow;
//    @BindView(R.id.tv_check_time)
//    TextView mTvCheckTime;
    @BindView(R.id.tv_deploy_more)
    TextView mTvDeployMore;
    @BindView(R.id.rv_app_detail_deployment)
    RecyclerView mRvAppDetailDeploy;
//    @BindView(R.id.tv_service_more)
//    TextView mTvServiceMore;
//    @BindView(R.id.rv_app_detail_service)
//    RecyclerView mRvAppDetailService;
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
//    private AppDetailPresenter mAppDetailPresenter;
    private int mAppId;

    private AppBean mAppBean;
    private AppImagePresenter mAppImagePresenter;
    private RefreshBroadCastHandler mInfoBroadCastHandler;
    private StatusDialog mStatusDialog;
    private SubApplicationPresenter mSubApplicationPresenter;
    private int mMasterAppId;

    @Override
    protected boolean isBindEventBus() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_service_app_detail);
        initTitleBar(true, "子应用详情");

        mAppId = getIntent().getIntExtra("id", -1);
        mMasterAppId = getIntent().getIntExtra("master_app", -1);

        mSubApplicationPresenter = new SubApplicationPresenter();
        mSubApplicationPresenter.attachView(this);

//        mAppDetailPresenter = new AppDetailPresenter();
//        mAppDetailPresenter.attachView(this);
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
//        initServiceView();
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
        mDeploymentAdapter.setOnItemClickListener(new CJSBaseRecyclerViewAdapter.OnItemClickListener<DeploymentBean>() {
            @Override
            public void onObjectItemClicked(DeploymentBean deploymentBean, int position) {
                Intent intent = new Intent(mContext, AppDeployDetailsActivity.class);
                intent.putExtra(IntentKey.APP_ID, deploymentBean.getApp_id());
                intent.putExtra(IntentKey.DEPLOYMENT_ID, deploymentBean.getId());
                startActivity(intent);
            }
        });

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
        mSubApplicationPresenter.getDeploymentLatestById(mAppId);//获取最新部署
        mAppImagePresenter.getAppImageById(mAppId);//获取镜像
        mSubApplicationPresenter.getSubApplicationListById(mMasterAppId, mAppId);//获取子应用详情

    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent.getBooleanExtra("viewImage", false)) {
            mAppImagePresenter.getAppImageById(mAppId);
            int top = mRlImage.getTop();
            mScrollView.scrollTo(0, top);
        }
    }

    @OnClick({R.id.rl_basic_detail, R.id.tv_deploy_more, R.id.btn_toolbox,
            /*R.id.tv_service_more,*/ R.id.tv_image_more, R.id.tv_task_more, R.id.tv_status_description})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.rl_basic_detail:
                startActivity(new Intent(this, AppAddActivity.class).putExtra("id", mAppId).putExtra("master_app", mMasterAppId).putExtra("type", 1));
                break;
            case R.id.tv_deploy_more:
                intent = new Intent(this, AppDeployListActivity.class);
                intent.putExtra(IntentKey.APP_ID, mAppId);
                startActivity(intent);
                break;
//            case R.id.tv_service_more:
//                startActivityNoValue(this, AppServiceListActivity.class);
//                break;
            case R.id.tv_image_more: {
                intent = new Intent(this, AppImageListActivity.class);
                intent.putExtra(IntentKey.APP_ID, mAppId);
                startActivity(intent);
                break;
            }
            case R.id.tv_task_more:
                startActivityNoValue(this, AppTaskListActivity.class);
                break;
            case R.id.btn_toolbox: {
                BlurBuilder.snapShotWithoutStatusBar(this);
                intent = new Intent(this, AppToolBoxActivity.class);
                intent.putExtra(IntentKey.APP_ITEM, mAppBean);
                startActivityForResult(intent, Constants.SUB_APP_DEL);
                overridePendingTransition(0, 0);
                break;
            }
            case R.id.tv_status_description:
                if (mStatusDialog == null) {
                    mStatusDialog = new StatusDialog(mContext);
                }
                mStatusDialog.show();
                break;
        }
    }

//    @Override
//    public void showAppDetail(AppBean appBean) {
//        mAppBean = appBean;
//        if (!TextUtils.isEmpty(appBean.getLogo_url()))
//            GlideUtils.getInstance().loadCircleImage(mContext, mIvLogo, appBean.getLogo_url(), R.mipmap.icon_app_photo);
//        if (!TextUtils.isEmpty(appBean.getName()))
//            mTvName.setText(appBean.getName());
//
//        String label_name = appBean.getLabel_name();
//        if (!TextUtils.isEmpty(label_name)) {
//            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
//            mFblLabel.removeAllViews();
//            String[] labels = label_name.split(",");
//            for (String label : labels) {
//                TextView tvLabel = (TextView) inflater.inflate(R.layout.item_app_service_label_default, mFblLabel, false);
//                tvLabel.setText(label);
//                mFblLabel.addView(tvLabel);
//            }
//        }
//        switch (appBean.getStatus()) {
//            case Constants.APP_STATUS_INIT:
//                mTvStatus.setBackgroundResource(R.drawable.shape_app_status_init_round);
//                mTvStatus.setTextColor(UiUtils.getColor(R.color.text_color_09bb07));
//                mTvStatus.setText("初创建");
//                break;
//            case Constants.APP_STATUS_NORMAL:
//                mTvStatus.setBackgroundResource(R.drawable.shape_app_status_normal_round);
//                mTvStatus.setTextColor(UiUtils.getColor(R.color.text_color_48bbc0));
//                mTvStatus.setText("正常");
//                break;
//            case Constants.APP_STATUS_ERROR:
//                mTvStatus.setBackgroundResource(R.drawable.shape_app_status_error_round);
//                mTvStatus.setTextColor(UiUtils.getColor(R.color.text_color_ef9a9a));
//                mTvStatus.setText("异常");
//                break;
//        }
//    }
//
//    @Override
//    public void showServiceBriefDetails(ServiceBriefBean serverBatchBean) {
//
//    }
//
//    @Override
//    public void showImages(List<ImageBean> images) {
//        mImageAdapter.setDatas(images);
//    }
//
//    @Override
//    public void showImageEmpty() {
//
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubApplicationPresenter.detachView();
        mInfoBroadCastHandler.unregisterReceiver();
        mInfoBroadCastHandler = null;
    }

    @Override
    public void showSubApplicationList(List<AppBean> appBeans) {

    }

    @Override
    public void showSubApplicationDetails(AppBean appBean) {
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
    public void showDeploymentLatestDetails(DeploymentBean deploymentBean) {
        if (!ObjectUtils.isEmpty(deploymentBean)){
            List<DeploymentBean> deploymentBeans = new ArrayList<>();
            deploymentBeans.add(deploymentBean);
            mDeploymentAdapter.setDatas(deploymentBeans);
        }

    }

    @Override
    public void showImages(List<ImageBean> images) {
        mImageAdapter.setDatas(images);
    }

    @Override
    public void showImageEmpty() {

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
            window.setContentView(R.layout.dialog_statements);

            window.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == Constants.SUB_APP_DEL){
                setResult(RESULT_OK);
                finish();
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateLastDeploy(DeployEven deployEven){
        mSubApplicationPresenter.getDeploymentLatestById(mAppId);//获取最新部署

    }
}
