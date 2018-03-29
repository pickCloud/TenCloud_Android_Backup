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
import com.ten.tencloud.widget.decoration.ServiceItemDecoration;

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
        mRvAppDetailDeploy.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mDeploymentAdapter = new RvAppDetailDeploymentAdapter(this);
        mRvAppDetailDeploy.setAdapter(mDeploymentAdapter);

        ArrayList<DeploymentBean.Pod> pods = new ArrayList<>();
        pods.add(new DeploymentBean.Pod("预设Pod", 1));
        pods.add(new DeploymentBean.Pod("当前Pod", 1));
        pods.add(new DeploymentBean.Pod("更新Pod", 1));
        pods.add(new DeploymentBean.Pod("可用Pod", 1));
        pods.add(new DeploymentBean.Pod("运行时间", 6));
        ArrayList<DeploymentBean> deploymentBeans = new ArrayList<>();
        deploymentBeans.add(new DeploymentBean("kubernets-bootcamp", 1, pods, "2018-2-15 18:15:12", "AIUnicorn"));
        mDeploymentAdapter.setDatas(deploymentBeans);
    }

    private void initServiceView() {
        mRvAppDetailService.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
//        mRvAppDetailService.addItemDecoration(new ServiceItemDecoration());
        mServiceAdapter = new RvServiceAdapter(this);
        mRvAppDetailService.setAdapter(mServiceAdapter);

        mServiceBeans = new ArrayList<>();
        mServiceBeans.add(new ServiceBean("Service-example", "ClusterIp", "10.23.123.9", "<none>", "xxxx", "80/TCP,443/TCP", "2018-2-15 18:15:12"));
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

        mImageBeans = new ArrayList<>();
        mImageBeans.add(new ImageBean("Diango1", "V1.0.1", "2018-3-29 10:0:01"));
        mImageBeans.add(new ImageBean("Diango2", "V1.0.2", "2018-3-29 10:0:10"));
        mImageBeans.add(new ImageBean("Diango3", "V1.0.3", "2018-3-29 10:0:21"));
        mImageAdapter.setDatas(mImageBeans);
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
        mTaskBeans.add(new TaskBean("构建镜像 - Djago v1.0.5",80,"2018-3-29 10:0:01","2018-3-29 10:0:11",0));
        mTaskBeans.add(new TaskBean("kubernests部署 - Djago v1.0.5",100,"2018-3-29 11:0:01","2018-3-29 11:0:11",1));
        mTaskBeans.add(new TaskBean("docker原生部署 - Djago v1.0.5",30,"2018-3-29 12:0:01","2018-3-29 12:0:11",-1));
        mTaskAdapter.setDatas(mTaskBeans);
    }

    private void initData() {


    }

    @OnClick({R.id.iv_arrow, R.id.tv_deploy_more, R.id.tv_service_more, R.id.tv_image_more, R.id.tv_task_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_arrow:
                startActivityNoValue(this, AppAddActivity.class);
                break;
            case R.id.tv_deploy_more:
                startActivityNoValue(this, DeploymentListActivity.class);
                break;
            case R.id.tv_service_more:
                startActivityNoValue(this, ServiceListActivity.class);
                break;
            case R.id.tv_image_more:
                startActivityNoValue(this, ImageListActivity.class);
                break;
            case R.id.tv_task_more:
                startActivityNoValue(this, TaskListActivity.class);
                break;
        }
    }
}
