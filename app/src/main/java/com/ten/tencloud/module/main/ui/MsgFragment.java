package com.ten.tencloud.module.main.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseFragment;
import com.ten.tencloud.bean.MessageBean;
import com.ten.tencloud.module.main.adapter.RvMsgAdapter;
import com.ten.tencloud.module.main.contract.MsgContract;
import com.ten.tencloud.module.main.presenter.MsgPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by lxq on 2018/1/10.
 */

public class MsgFragment extends BaseFragment implements MsgContract.View {

    private String status = "";
    private String mode = "";

    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;
    @BindView(R.id.rv_msg)
    RecyclerView mRvMsg;
    @BindView(R.id.tv_empty)
    TextView mTvEmpty;
    private RvMsgAdapter mMsgAdapter;
    private MsgPresenter mMsgPresenter;

    private boolean isFirst = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return createView(inflater, container, R.layout.fragment_msg);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        status = getArguments().getString("status");
        mMsgPresenter = new MsgPresenter();
        mMsgPresenter.attachView(this);
        if ("0".equals(status)) {
            initView();
        }
    }

    @Override
    public void onVisible() {
        if (isFirst) {
            initView();
        }
    }

    private void initView() {
        mRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mMsgPresenter.getMsgList(false, status, mode);
            }

            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mMsgPresenter.getMsgList(true, status, mode);
            }
        });
        mRvMsg.setLayoutManager(new LinearLayoutManager(mActivity));
        mMsgAdapter = new RvMsgAdapter(mActivity);
        mRvMsg.setAdapter(mMsgAdapter);
        mRefresh.autoRefresh();
    }

    public void search(String key, String mode) {
        this.mode = mode;
        mMsgPresenter.search(status, this.mode, key);
    }

    @Override
    public void showEmpty(boolean isLoadMore) {
        isFirst = false;
        if (isLoadMore) {
            showMessage("暂无更多数据");
            mRefresh.finishLoadmore();
        } else {
            mMsgAdapter.clear();
            mRefresh.finishRefresh();
            mTvEmpty.setVisibility(View.VISIBLE);
            mRvMsg.setVisibility(View.GONE);
        }
    }

    @Override
    public void showMsgList(List<MessageBean> msg, boolean isLoadMore) {
        isFirst = false;
        mTvEmpty.setVisibility(View.GONE);
        mRvMsg.setVisibility(View.VISIBLE);
        if (isLoadMore) {
            mMsgAdapter.addData(msg);
            mRefresh.finishLoadmore();
        } else {
            mMsgAdapter.setDatas(msg);
            mRefresh.finishRefresh();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMsgPresenter != null) {
            mMsgPresenter.detachView();
        }
    }
}
