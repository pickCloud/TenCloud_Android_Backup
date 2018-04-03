package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.socks.library.KLog;
import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.broadcast.RefreshBroadCastHandler;
import com.ten.tencloud.listener.OnRefreshListener;
import com.ten.tencloud.module.app.adapter.RvAppAdapter;
import com.ten.tencloud.module.app.contract.AppListContract;
import com.ten.tencloud.module.app.presenter.AppListPresenter;
import com.ten.tencloud.widget.dialog.AppFilterDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenxh@10.com on 2018/3/27.
 */
public class AppListActivity extends BaseActivity implements AppListContract.View {

    @BindView(R.id.tv_filter)
    TextView mTvFilter;
    @BindView(R.id.rv_app)
    RecyclerView mRvApp;
    @BindView(R.id.tv_add_app)
    TextView mTvAddApp;
    @BindView(R.id.empty_view)
    FrameLayout mEmptyView;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;

    private RefreshBroadCastHandler mAppHandler;
    private ArrayList<AppBean> mAppBeans;
    private RvAppAdapter mAppAdapter;

    private AppFilterDialog mAppFilterDialog;
    private AppListPresenter mAppListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_service_app_list);

        initTitleBar(true, "应用列表", R.menu.menu_add_app, new OnMenuItemClickListener() {
            @Override
            public void onItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_add_app:
                        startActivityNoValue(mContext, AppAddActivity.class);
                        break;
                }
            }
        });

        mAppListPresenter = new AppListPresenter();
        mAppListPresenter.attachView(this);

        mAppHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.APP_LIST_CHANGE_ACTION);
        mAppHandler.registerReceiver(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAppListPresenter.getAppListByPage(false);
            }
        });

        initView();

    }

    private void initView() {
        mRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mAppListPresenter.getAppListByPage(false);
            }

            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mAppListPresenter.getAppListByPage(true);

            }
        });
        mRefresh.autoRefresh();

        mRvApp.setLayoutManager(new LinearLayoutManager(this));
        mAppAdapter = new RvAppAdapter(this);
        mAppAdapter.setOnItemClickListener(new CJSBaseRecyclerViewAdapter.OnItemClickListener<AppBean>() {
            @Override
            public void onObjectItemClicked(AppBean appBean, int position) {
                startActivity(new Intent(AppListActivity.this, AppDetailActivity.class).putExtra("id", appBean.getId()));
            }
        });
        mRvApp.setAdapter(mAppAdapter);

        mAppFilterDialog = new AppFilterDialog(this);
        mAppFilterDialog.setAppFilterListener(new AppFilterDialog.AppFilterListener() {
            @Override
            public void getFilterData() {

            }

            @Override
            public void onOkClick(Map<String, Map<String, Boolean>> select) {

            }

        });
    }

    @OnClick({R.id.tv_filter, R.id.tv_add_app})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_filter:
                mAppFilterDialog = new AppFilterDialog(this);
                mAppFilterDialog.show();
                break;
            case R.id.tv_add_app:
                startActivityNoValue(this, AppAddActivity.class);
                break;
        }
    }

    @Override
    public void showEmpty(boolean isLoadMore) {
        if (isLoadMore) {
            showMessage("暂无更多数据");
            mRefresh.finishLoadmore();
        } else {
            mAppAdapter.clear();
            mRefresh.finishRefresh();
            mEmptyView.setVisibility(View.VISIBLE);
            mRvApp.setVisibility(View.GONE);
        }
    }

    @Override
    public void showAppList(List<AppBean> msg, boolean isLoadMore) {
        mEmptyView.setVisibility(View.GONE);
        mRvApp.setVisibility(View.VISIBLE);
        if (isLoadMore) {
            mAppAdapter.addData(msg);
            mRefresh.finishLoadmore();
        } else {
            mAppAdapter.setDatas(msg);
            mRefresh.finishRefresh();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAppHandler.unregisterReceiver();
        mAppHandler = null;
        mAppListPresenter.detachView();
    }
}
