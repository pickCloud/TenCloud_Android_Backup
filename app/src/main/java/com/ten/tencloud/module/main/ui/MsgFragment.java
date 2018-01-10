package com.ten.tencloud.module.main.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseFragment;
import com.ten.tencloud.bean.MessageBean;
import com.ten.tencloud.module.main.contract.MsgContract;
import com.ten.tencloud.module.main.presenter.MsgPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by lxq on 2018/1/10.
 */

public class MsgFragment extends BaseFragment implements MsgContract.View {

    private String status = "";

    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;
    @BindView(R.id.rv_msg)
    RecyclerView mRvMsg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return createView(inflater, container, R.layout.fragment_msg);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        status = getArguments().getString("status");
        MsgPresenter msgPresenter = new MsgPresenter();
        msgPresenter.getMsgList(false, status, 0);
    }

    @Override
    public void showEmpty(boolean isLoadMore) {

    }

    @Override
    public void showMsgList(List<MessageBean> msg, boolean isLoadMore) {

    }
}
