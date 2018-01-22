package com.ten.tencloud.module.main.ui;

import android.content.Intent;
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
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseFragment;
import com.ten.tencloud.bean.MessageBean;
import com.ten.tencloud.constants.GlobalStatusManager;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.module.login.ui.JoinComStep2Activity;
import com.ten.tencloud.module.main.adapter.RvMsgAdapter;
import com.ten.tencloud.module.main.contract.MsgContract;
import com.ten.tencloud.module.main.presenter.MsgPresenter;
import com.ten.tencloud.module.user.ui.CompanyInfoActivity;
import com.ten.tencloud.module.user.ui.EmployeeListActivity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

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
        mMsgAdapter.setOnBtnClickListener(new RvMsgAdapter.OnBtnClickListener() {
            @Override
            public void onClick(int subMode, String tip) {
                mSubMode = subMode;
                mTip = tip;
                String[] tips = tip.split(":");
                String cid = tips[0];
                //判断是否还在改公司里
                mMsgPresenter.checkCompany(Integer.parseInt(cid));
            }
        });
        mRvMsg.setAdapter(mMsgAdapter);
        mRefresh.autoRefresh();
    }

    private int mSubMode;
    private String mTip;

    private void handClickByMode(int subMode, String tip) {
        String[] tips = tip.split(":");
        String cid = tips[0];
        AppBaseCache.getInstance().setCid(Integer.parseInt(cid));
        GlobalStatusManager.getInstance().setCompanyListNeedRefresh(true);
        switch (subMode) {
            //马上审核
            case 0:
                Observable.just("").delay(50, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                mActivity.startActivity(new Intent(mActivity, EmployeeListActivity.class));
                            }
                        });
                TenApp.getInstance().jumpMainActivity();
                break;
            //重新提交
            case 1:
                Intent intent = new Intent(mActivity, JoinComStep2Activity.class);
                intent.putExtra("code", tips[1]);
                mActivity.startActivity(intent);
                break;
            //进入企业
            case 2:
                TenApp.getInstance().jumpMainActivity();
                break;
            //马上查看
            case 3:
                Observable.just("").delay(50, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                mActivity.startActivity(new Intent(mActivity, CompanyInfoActivity.class));
                            }
                        });

                TenApp.getInstance().jumpMainActivity();
                break;
        }
    }

    @Override
    public void jumpPage(boolean isEmployee) {
        if (isEmployee) {
            handClickByMode(mSubMode, mTip);
        } else {
            showMessage("消息已过期");
        }
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
    public void onDestroy() {
        super.onDestroy();
        if (mMsgPresenter != null) {
            mMsgPresenter.detachView();
        }
    }
}
