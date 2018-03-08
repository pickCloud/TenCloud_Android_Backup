package com.ten.tencloud.module.server.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.base.view.BaseFragment;
import com.ten.tencloud.bean.ServerBean;
import com.ten.tencloud.broadcast.RefreshBroadCastHandler;
import com.ten.tencloud.listener.OnRefreshListener;
import com.ten.tencloud.module.server.adapter.RvServerAdapter;
import com.ten.tencloud.module.server.contract.ServerHomeContract;
import com.ten.tencloud.module.server.presenter.ServerHomePresenter;
import com.ten.tencloud.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lxq on 2017/11/22.
 */

public class ServerHomeFragment extends BaseFragment implements ServerHomeContract.View {

    @BindView(R.id.xrv_servers)
    RecyclerView mRvServer;
    @BindView(R.id.empty_view)
    View mEmptyView;
    @BindView(R.id.tv_add_server)
    TextView mTvAddServer;

    private RvServerAdapter mAdapter;
    private ServerHomePresenter mPresenter;
    private boolean mPermissionAddServer;

    private RefreshBroadCastHandler mPermissionRefreshBroadCastHandler;
    private RefreshBroadCastHandler mSwitchCompanyRefreshBroadCastHandler;
    private RefreshBroadCastHandler mServerRefreshHandler;

    @BindView(R.id.tv_total)
    TextView mTvServerTotal;
    @BindView(R.id.tv_alarm)
    TextView mTvServerAlarm;
    @BindView(R.id.tv_cluster)
    TextView mTvServerCluster;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return createView(inflater, container, R.layout.fragment_server_home);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        OnRefreshListener onRefreshListener = new OnRefreshListener() {
            @Override
            public void onRefresh() {
                initView();
            }
        };

        mPermissionRefreshBroadCastHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.PERMISSION_REFRESH_ACTION);
        mPermissionRefreshBroadCastHandler.registerReceiver(onRefreshListener);
        mSwitchCompanyRefreshBroadCastHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.SWITCH_COMPANY_REFRESH_ACTION);
        mSwitchCompanyRefreshBroadCastHandler.registerReceiver(onRefreshListener);
        mServerRefreshHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.SERVER_LIST_CHANGE_ACTION);
        mServerRefreshHandler.registerReceiver(onRefreshListener);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;//禁止滑动，处理与scrollview的冲突
            }
        };
        mRvServer.setLayoutManager(layoutManager);
        mAdapter = new RvServerAdapter(mActivity);
        mAdapter.setOnItemClickListener(new CJSBaseRecyclerViewAdapter.OnItemClickListener<ServerBean>() {
            @Override
            public void onObjectItemClicked(ServerBean serverBean, int position) {
                Intent intent = new Intent(mActivity, ServerDetailActivity.class);
                intent.putExtra("name", serverBean.getName());
                intent.putExtra("id", serverBean.getId());
                startActivity(intent);
            }
        });
        mRvServer.setAdapter(mAdapter);
        mRvServer.setFocusableInTouchMode(false);//处理自动滚动
        mPresenter = new ServerHomePresenter();
        mPresenter.attachView(this);
        initView();
    }

    private void initView() {
        mPresenter.getWarnServerList(1);
        mPermissionAddServer = Utils.hasPermission("添加主机");
        mTvAddServer.setVisibility(mPermissionAddServer ? View.VISIBLE : View.GONE);
        mPresenter.summary();
    }

    @OnClick({R.id.tv_add_server, R.id.tv_more, R.id.rl_server, R.id.rl_cluster, R.id.rl_alarm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add_server:
                startActivity(new Intent(mActivity, ServerAddActivity.class));
                break;
            case R.id.tv_more:
                startActivity(new Intent(mActivity, ServerListActivity.class));
                break;
            case R.id.rl_server:
                startActivity(new Intent(mActivity, ServerListActivity.class));
                break;
            case R.id.rl_cluster:

                break;
            case R.id.rl_alarm:

                break;
        }
    }

    @Override
    public void showWarnServerList(List<ServerBean> servers) {
        mAdapter.setDatas(servers);
        mEmptyView.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyView() {
        mEmptyView.setVisibility(View.VISIBLE);
        mAdapter.clear();
    }

    @Override
    public void showSummary(int server_num, int warn_num, int payment_num) {
        mTvServerTotal.setText(server_num + "");
        mTvServerAlarm.setText(warn_num + "");
        mTvServerCluster.setText(payment_num + "");
        mTvServerTotal.setSelected(server_num != 0);
        mTvServerAlarm.setSelected(warn_num != 0);
        mTvServerCluster.setSelected(payment_num != 0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPermissionRefreshBroadCastHandler.unregisterReceiver();
        mPermissionRefreshBroadCastHandler = null;
        mSwitchCompanyRefreshBroadCastHandler.unregisterReceiver();
        mSwitchCompanyRefreshBroadCastHandler = null;
        mServerRefreshHandler.unregisterReceiver();
        mServerRefreshHandler = null;
        mPresenter.detachView();
    }
}
