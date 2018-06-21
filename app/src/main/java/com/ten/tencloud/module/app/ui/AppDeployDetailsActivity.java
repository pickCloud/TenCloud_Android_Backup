package com.ten.tencloud.module.app.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.DeploymentBean;
import com.ten.tencloud.bean.DeploymentInfoBean;
import com.ten.tencloud.bean.UpdateRecordBean;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.constants.IntentKey;
import com.ten.tencloud.module.app.adapter.OperationAdapter;
import com.ten.tencloud.module.app.adapter.UpdateRecordAdapter;
import com.ten.tencloud.module.app.contract.AppDeployInfoContract;
import com.ten.tencloud.module.app.contract.AppDeployListContract;
import com.ten.tencloud.module.app.presenter.DeployInfoPresenter;
import com.ten.tencloud.module.app.presenter.DeployListPresenter;
import com.ten.tencloud.utils.UiUtils;
import com.ten.tencloud.widget.blur.BlurBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 部署详情
 */
public class AppDeployDetailsActivity extends BaseActivity implements AppDeployInfoContract.View, AppDeployListContract.View {

    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_relevance_application)
    TextView mTvRelevanceApplication;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.rl_basic_detail)
    ConstraintLayout mRlBasicDetail;
    @BindView(R.id.tv_ysyy)
    TextView mTvYsyy;
    @BindView(R.id.tv_dqyy)
    TextView mTvDqyy;
    @BindView(R.id.tv_kyyy)
    TextView mTvKyyy;
    @BindView(R.id.tv_gxyy)
    TextView mTvGxyy;
    @BindView(R.id.tv_run_time)
    TextView mTvRunTime;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.rec_operation)
    RecyclerView mRecOperation;
    @BindView(R.id.tv_deployment)
    TextView mTvDeployment;
    @BindView(R.id.tv_task_more)
    TextView mTvTaskMore;
    @BindView(R.id.tv_reason)
    TextView mTvReason;
    @BindView(R.id.rec_record)
    RecyclerView mRecRecord;
    //    @BindView(R.id.scroll_view)
