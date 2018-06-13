package com.ten.tencloud.module.app.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.DeploymentInfoBean;
import com.ten.tencloud.bean.UpdateRecordBean;
import com.ten.tencloud.module.app.adapter.OperationAdapter;
import com.ten.tencloud.module.app.adapter.UpdateRecordAdapter;
import com.ten.tencloud.module.app.contract.AppDeployInfoContract;
import com.ten.tencloud.module.app.presenter.DeployInfoPresenter;
import com.ten.tencloud.widget.blur.BlurBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AppDeployDetailsActivity extends BaseActivity implements AppDeployInfoContract.View {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_deploy_details);
        ButterKnife.bind(this);

        mRecOperation.setLayoutManager(new LinearLayoutManager(this));
        mOperationAdapter = new OperationAdapter();
        mRecOperation.setAdapter(mOperationAdapter);

        mRecRecord.setLayoutManager(new LinearLayoutManager(this));
        mUpdateRecordAdapter = new UpdateRecordAdapter();
        mRecRecord.setAdapter(mUpdateRecordAdapter);

        mDeployInfoPresenter = new DeployInfoPresenter();
        mDeployInfoPresenter.attachView(this);


        mOperationAdapter.addData(new DeploymentInfoBean());
        mOperationAdapter.addData(new DeploymentInfoBean());
        mOperationAdapter.addData(new DeploymentInfoBean());

        mUpdateRecordAdapter.addData(new UpdateRecordBean());
        mUpdateRecordAdapter.addData(new UpdateRecordBean());
        mUpdateRecordAdapter.addData(new UpdateRecordBean());


    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showDeploymentPodsList(List<DeploymentInfoBean> data) {

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
