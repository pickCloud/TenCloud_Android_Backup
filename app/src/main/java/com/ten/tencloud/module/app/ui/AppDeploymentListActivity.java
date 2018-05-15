package com.ten.tencloud.module.app.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.DeploymentBean;
import com.ten.tencloud.broadcast.RefreshBroadCastHandler;
import com.ten.tencloud.listener.OnRefreshListener;
import com.ten.tencloud.module.app.adapter.RvAppServiceDeploymentAdapter;
import com.ten.tencloud.widget.decoration.Hor16Ver8ItemDecoration;
import com.ten.tencloud.widget.dialog.AppFilterDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenxh@10.com on 2018/3/27.
 */
public class AppDeploymentListActivity extends BaseActivity {

    @BindView(R.id.rl_filter)
    RelativeLayout mRlFilter;
    @BindView(R.id.tv_filter)
    TextView mTvFilter;
    @BindView(R.id.rv_app)
    RecyclerView mRvApp;
    @BindView(R.id.empty_view)
    FrameLayout mEmptyView;

    private RefreshBroadCastHandler mAppHandler;
    private RvAppServiceDeploymentAdapter mAppServiceDeploymentAdapter;

    private AppFilterDialog mAppFilterDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_service_app_list);

        mRlFilter.setVisibility(View.GONE);
        initTitleBar(true, "部署列表");

        mAppHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.APP_LIST_CHANGE_ACTION);
        mAppHandler.registerReceiver(new OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });

        initView();
        initData();
    }

    private void initView() {
        mRvApp.setLayoutManager(new LinearLayoutManager(this));
        mAppServiceDeploymentAdapter = new RvAppServiceDeploymentAdapter(this);
        mRvApp.addItemDecoration(new Hor16Ver8ItemDecoration());
        mRvApp.setAdapter(mAppServiceDeploymentAdapter);

    }

    private void initData() {
        ArrayList<DeploymentBean> deploymentBeans = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            ArrayList<DeploymentBean.Pod> pods = new ArrayList<>();
            pods.add(new DeploymentBean.Pod("预设Pod", 1));
            pods.add(new DeploymentBean.Pod("当前Pod", 1));
            pods.add(new DeploymentBean.Pod("更新Pod", 1));
            pods.add(new DeploymentBean.Pod("可用Pod", 1));
            pods.add(new DeploymentBean.Pod("运行时间", 8));
            deploymentBeans.add(new DeploymentBean("kubernets-bootcamp", i, pods, "2018-02-15  18:15:12", "AIUnicorn"));
        }
        mAppServiceDeploymentAdapter.setDatas(deploymentBeans);
    }

    @OnClick({R.id.tv_filter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_filter:
                mAppFilterDialog = new AppFilterDialog(this);
                mAppFilterDialog.show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAppHandler.unregisterReceiver();
        mAppHandler = null;
    }
}
