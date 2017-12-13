package com.ten.tencloud.module.server.ui;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.ServerHistoryBean;
import com.ten.tencloud.module.server.adapter.RvServerHistoryAdapter;
import com.ten.tencloud.module.server.contract.ServerHistoryContract;
import com.ten.tencloud.module.server.presenter.ServerHistoryPresenter;
import com.ten.tencloud.widget.dialog.ServerHistoryTimeDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ServerPerformanceHistoryActivity extends BaseActivity implements ServerHistoryContract.View {

    private final static int TYPE_DEFAULT = 1;
    private final static int TYPE_HOUR = 2;
    private final static int TYPE_DAY = 3;

    @BindView(R.id.ll_cycle)
    LinearLayout mLLCycle;
    @BindView(R.id.iv_option)
    ImageView mIvOption;
    @BindView(R.id.tv_cycle)
    TextView mTvCycle;
    @BindView(R.id.rv_history)
    RecyclerView mRvHistory;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;

    private String mId;
    private String mServerName;
    private int mTypeId = TYPE_DEFAULT;

    private long startTime;
    private long endTime;


    private PopupWindow mPopupWindow;
    private TextView mTvCycleDefault;
    private TextView mTvCycleHour;
    private TextView mTvCycleDay;
    private ImageView mIvCycleDefault;
    private ImageView mIvCycleHour;
    private ImageView mIvCycleDay;
    private TextView[] mTvCycleArray;
    private ImageView[] mIvCycleArray;
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

    @OnClick({R.id.ll_cycle, R.id.tv_time_select})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_cycle:
                showCycleWindow();
                break;
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

    private String mSelectCycle = "正常";//默认选择的周期

    private void showCycleWindow() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pop_server_history_cycle, null);
        mTvCycleDefault = view.findViewById(R.id.tv_cycle_default);
        mTvCycleHour = view.findViewById(R.id.tv_cycle_hour);
        mTvCycleDay = view.findViewById(R.id.tv_cycle_day);
        mIvCycleDefault = view.findViewById(R.id.iv_cycle_default);
        mIvCycleHour = view.findViewById(R.id.iv_cycle_hour);
        mIvCycleDay = view.findViewById(R.id.iv_cycle_day);
        mTvCycleArray = new TextView[]{mTvCycleDefault, mTvCycleHour, mTvCycleDay};
        mIvCycleArray = new ImageView[]{mIvCycleDefault, mIvCycleHour, mIvCycleDay};

        mTvCycleDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("正常".equals(mSelectCycle)) {
                    return;
                }
                mSelectCycle = "正常";
                mTypeId = TYPE_DEFAULT;
                mTvCycle.setText(mSelectCycle);
                mRefresh.autoRefresh();
                mPopupWindow.dismiss();
            }
        });
        mTvCycleHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("按时平均".equals(mSelectCycle)) {
                    return;
                }
                mSelectCycle = "按时平均";
                mTypeId = TYPE_HOUR;
                mTvCycle.setText(mSelectCycle);
                mRefresh.autoRefresh();
                mPopupWindow.dismiss();
            }
        });

        mTvCycleDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("按天平均".equals(mSelectCycle)) {
                    return;
                }
                mSelectCycle = "按天平均";
                mTypeId = TYPE_DAY;
                mTvCycle.setText(mSelectCycle);
                mRefresh.autoRefresh();
                mPopupWindow.dismiss();
            }
        });

        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mIvOption.animate().rotation(0);
                mTvCycle.setTextColor(getResources().getColor(R.color.text_color_556278));
                mLLCycle.setBackgroundResource(R.drawable.shape_round_3f4656);
            }
        });

        if (!mPopupWindow.isShowing()) {
            //界面显示
            mIvOption.animate().rotation(180);
            mTvCycle.setTextColor(getResources().getColor(R.color.text_color_899ab6));
            mLLCycle.setBackgroundResource(R.drawable.shape_round_3f4656_top);
            if ("正常".equals(mSelectCycle)) {
                setPopSelect(0);
            } else if ("按时平均".equals(mSelectCycle)) {
                setPopSelect(1);
            } else if ("按天平均".equals(mSelectCycle)) {
                setPopSelect(2);
            }
            mPopupWindow.showAsDropDown(mLLCycle);
        }
    }

    private void setPopSelect(int pos) {
        for (int i = 0; i < mIvCycleArray.length; i++) {
            if (i == pos) {
                mIvCycleArray[i].setVisibility(View.VISIBLE);
                mTvCycleArray[i].setSelected(true);
            } else {
                mIvCycleArray[i].setVisibility(View.INVISIBLE);
                mTvCycleArray[i].setSelected(false);
            }
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
