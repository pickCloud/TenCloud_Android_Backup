package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
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
import com.ten.tencloud.bean.AppServiceHeaderBean;
import com.ten.tencloud.bean.DeploymentBean;
import com.ten.tencloud.bean.ServiceBean;
import com.ten.tencloud.module.app.adapter.RvAppAdapter;
import com.ten.tencloud.module.app.adapter.RvAppServiceDeploymentAdapter;
import com.ten.tencloud.module.app.adapter.RvAppServiceHeaderAdapter;
import com.ten.tencloud.module.app.adapter.RvServiceAdapter;
import com.ten.tencloud.module.app.contract.AppServiceHomeContract;
import com.ten.tencloud.widget.decoration.HorizontalItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenxh@10.com on 2018/3/26.
 */
public class AppServiceFragment extends BaseFragment implements AppServiceHomeContract.View {

    @BindView(R.id.rv_app_service_frag_header)
    RecyclerView mRvAppServiceFragHeader;
    @BindView(R.id.tv_hot_app_more)
    TextView mTvHotAppMore;
    @BindView(R.id.rv_hot_app)
    RecyclerView mRvHotApp;
    @BindView(R.id.tv_add_app)
    TextView mTvAddServer;
    @BindView(R.id.app_empty_view)
    FrameLayout mAppEmptyView;
    @BindView(R.id.tv_newest_deployment_more)
    TextView mTvNewestDeploymentMore;
    @BindView(R.id.rv_newest_deployment)
    RecyclerView mRvNewestDeployment;
    @BindView(R.id.tv_newest_service_more)
    TextView mTvNewestServiceMore;
    @BindView(R.id.rv_newest_service)
    RecyclerView mRvNewestService;
    @BindView(R.id.deployment_empty_view)
    FrameLayout mDeploymentEmptyView;
    @BindView(R.id.service_empty_view)
    FrameLayout mServiceEmptyView;

    private RvAppServiceHeaderAdapter mAppServiceHeaderAdapter;
    private RvAppAdapter mAppAdapter;
    private RvAppServiceDeploymentAdapter mDeploymentAdapter;
    private RvServiceAdapter mServiceAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = createView(inflater, container, R.layout.fragment_app_service);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewHeader();
        initViewApp();
        initViewDeployment();
        initViewService();
        initData();
    }

    private void initViewHeader() {
        mRvAppServiceFragHeader.setLayoutManager(new GridLayoutManager(mActivity, 5) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mAppServiceHeaderAdapter = new RvAppServiceHeaderAdapter(mActivity);
        mRvAppServiceFragHeader.setAdapter(mAppServiceHeaderAdapter);
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
                startActivity(new Intent(mActivity, AppDetailActivity.class));
            }
        });

    }

    private void initViewDeployment() {
        mRvNewestDeployment.setLayoutManager(new LinearLayoutManager(mActivity) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mDeploymentAdapter = new RvAppServiceDeploymentAdapter(mActivity);
        mRvNewestDeployment.setAdapter(mDeploymentAdapter);
    }

    private void initViewService() {
        mRvNewestService.setLayoutManager(new LinearLayoutManager(mActivity) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRvNewestService.addItemDecoration(new HorizontalItemDecoration());
        mServiceAdapter = new RvServiceAdapter(mActivity);
        mRvNewestService.setAdapter(mServiceAdapter);
    }

    private void initData() {
        ArrayList<AppServiceHeaderBean> appServiceHeaderBeans = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            appServiceHeaderBeans.add(new AppServiceHeaderBean(i, "模拟数据" + i));
        }
        ArrayList<AppBean> appBeans = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            appBeans.add(new AppBean());
        }
        ArrayList<DeploymentBean> deploymentBeans = new ArrayList<>();
        deploymentBeans.add(new DeploymentBean());
        ArrayList<ServiceBean> serviceBeans = new ArrayList<>();
        serviceBeans.add(new ServiceBean());

        mAppServiceHeaderAdapter.setDatas(appServiceHeaderBeans);
        mAppAdapter.setDatas(appBeans);
        mDeploymentAdapter.setDatas(deploymentBeans);
        mServiceAdapter.setDatas(serviceBeans);
    }

    @OnClick({R.id.tv_add_app, R.id.tv_hot_app_more, R.id.tv_newest_deployment_more, R.id.tv_newest_service_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add_app:
                startActivity(new Intent(mActivity, AppAddActivity.class));
                break;
            case R.id.tv_hot_app_more:
                startActivity(new Intent(mActivity, AppListActivity.class));
                break;
            case R.id.tv_newest_deployment_more:
                break;
            case R.id.tv_newest_service_more:
                break;
        }
    }

    @Override
    public void showEmptyView() {
        mAppEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
