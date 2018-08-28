package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.bean.DeploymentBean;
import com.ten.tencloud.broadcast.RefreshBroadCastHandler;
import com.ten.tencloud.constants.IntentKey;
import com.ten.tencloud.listener.OnRefreshListener;
import com.ten.tencloud.module.app.adapter.RvAppServiceDeploymentAdapter;
import com.ten.tencloud.module.app.contract.AppDeployListContract;
import com.ten.tencloud.module.app.contract.AppDetailContract;
import com.ten.tencloud.module.app.contract.AppListContract;
import com.ten.tencloud.module.app.presenter.DeployListPresenter;
import com.ten.tencloud.widget.decoration.Hor16Ver8ItemDecoration;
import com.ten.tencloud.widget.dialog.AppFilterDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 部署列表
 * Created by chenxh@10.com on 2018/3/27.
 */
public class AppDeployListActivity extends BaseActivity implements AppDeployListContract.View, OnRefreshLoadmoreListener
{

    @BindView(R.id.rl_filter)
    RelativeLayout mRlFilter;
    @BindView(R.id.tv_filter)
    TextView mTvFilter;
    @BindView(R.id.rv_app)
    RecyclerView mRvApp;
    @BindView(R.id.empty_view)
    FrameLayout mEmptyView;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;

    private RefreshBroadCastHandler mAppHandler;
    private RvAppServiceDeploymentAdapter mAppServiceDeploymentAdapter;

    private AppFilterDialog mAppFilterDialog;
    private DeployListPresenter mDeployListPresenter;

    private boolean isRefrsh;
    private int mAppId;
    private int mPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_service_app_list);

        mAppId = getIntent().getIntExtra(IntentKey.APP_ID, 0);

        initTitleBar(true, "部署列表");

        mAppHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.APP_LIST_CHANGE_ACTION);
        mAppHandler.registerReceiver(new OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });

        mRefresh.setOnRefreshLoadmoreListener(this);
        mDeployListPresenter = new DeployListPresenter();
        mDeployListPresenter.attachView(this);

        initView();
        initData();

    }

    private void initView() {
        mRvApp.setLayoutManager(new LinearLayoutManager(this));
        mAppServiceDeploymentAdapter = new RvAppServiceDeploymentAdapter();
//        mRvApp.addItemDecoration(new Hor16Ver8ItemDecoration());
        mRvApp.setAdapter(mAppServiceDeploymentAdapter);
        mAppServiceDeploymentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DeploymentBean deploymentBean = (DeploymentBean) adapter.getData().get(position);

                Intent intent = new Intent(mContext, AppDeployDetailsActivity.class);
                intent.putExtra(IntentKey.APP_ID, deploymentBean.getApp_id());
                intent.putExtra(IntentKey.DEPLOYMENT_ID, deploymentBean.getId());
                startActivity(intent);
            }
        });

    }

    private void initData() {

        mDeployListPresenter.getDeployList(mAppId, null, null, null, null, null, mPage, isRefrsh? false: true);
//        mAppServiceDeploymentAdapter.setDatas(deploymentBeans);
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
        mDeployListPresenter.detachView();

    }

    @Override
    public void showEmpty() {
        if (!isRefrsh){
            showMessage("暂无更多数据");
            mRefresh.finishLoadmore();

        }else {

        }

    }

    @Override
    public void showList(List<DeploymentBean> data) {
        if (isRefrsh){
            mRefresh.finishRefresh();
            mAppServiceDeploymentAdapter.setNewData(data);
        }
        else{
            if (!ObjectUtils.isEmpty(data))
                mPage ++ ;

            mAppServiceDeploymentAdapter.addData(data);
            mRefresh.finishLoadmore();

        }

    }

    @Override
    public void showResult(Object o) {
        isRefrsh = true;
        mPage = 1;
        initData();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        isRefrsh = false;
        initData();

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        isRefrsh = true;
        mPage = 1;
        initData();

    }
}
