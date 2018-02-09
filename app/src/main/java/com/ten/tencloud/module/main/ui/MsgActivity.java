package com.ten.tencloud.module.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.MessageBean;
import com.ten.tencloud.constants.GlobalStatusManager;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.module.login.ui.JoinComStep2Activity;
import com.ten.tencloud.module.main.adapter.RvMsgAdapter;
import com.ten.tencloud.module.main.contract.MsgContract;
import com.ten.tencloud.module.main.model.MsgModel;
import com.ten.tencloud.module.main.presenter.MsgPresenter;
import com.ten.tencloud.module.server.ui.ServerAddActivity;
import com.ten.tencloud.module.server.ui.ServerListActivity;
import com.ten.tencloud.module.user.ui.CompanyInfoActivity;
import com.ten.tencloud.module.user.ui.EmployeeListActivity;
import com.ten.tencloud.utils.Utils;
import com.ten.tencloud.widget.StatusSelectPopView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MsgActivity extends BaseActivity implements MsgContract.View {

    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.spv_mode)
    StatusSelectPopView mSpvMode;

    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;
    @BindView(R.id.rv_msg)
    RecyclerView mRvMsg;
    @BindView(R.id.empty_view)
    View mEmptyView;

    private RvMsgAdapter mMsgAdapter;
    private MsgPresenter mMsgPresenter;

    private int mSubMode;
    private String mTip;

    private String mode = MsgModel.MODE_ALL;

    private String[] modes = {MsgModel.MODE_ALL,
            MsgModel.MODE_JOIN,
            MsgModel.MODE_CHANGE,
            MsgModel.MODE_LEAVE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_msg);
        initTitleBar(true, "消息盒子");
        mMsgPresenter = new MsgPresenter();
        mMsgPresenter.attachView(this);
        initView();
    }


    private void initView() {

        List<String> modeTitles = new ArrayList<>();
        modeTitles.add("全部");
        modeTitles.add("加入企业");
        modeTitles.add("企业变更");
        modeTitles.add("离开企业");
        mSpvMode.initData(modeTitles);
        mSpvMode.setOnSelectListener(new StatusSelectPopView.OnSelectListener() {
            @Override
            public void onSelect(int pos) {
                mode = modes[pos];
                search();
            }
        });

        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search();
                }
                return false;
            }
        });

        mRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mMsgPresenter.getMsgList(false, "", mode);
            }

            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mMsgPresenter.getMsgList(true, "", mode);
            }
        });
        mRvMsg.setLayoutManager(new LinearLayoutManager(this));
        mMsgAdapter = new RvMsgAdapter(this);
        mMsgAdapter.setOnBtnClickListener(new RvMsgAdapter.OnBtnClickListener() {
            @Override
            public void onClick(int subMode, String tip) {
                mSubMode = subMode;
                mTip = tip;
                String[] tips = tip.split(":");
                String cid = tips[0];
                //判断是否还在改公司里
                if (subMode != 1) {
                    mMsgPresenter.checkCompany(Integer.parseInt(cid));
                } else {
                    handClickByMode(subMode, tip);
                }
            }
        });
        mRvMsg.setAdapter(mMsgAdapter);
        mRefresh.autoRefresh();
    }

    private void handClickByMode(int subMode, String tip) {
        String[] tips = tip.split(":");
        String cid = tips[0];
        AppBaseCache.getInstance().setCid(Integer.parseInt(cid));
        GlobalStatusManager.getInstance().setCompanyListNeedRefresh(true);
        switch (subMode) {
            //马上审核
            case 0:
                Utils.startActivityWithMain(mContext, new Intent(mContext, EmployeeListActivity.class));
                break;
            //重新提交
            case 1:
                Intent intent = new Intent(mContext, JoinComStep2Activity.class);
                intent.putExtra("code", tips[1]);
                startActivity(intent);
                break;
            //进入企业
            case 2:
                TenApp.getInstance().jumpMainActivity();
                break;
            //马上查看
            case 3:
                Utils.startActivityWithMain(mContext, new Intent(mContext, CompanyInfoActivity.class));
                break;
            //查看主机
            case 4:
                Utils.startActivityWithMain(mContext, new Intent(mContext, ServerListActivity.class));
                break;
            //添加主机
            case 5:
                Utils.startActivityWithMain(mContext, new Intent(mContext, ServerAddActivity.class));
                break;
        }
    }


    private void search() {
        String key = mEtSearch.getText().toString().trim();
        mMsgPresenter.search("", this.mode, key);
    }

    @Override
    public void showEmpty(boolean isLoadMore) {
        if (isLoadMore) {
            showMessage("暂无更多数据");
            mRefresh.finishLoadmore();
        } else {
            mMsgAdapter.clear();
            mRefresh.finishRefresh();
            mEmptyView.setVisibility(View.VISIBLE);
            mRvMsg.setVisibility(View.GONE);
        }
    }

    @Override
    public void showMsgList(List<MessageBean> msg, boolean isLoadMore) {
        mEmptyView.setVisibility(View.GONE);
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
    public void jumpPage(boolean isEmployee) {
        if (isEmployee) {
            handClickByMode(mSubMode, mTip);
        } else {
            showMessage("消息已过期");
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
