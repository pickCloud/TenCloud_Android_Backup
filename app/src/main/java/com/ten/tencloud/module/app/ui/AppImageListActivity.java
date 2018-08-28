package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.bean.ImageBean;
import com.ten.tencloud.constants.IntentKey;
import com.ten.tencloud.module.app.adapter.RvAppDetailImageAdapter1;
import com.ten.tencloud.module.app.contract.AppImageContract;
import com.ten.tencloud.module.app.presenter.AppImagePresenter;
import com.ten.tencloud.widget.dialog.AppFilterDialog;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenxh@10.com on 2018/3/27.
 */
public class AppImageListActivity extends BaseActivity implements AppImageContract.View, OnRefreshLoadmoreListener{

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

    private RvAppDetailImageAdapter1 mImageAdapter;

    private AppFilterDialog mAppFilterDialog;
//    private int mAppId;
    private AppImagePresenter mAppImagePresenter;
    private AppBean mAppBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_service_app_list);

        mRlFilter.setVisibility(View.GONE);
        initTitleBar(true, "镜像列表");

        mAppBean = getIntent().getParcelableExtra(IntentKey.APP_ITEM);
//        mAppId = ;
        mAppImagePresenter = new AppImagePresenter();
        mAppImagePresenter.attachView(this);

        mRefresh.setOnRefreshLoadmoreListener(this);

        initView();
        initData();
    }

    private void initView() {
        mRvApp.setLayoutManager(new LinearLayoutManager(this));
        mImageAdapter = new RvAppDetailImageAdapter1(this);
        mRvApp.setAdapter(mImageAdapter);

    }

    private void initData() {
        mAppImagePresenter.getAppListByPage(mAppBean.getId(), false);
    }

    @OnClick({R.id.tv_filter, R.id.tv_add_server})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_filter:
                mAppFilterDialog = new AppFilterDialog(this);
                mAppFilterDialog.show();
                break;
            case R.id.tv_add_server:
                Intent intent = new Intent(this, AppMakeImageStep1Activity.class);
                intent.putExtra(IntentKey.APP_ITEM, mAppBean);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void showImages(List<ImageBean> images, boolean isLoadMore) {
        mEmptyView.setVisibility(View.GONE);
        mRvApp.setVisibility(View.VISIBLE);
        if (isLoadMore){
            mImageAdapter.addData(images);
            mRefresh.finishLoadmore();

        }else {
            mImageAdapter.setDatas(images);
            mRefresh.finishRefresh();

        }

    }

    @Override
    public void showImageEmpty(boolean isLoadMore) {
        if (isLoadMore) {
            showMessage("暂无更多数据");
            mRefresh.finishLoadmore();
        } else {
            mImageAdapter.getDatas().clear();
            mRefresh.finishRefresh();
            mEmptyView.setVisibility(View.VISIBLE);
            mRvApp.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        mAppImagePresenter.getAppListByPage(mAppBean.getId(), true);

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mAppImagePresenter.getAppListByPage(mAppBean.getId(), false);

    }
}
