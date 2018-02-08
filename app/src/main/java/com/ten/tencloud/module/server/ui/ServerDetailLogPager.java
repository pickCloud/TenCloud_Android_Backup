package com.ten.tencloud.module.server.ui;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BasePager;
import com.ten.tencloud.bean.ServerLogBean;
import com.ten.tencloud.module.server.adapter.RvServerLogAdapter;
import com.ten.tencloud.module.server.contract.ServerLogContract;
import com.ten.tencloud.module.server.presenter.ServerLogPresenter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.List;

import butterknife.BindView;

/**
 * Created by lxq on 2017/11/29.
 */

public class ServerDetailLogPager extends BasePager implements ServerLogContract.View {

    @BindView(R.id.rv_server_log)
    RecyclerView mRvServerLog;
    @BindView(R.id.empty_view)
    View mEmptyView;

    private boolean isFirst = true;
    private RvServerLogAdapter mLogAdapter;
    private final ServerLogPresenter mLogPresenter;
    private String mId;

    public ServerDetailLogPager(Context context) {
        super(context);
        mLogPresenter = new ServerLogPresenter();
        mLogPresenter.attachView(this);
    }

    @Override
    public void init() {
        if (isFirst) {
            mId = getArgument("id");
            createView(R.layout.pager_server_detail_log);
            mRvServerLog.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
            mLogAdapter = new RvServerLogAdapter(mContext);
            mRvServerLog.setAdapter(mLogAdapter);
            StickyRecyclerHeadersDecoration stickyRecyclerHeadersDecoration = new StickyRecyclerHeadersDecoration(mLogAdapter);
            mRvServerLog.addItemDecoration(stickyRecyclerHeadersDecoration);
            mLogPresenter.getServerLogList(mId);
        }
    }

    @Override
    public void showServerLogList(List<ServerLogBean.LogInfo> servers) {
        mEmptyView.setVisibility(INVISIBLE);
        mLogAdapter.setDatas(servers);
        isFirst = false;
    }

    @Override
    public void showEmpty() {
        mLogAdapter.clear();
        mEmptyView.setVisibility(VISIBLE);
    }
}
