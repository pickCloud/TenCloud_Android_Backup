package com.ten.tencloud.module.server.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.ClusterBean;
import com.ten.tencloud.module.server.adapter.RvServerClusterAdapter;
import com.ten.tencloud.module.server.contract.ServerClusterListContract;
import com.ten.tencloud.module.server.presenter.ServerClusterListPresenter;

import java.util.List;

import butterknife.BindView;

public class ServerClusterListActivity extends BaseActivity implements ServerClusterListContract.View {

    @BindView(R.id.rv_cluster)
    RecyclerView mRvCluster;
    @BindView(R.id.empty_view)
    View mEmptyView;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;
    private RvServerClusterAdapter mClusterAdapter;
    private ServerClusterListPresenter mServerClusterListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_server_cluster_list);
        initTitleBar(true, "集群列表", R.menu.menu_add_cluster, new OnMenuItemClickListener() {
            @Override
            public void onItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_create_cluster) {
                    startActivityNoValue(mContext, ServerClusterCreateActivity.class);
                }
            }
        });

        mServerClusterListPresenter = new ServerClusterListPresenter();
        mServerClusterListPresenter.attachView(this);

        initView();
        initData();
    }

    private void initView() {
        mRvCluster.setLayoutManager(new LinearLayoutManager(this));
        mClusterAdapter = new RvServerClusterAdapter(this);
        mRvCluster.setAdapter(mClusterAdapter);
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mServerClusterListPresenter.getClusterList();
            }
        });
    }

    private void initData() {
        mRefresh.autoRefresh();
    }

    @Override
    public void showClusterList(List<ClusterBean> data) {
        mEmptyView.setVisibility(View.GONE);
        mRvCluster.setVisibility(View.VISIBLE);
        mClusterAdapter.setDatas(data);
        mRefresh.finishRefresh();
    }

    @Override
    public void showEmpty() {
        mRefresh.finishRefresh();
        mEmptyView.setVisibility(View.VISIBLE);
        mRvCluster.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
