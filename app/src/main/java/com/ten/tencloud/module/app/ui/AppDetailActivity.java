package com.ten.tencloud.module.app.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.DeploymentBean;
import com.ten.tencloud.bean.ImageBean;
import com.ten.tencloud.bean.ServiceBean;
import com.ten.tencloud.bean.TaskBean;
import com.ten.tencloud.module.app.adapter.RvAppDetailDeploymentAdapter;
import com.ten.tencloud.module.app.adapter.RvAppDetailImageAdapter;
import com.ten.tencloud.module.app.adapter.RvAppDetailTaskAdapter;
import com.ten.tencloud.module.app.adapter.RvServiceAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenxh@10.com on 2018/3/27.
 */
public class AppDetailActivity extends BaseActivity {

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

    private RvAppDetailDeploymentAdapter mDeploymentAdapter;
    private RvServiceAdapter mServiceAdapter;
    private RvAppDetailImageAdapter mImageAdapter;
    private RvAppDetailTaskAdapter mTaskAdapter;

    private ArrayList<DeploymentBean> mDeploymentBeans;
    private ArrayList<ServiceBean> mServiceBeans;
    private ArrayList<ImageBean> mImageBeans;
    private ArrayList<TaskBean> mTaskBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_service_app_detail);
        initTitleBar(true, "应用详情");

        initDeploymentView();
        initServiceView();
        initImageView();
        initTaskView();
        initData();

    }

    private void initDeploymentView() {
        mRvAppDetailDeploy.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mDeploymentAdapter = new RvAppDetailDeploymentAdapter(this);
        mRvAppDetailDeploy.setAdapter(mDeploymentAdapter);
    }

    private void initServiceView() {
        mRvAppDetailService.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mServiceAdapter = new RvServiceAdapter(this);
        mRvAppDetailService.setAdapter(mServiceAdapter);
    }

    private void initImageView() {
        mRvImage.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mImageAdapter = new RvAppDetailImageAdapter(this);
        mRvImage.setAdapter(mImageAdapter);
    }

    private void initTaskView() {
        mRvTask.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mTaskAdapter = new RvAppDetailTaskAdapter(this);
        mRvTask.setAdapter(mTaskAdapter);
    }

    private void initData() {
        mDeploymentBeans = new ArrayList<>();
        mServiceBeans = new ArrayList<>();
        mImageBeans = new ArrayList<>();
        mTaskBeans = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            mDeploymentBeans.add(new DeploymentBean());
        }
        for (int i = 0; i < 1; i++) {
            mServiceBeans.add(new ServiceBean());
        }
        for (int i = 0; i < 3; i++) {
            mImageBeans.add(new ImageBean());
        }
        for (int i = 0; i < 3; i++) {
            mTaskBeans.add(new TaskBean());
        }
        mDeploymentAdapter.setDatas(mDeploymentBeans);
        mServiceAdapter.setDatas(mServiceBeans);
        mImageAdapter.setDatas(mImageBeans);
        mTaskAdapter.setDatas(mTaskBeans);
    }

    @OnClick({R.id.iv_arrow, R.id.tv_deploy_more, R.id.tv_service_more, R.id.tv_image_more, R.id.tv_task_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_arrow:
                break;
            case R.id.tv_deploy_more:
                break;
            case R.id.tv_service_more:
                break;
            case R.id.tv_image_more:
                break;
            case R.id.tv_task_more:
                break;
        }
    }
}
