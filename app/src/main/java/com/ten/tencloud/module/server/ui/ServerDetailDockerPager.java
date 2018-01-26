package com.ten.tencloud.module.server.ui;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BasePager;
import com.ten.tencloud.module.server.adapter.RvServerDetailDockerAdapter;
import com.ten.tencloud.module.server.contract.ServerDockerContract;
import com.ten.tencloud.module.server.presenter.ServerDockerPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by lxq on 2017/11/29.
 */

public class ServerDetailDockerPager extends BasePager implements ServerDockerContract.View {

    @BindView(R.id.xrv_docker)
    RecyclerView mXrvDocker;
    @BindView(R.id.tv_empty)
    TextView mTvEmpty;

    private boolean isFirst = true;
    private RvServerDetailDockerAdapter mAdapter;
    private ServerDockerPresenter mServerDockerPresenter;
    private String mId;

    public ServerDetailDockerPager(Context context) {
        super(context);
        mServerDockerPresenter = new ServerDockerPresenter();
        mServerDockerPresenter.attachView(this);
    }

    @Override
    public void init() {
        if (isFirst) {
            mId = getArgument("id");
            createView(R.layout.pager_server_detail_docker);
            mXrvDocker.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
            mAdapter = new RvServerDetailDockerAdapter(mContext);
            mXrvDocker.setAdapter(mAdapter);
            mServerDockerPresenter.getDockerList(mId);
        }
    }

    @Override
    public void showDocker(List<List<String>> dockers) {
        mTvEmpty.setVisibility(INVISIBLE);
        mAdapter.setDatas(dockers);
        isFirst = false;
    }

    @Override
    public void showEmpty() {
        mTvEmpty.setVisibility(VISIBLE);
        isFirst = false;
    }
}
