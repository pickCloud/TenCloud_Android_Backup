package com.ten.tencloud.module.server.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.ServerHistoryBean;
import com.ten.tencloud.module.server.adapter.RvServerHistoryAdapter;
import com.ten.tencloud.module.server.contract.ServerHistoryContract;
import com.ten.tencloud.module.server.presenter.ServerHistoryPresenter;
import com.ten.tencloud.widget.StatusSelectPopView;
import com.ten.tencloud.widget.dialog.ServerHistoryTimeDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ServerPerformanceHistoryActivity extends BaseActivity implements ServerHistoryContract.View {

    private final static int TYPE_DEFAULT = 1;
    private final static int TYPE_HOUR = 2;
    private final static int TYPE_DAY = 3;

    private Integer[] mCycleData = {TYPE_DEFAULT, TYPE_HOUR, TYPE_DAY};

    @BindView(R.id.spv_cycle)
    StatusSelectPopView mSpvCycle;
    @BindView(R.id.rv_history)
    RecyclerView mRvHistory;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;

    private String mId;
    private String mServerName;
    private int mTypeId = TYPE_DEFAULT;

    private long startTime;
    private long endTime;


    //    private PopupWindow mPopupWindow;
//    private TextView mTvCycleDefault;
//    private TextView mTvCycleHour;
//    private TextView mTvCycleDay;
//    private ImageView mIvCycleDefault;
//    private ImageView mIvCycleHour;
//    private ImageView mIvCycleDay;
//    private TextView[] mTvCycleArray;
//    private ImageView[] mIvCycleArray;
    private ServerHistoryPresenter mServerHistoryPresenter;
    private RvServerHistoryAdapter mAdapter;
    private ServerHistoryTimeDialog mHistoryTimeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_server_performance_history);
        mId = getIntent().getStringExtra("id");
        mServerName = getIntent().getStringExtra("name");
        initTitleBar(true, "历史记录", mServerName);
        mServerHistoryPresenter = new ServerHistoryPresenter();
        mServerHistoryPresenter.attachView(this);
        endTime = System.currentTimeMillis() / 1000;
        startTime = endTime - (7 * 24 * 60 * 60);//默认取一周的
        initView();
    }

    private void initView() {

        List<String> cycleTitles = new ArrayList<>();
        cycleTitles.add("正常");
        cycleTitles.add("按时平均");
        cycleTitles.add("按天平均");
        mSpvCycle.initData(cycleTitles);
        mSpvCycle.setOnSelectListener(new StatusSelectPopView.OnSelectListener() {
            @Override
            public void onSelect(int pos) {
                mTypeId = mCycleData[pos];
                mRefresh.autoRefresh();
            }
        });

        mRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mServerHistoryPresenter.getServerHistory(false, mId, mTypeId, startTime + "", endTime + "");
            }

            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mServerHistoryPresenter.getServerHistory(true, mId, mTypeId, startTime + "", endTime + "");
            }
        });

        mRvHistory.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RvServerHistoryAdapter(this);
        mRvHistory.setAdapter(mAdapter);
        mRefresh.autoRefresh();
    }

    @OnClick({R.id.tv_time_select})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_time_select:
                showSelectTimeDialog();
                break;
        }
    }

    private void showSelectTimeDialog() {
        if (mHistoryTimeDialog == null) {
            mHistoryTimeDialog = new ServerHistoryTimeDialog(this);
            mHistoryTimeDialog.setOnOkListener(new ServerHistoryTimeDialog.OnOkListener() {
                @Override
                public void onOk(long startTime, long endTime) {
                    ServerPerformanceHistoryActivity.this.startTime = startTime;
                    ServerPerformanceHistoryActivity.this.endTime = endTime;
                    mRefresh.autoRefresh();
                }
            });
        }
        if (!mHistoryTimeDialog.isShowing()) {
            mHistoryTimeDialog.setTime(startTime, endTime);
            mHistoryTimeDialog.show();
        }
    }

    @Override
    public void showServerHistory(List<ServerHistoryBean> data, boolean isLoadMore) {
        if (isLoadMore) {
            mAdapter.addData(data);
            mRefresh.finishLoadmore();
        } else {
            mAdapter.setDatas(data);
            mRefresh.finishRefresh();
        }
    }

    @Override
    public void showNoData(boolean isLoadMore) {
        if (isLoadMore) {
            showMessage("没有数据了");
            mRefresh.finishLoadmore();
        } else {
            showMessage("暂无数据");
            mAdapter.clear();
            mRefresh.finishRefresh();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
