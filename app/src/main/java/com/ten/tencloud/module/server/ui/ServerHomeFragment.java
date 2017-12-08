package com.ten.tencloud.module.server.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.base.view.BaseFragment;
import com.ten.tencloud.bean.ServerBean;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.module.server.adapter.RvServerAdapter;
import com.ten.tencloud.module.server.contract.ServerHomeContract;
import com.ten.tencloud.module.server.presenter.ServerHomePresenter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by lxq on 2017/11/22.
 */

public class ServerHomeFragment extends BaseFragment implements ServerHomeContract.View {

    @BindView(R.id.xrv_servers)
    XRecyclerView mXrvServer;

    private RvServerAdapter mAdapter;
    private ServerHomePresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return createView(inflater, container, R.layout.fragment_server_home);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View header = inflater.inflate(R.layout.header_server_main, null);
        header.findViewById(R.id.tv_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, ServerListActivity.class));
            }
        });
        header.findViewById(R.id.rl_server).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, ServerListActivity.class));
            }
        });
        header.findViewById(R.id.rl_temp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppBaseCache.getInstance().resetAppBaseCache();
                TenApp.getInstance().jumpLoginActivity();
            }
        });
        mXrvServer.addHeaderView(header);
        mXrvServer.setLoadingMoreEnabled(false);
        mXrvServer.setPullRefreshEnabled(false);
        mXrvServer.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mXrvServer.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
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
        mXrvServer.setAdapter(mAdapter);
        mPresenter = new ServerHomePresenter();
        mPresenter.attachView(this);
        mPresenter.getServerList(1);
    }

    @Override
    public void showServerList(List<ServerBean> servers) {
        mAdapter.addData(servers);
    }
}
