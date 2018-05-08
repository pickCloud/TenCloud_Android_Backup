package com.ten.tencloud.module.server.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.ClusterBean;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.module.server.adapter.RvServerClusterAdapter;
import com.ten.tencloud.module.server.contract.ServerClusterListContract;
import com.ten.tencloud.module.server.presenter.ServerClusterListPresenter;
import com.ten.tencloud.widget.dialog.CommonDialog;

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
    private boolean mSelect; //选择集群

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_server_cluster_list);
        mSelect = getIntent().getBooleanExtra("select", false);
        if (mSelect) {
            initTitleBar(true, "选择集群");
        } else {
            initTitleBar(true, "集群列表", R.menu.menu_add_cluster, new OnMenuItemClickListener() {
                @Override
                public void onItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.menu_create_cluster) {
                        startActivityNoValue(mContext, ServerClusterCreateActivity.class);
                    }
                }
            });
        }

        mServerClusterListPresenter = new ServerClusterListPresenter();
        mServerClusterListPresenter.attachView(this);

        initView();
        initData();
    }

    private void initView() {
        mRvCluster.setLayoutManager(new LinearLayoutManager(this));
        mClusterAdapter = new RvServerClusterAdapter(this);
        if (mSelect) {
            mClusterAdapter.setOnItemClickListener(new CJSBaseRecyclerViewAdapter.OnItemClickListener<ClusterBean>() {
                @Override
                public void onObjectItemClicked(final ClusterBean clusterBean, int position) {
                    new CommonDialog(mContext)
                            .setMessage("选择该集群？")
                            .setPositiveButton("确定", new CommonDialog.OnButtonClickListener() {
                                @Override
                                public void onClick(Dialog dialog) {
                                    dialog.dismiss();
                                    Intent data = new Intent();
                                    data.putExtra("master_server_id", clusterBean.getMaster_server_id());
                                    data.putExtra("clusterName", clusterBean.getName());
                                    setResult(Constants.ACTIVITY_RESULT_CODE_FINISH, data);
                                    finish();
                                }
                            })
                            .show();
                }
            });
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mServerClusterListPresenter.detachView();
        mServerClusterListPresenter = null;
    }
}
