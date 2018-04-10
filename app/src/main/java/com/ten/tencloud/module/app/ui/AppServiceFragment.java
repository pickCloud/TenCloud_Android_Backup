package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.base.view.BaseFragment;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.bean.AppBrief;
import com.ten.tencloud.bean.DeploymentBean;
import com.ten.tencloud.bean.ServiceBean;
import com.ten.tencloud.broadcast.RefreshBroadCastHandler;
import com.ten.tencloud.listener.OnRefreshListener;
import com.ten.tencloud.module.app.adapter.RvAppAdapter;
import com.ten.tencloud.module.app.adapter.RvAppServiceDeploymentAdapter;
import com.ten.tencloud.module.app.adapter.RvServiceAdapter;
import com.ten.tencloud.module.app.contract.AppServiceHomeContract;
import com.ten.tencloud.module.app.presenter.AppServiceHomePresenter;
import com.ten.tencloud.widget.decoration.Hor16Ver8ItemDecoration;
import com.ten.tencloud.widget.decoration.ServiceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenxh@10.com on 2018/3/26.
 */
public class AppServiceFragment extends BaseFragment implements AppServiceHomeContract.View {

    @BindView(R.id.tv_hot_app_more)
    TextView mTvHotAppMore;
    @BindView(R.id.rv_hot_app)
    RecyclerView mRvHotApp;
    @BindView(R.id.tv_add_app)
    TextView mTvAddServer;
    @BindView(R.id.app_empty_view)
    FrameLayout mAppEmptyView;
    @BindView(R.id.tv_deployment_more)
    TextView mTvNewestDeploymentMore;
    @BindView(R.id.rv_newest_deployment)
    RecyclerView mRvNewestDeployment;
    @BindView(R.id.tv_service_more)
    TextView mTvNewestServiceMore;
    @BindView(R.id.rv_service)
    RecyclerView mRvNewestService;
    @BindView(R.id.deployment_empty_view)
    FrameLayout mDeploymentEmptyView;
    @BindView(R.id.service_empty_view)
    FrameLayout mServiceEmptyView;
    @BindView(R.id.tv_app_count)
    TextView mTvAppCount;
    @BindView(R.id.tv_deploy_count)
    TextView mTvDeployCount;
    @BindView(R.id.tv_service_count)
    TextView mTvServiceCount;


    private RvAppAdapter mAppAdapter;
    private RvAppServiceDeploymentAdapter mDeploymentAdapter;
    private RvServiceAdapter mServiceAdapter;
    private AppServiceHomePresenter mAppServiceHomePresenter;
    private RefreshBroadCastHandler mRefreshBroadCastHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = createView(inflater, container, R.layout.fragment_app_service);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRefreshBroadCastHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.APP_LIST_CHANGE_ACTION);
        mRefreshBroadCastHandler.registerReceiver(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAppServiceHomePresenter.getAppList();
            }
        });
        initViewApp();
        initViewDeployment();
        initViewService();
        initData();

        mAppServiceHomePresenter = new AppServiceHomePresenter();
        mAppServiceHomePresenter.attachView(this);
        mAppServiceHomePresenter.getAppBrief();
        mAppServiceHomePresenter.getAppList();

    }

    private void initViewApp() {
        mRvHotApp.setLayoutManager(new LinearLayoutManager(mActivity) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mAppAdapter = new RvAppAdapter(mActivity);
        mRvHotApp.setAdapter(mAppAdapter);
        mAppAdapter.setOnItemClickListener(new CJSBaseRecyclerViewAdapter.OnItemClickListener<AppBean>() {
            @Override
            public void onObjectItemClicked(AppBean appBean, int position) {
                startActivity(new Intent(mActivity, AppDetailActivity.class).putExtra("id", appBean.getId()));
            }
        });
    }

    private void initViewDeployment() {
        mRvNewestDeployment.addItemDecoration(new Hor16Ver8ItemDecoration());
        mRvNewestDeployment.setLayoutManager(new LinearLayoutManager(mActivity) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mDeploymentAdapter = new RvAppServiceDeploymentAdapter(mActivity);
        mRvNewestDeployment.setAdapter(mDeploymentAdapter);

        ArrayList<DeploymentBean.Pod> pods = new ArrayList<>();
        pods.add(new DeploymentBean.Pod("预设Pod", 1));
        pods.add(new DeploymentBean.Pod("当前Pod", 1));
        pods.add(new DeploymentBean.Pod("更新Pod", 1));
        pods.add(new DeploymentBean.Pod("可用Pod", 1));
        pods.add(new DeploymentBean.Pod("运行时间", 8));
        ArrayList<DeploymentBean> deploymentBeans = new ArrayList<>();
        deploymentBeans.add(new DeploymentBean("kubernets-bootcamp", 1, pods, "2018-02-15  18:15:12", "AIUnicorn"));
        mDeploymentAdapter.setDatas(deploymentBeans);
    }

    private void initViewService() {
        mRvNewestService.setLayoutManager(new LinearLayoutManager(mActivity) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRvNewestService.addItemDecoration(new ServiceItemDecoration());
        mServiceAdapter = new RvServiceAdapter(mActivity);
        mRvNewestService.setAdapter(mServiceAdapter);

        ArrayList<ServiceBean> serviceBeans = new ArrayList<>();
        serviceBeans.add(new ServiceBean("service-example", "ClusterIp", "10.23.123.9", "<none>", "xxxx", "80/TCP，443/TCP", "2018-02-15  18:15:12", 0));
        mServiceAdapter.setDatas(serviceBeans);
    }

    private void initData() {


    }

    @OnClick({R.id.tv_add_app, R.id.tv_hot_app_more, R.id.tv_deployment_more, R.id.tv_service_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add_app:
                startActivity(new Intent(mActivity, AppAddActivity.class));
                break;
            case R.id.tv_hot_app_more:
                startActivity(new Intent(mActivity, AppListActivity.class));
                break;
            case R.id.tv_deployment_more:
                startActivity(new Intent(mActivity, DeploymentListActivity.class));
                break;
            case R.id.tv_service_more:
                startActivity(new Intent(mActivity, ServiceListActivity.class));
                break;
        }
    }

    @Override
    public void showEmptyView(int type) {
        if (type == AppServiceHomeContract.APP_EMPTY_VIEW) {
            mAppEmptyView.setVisibility(View.VISIBLE);
            mRvHotApp.setVisibility(View.GONE);
        } else if (type == AppServiceHomeContract.DEPLOY_EMPTY_VIEW) {
            mDeploymentEmptyView.setVisibility(View.VISIBLE);
            mRvNewestDeployment.setVisibility(View.GONE);
        } else {
            mServiceEmptyView.setVisibility(View.VISIBLE);
            mRvNewestService.setVisibility(View.GONE);
        }
    }

    @Override
    public void showAppBfief(AppBrief appBrief) {
        mTvAppCount.setText(String.valueOf(appBrief.getApp_num()));
        mTvDeployCount.setText(String.valueOf(appBrief.getDeploy_num()));
        mTvServiceCount.setText(String.valueOf(appBrief.getService_num()));
    }

    @Override
    public void showAppList(List<AppBean> appBeanList) {
        mAppEmptyView.setVisibility(View.GONE);
        mAppAdapter.setDatas(appBeanList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAppServiceHomePresenter.detachView();
    }
}