//    TenForbidAutoScrollView mScrollView;
    private OperationAdapter mOperationAdapter;

    private List<DeploymentInfoBean> deploymentInfoBeans = new ArrayList<>();
    private List<UpdateRecordBean> updateRecordBeans = new ArrayList<>();
    private UpdateRecordAdapter mUpdateRecordAdapter;
    private DeployInfoPresenter mDeployInfoPresenter;
    private DeployDialog mDeployDialog;
    private DeploymentBean mDeploymentBean;
    private DeployListPresenter mDeployListPresenter;
    private int mAppId;
    private int mDeploymentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_deploy_details);
        ButterKnife.bind(this);
        initTitleBar(true, "部署详情");

        mDeploymentBean = getIntent().getParcelableExtra("deploymentBean");

        mAppId = getIntent().getIntExtra(IntentKey.APP_ID, 0);
        mDeploymentId = getIntent().getIntExtra(IntentKey.DEPLOYMENT_ID, 0);


        mRecOperation.setLayoutManager(new LinearLayoutManager(this));
        mOperationAdapter = new OperationAdapter();
        mRecOperation.setAdapter(mOperationAdapter);

        mRecRecord.setLayoutManager(new LinearLayoutManager(this));
        mUpdateRecordAdapter = new UpdateRecordAdapter();
        mRecRecord.setAdapter(mUpdateRecordAdapter);

        mDeployInfoPresenter = new DeployInfoPresenter();
        mDeployInfoPresenter.attachView(this);

        mDeployListPresenter = new DeployListPresenter();
        mDeployListPresenter.attachView(this);

        mDeployInfoPresenter.deploymentPods(mDeploymentId, null);//获取部署详情

        mDeployListPresenter.getDeployList(mAppId, mDeploymentId,  1);

        mUpdateRecordAdapter.addData(new UpdateRecordBean());
        mUpdateRecordAdapter.addData(new UpdateRecordBean());
        mUpdateRecordAdapter.addData(new UpdateRecordBean());


    }

    void initData(){
        if (!ObjectUtils.isEmpty(mDeploymentBean)){
            mTvName.setText(mDeploymentBean.getName());
            mTvRelevanceApplication.setText("关联应用   应用名称 " + mDeploymentBean.getApp_name());
            mTvDate.setText("创建时间 " + mDeploymentBean.getCreate_time());

            switch (mDeploymentBean.getStatus()) {
                case Constants.DEPLOYMENT_STATUS_INIT:
                    mTvStatus.setBackgroundResource(R.drawable.shape_app_status_init_round);
                    mTvStatus.setCompoundDrawablesWithIntrinsicBounds(UiUtils.getDrawable(R.mipmap.icon_detail_green), null, null, null);
                    mTvStatus.setTextColor(UiUtils.getColor(R.color.text_color_09bb07));
                    mTvStatus.setText("进行中");
                    break;
                case Constants.DEPLOYMENT_STATUS_NORMAL:
                    mTvStatus.setBackgroundResource(R.drawable.shape_app_status_normal_round);
                    mTvStatus.setCompoundDrawablesWithIntrinsicBounds(UiUtils.getDrawable(R.mipmap.icon_detail), null, null, null);
                    mTvStatus.setTextColor(UiUtils.getColor(R.color.text_color_48bbc0));
                    mTvStatus.setText("已完成");
                    break;
                case Constants.DEPLOYMENT_STATUS_ERROR:
                    mTvStatus.setBackgroundResource(R.drawable.shape_app_status_error_round);
                    mTvStatus.setCompoundDrawablesWithIntrinsicBounds(UiUtils.getDrawable(R.mipmap.icon_detail_pink), null, null, null);
                    mTvStatus.setTextColor(UiUtils.getColor(R.color.text_color_ef9a9a));
                    mTvStatus.setText("失败");
                    break;
            }

            mTvYsyy.setText(mDeploymentBean.getReplicas() + "");
            mTvDqyy.setText(mDeploymentBean.getReadyReplicas() + "");
            mTvKyyy.setText(mDeploymentBean.getAvailableReplicas() + "");
            mTvGxyy.setText(mDeploymentBean.getUpdatedReplicas() + "");
            mTvDeployment.setText(mDeploymentBean.getYaml());
        }
    }
    @Override
    public void showEmpty() {

    }

    @Override
    public void showList(List<DeploymentBean> data) {
        if (!ObjectUtils.isEmpty(data) && data.size() > 0){
            mDeploymentBean = data.get(0);
            initData();

        }

    }

    @Override
    public void showDeploymentPodsList(List<DeploymentInfoBean> data) {
        if (ObjectUtils.isEmpty(data))
            mOperationAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.include_empty, null));
        else
            mOperationAdapter.setNewData(data);

    }

    @Override
    public void showDeploymentReplicasList(List<DeploymentInfoBean> data) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDeployInfoPresenter.detachView();
    }

    @OnClick({R.id.tv_refresh, R.id.tv_desc, R.id.btn_toolbox})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_refresh:
                mDeployInfoPresenter.deploymentPods(mDeploymentBean.getId(), null);//获取部署详情

                break;
            case R.id.tv_desc:
                if (mDeployDialog == null) {
                    mDeployDialog = new DeployDialog(mContext);
                }
                mDeployDialog.show();
                break;
            case R.id.btn_toolbox:
                BlurBuilder.snapShotWithoutStatusBar(this);
                Intent intent = new Intent(this, DeployDetailsToolBoxActivity.class);
//                intent.putExtra("appBean", mAppBean);
                startActivity(intent);
                overridePendingTransition(0, 0);
                break;
        }
    }


    public class DeployDialog extends Dialog {

        public DeployDialog(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉头
            Window window = getWindow();
            window.setGravity(Gravity.CENTER);
            window.setContentView(R.layout.dialog_deploy_desc);

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
}
