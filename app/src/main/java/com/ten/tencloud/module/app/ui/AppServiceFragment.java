package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseFragment;
import com.ten.tencloud.bean.AppServiceHeaderBean;
import com.ten.tencloud.module.app.adapter.RvAppServiceHeaderAdapter;
import com.ten.tencloud.module.app.contract.AppServiceHomeContract;

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

    private RvAppServiceHeaderAdapter mRvAppServiceHeaderAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = createView(inflater, container, R.layout.fragment_app_service);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        ArrayList<AppServiceHeaderBean> data = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            data.add(new AppServiceHeaderBean(i, "模拟数据" + i));
        }
        mRvAppServiceHeaderAdapter.setDatas(data);
    }

    private void initView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 5) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRvAppServiceHeaderAdapter = new RvAppServiceHeaderAdapter(mActivity);
        mRvAppServiceFragHeader.setLayoutManager(gridLayoutManager);
        mRvAppServiceFragHeader.setAdapter(mRvAppServiceHeaderAdapter);

        mAppEmptyView.setVisibility(View.VISIBLE);
        mDeploymentEmptyView.setVisibility(View.VISIBLE);
        mServiceEmptyView.setVisibility(View.VISIBLE);
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
