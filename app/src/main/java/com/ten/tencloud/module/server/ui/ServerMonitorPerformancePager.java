package com.ten.tencloud.module.server.ui;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BasePager;
import com.ten.tencloud.bean.ServerSystemLoadBean;
import com.ten.tencloud.module.server.contract.ServerSystemLoadContract;
import com.ten.tencloud.module.server.presenter.ServerSystemLoadPresenter;
import com.ten.tencloud.widget.dialog.ServerSystemLoadDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lxq on 2017/11/29.
 */

public class ServerMonitorPerformancePager extends BasePager implements ServerSystemLoadContract.View {

    private final ServerSystemLoadPresenter mServerSystemLoadPresenter;
    //资源概况
    @BindView(R.id.tv_os_time)
    TextView mTvOsTime;
    @BindView(R.id.tv_run_duration)
    TextView mTvRunDuration;
    @BindView(R.id.tv_login_count)
    TextView mTvLoginCount;
    @BindView(R.id.tv_load_1)
    TextView mTvLoad1;
    @BindView(R.id.tv_load_5)
    TextView mTvLoad5;
    @BindView(R.id.tv_load_15)
    TextView mTvLoad15;

    @BindView(R.id.lc_cpu)
    LineChart mLcCpu;
    @BindView(R.id.lc_memory)
    LineChart mLcMemory;
    @BindView(R.id.lc_disk)
    LineChart mLcDisk;
    @BindView(R.id.lc_net)
    LineChart mLcNet;

    private boolean isFirst = true;
    private String mId;
    private ServerSystemLoadDialog mServerSystemLoadDialog;

    public ServerMonitorPerformancePager(Context context) {
        super(context);
        mServerSystemLoadPresenter = new ServerSystemLoadPresenter();
        mServerSystemLoadPresenter.attachView(this);
    }


    @Override
    public void init() {
        if (isFirst) {
            mId = getArgument("id");
            createView(R.layout.pager_server_monitor_performance);
            initView();
            initData();
        }
    }

    private void initView() {
        initLineChart();
    }

    private void initLineChart() {
        mLcCpu.setNoDataText("暂无数据");
        mLcMemory.setNoDataText("暂无数据");
        mLcDisk.setNoDataText("暂无数据");
        mLcNet.setNoDataText("暂无数据");

        mLcCpu.setNoDataTextColor(getResources().getColor(R.color.text_color_899ab6));
        mLcMemory.setNoDataTextColor(getResources().getColor(R.color.text_color_899ab6));
        mLcDisk.setNoDataTextColor(getResources().getColor(R.color.text_color_899ab6));
        mLcNet.setNoDataTextColor(getResources().getColor(R.color.text_color_899ab6));

        mLcCpu.setScaleEnabled(false);
        mLcMemory.setScaleEnabled(false);
        mLcDisk.setScaleEnabled(false);
        mLcNet.setScaleEnabled(false);

        Description description = new Description();
        description.setText("");
        mLcCpu.setDescription(description);
        mLcMemory.setDescription(description);
        mLcDisk.setDescription(description);
        mLcNet.setDescription(description);

        mLcCpu.getLegend().setEnabled(false);//标签设置
        mLcMemory.getLegend().setEnabled(false);//标签设置
        mLcDisk.getLegend().setEnabled(false);//标签设置
        mLcNet.getLegend().setEnabled(true);//标签设置
        mLcNet.getLegend().setTextSize(10);
        mLcNet.getLegend().setForm(Legend.LegendForm.LINE);
        mLcNet.getLegend().setFormSize(8);
        mLcNet.getLegend().setFormToTextSpace(2);
        mLcNet.getLegend().setTextColor(getResources().getColor(R.color.text_color_899ab6));

        setLineChartAxisStyle(mLcCpu);
        setLineChartAxisStyle(mLcMemory);
        setLineChartAxisStyle(mLcDisk);
        setLineChartAxisStyle(mLcNet);
    }

    /**
     * 处理坐标轴
     *
     * @param lineChart
     */
    private void setLineChartAxisStyle(LineChart lineChart) {
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(8);

        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(getResources().getColor(R.color.text_color_899ab6));
        xAxis.setLabelRotationAngle(48);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setTextSize(10);
        leftAxis.setDrawGridLines(false);
        leftAxis.setTextColor(getResources().getColor(R.color.text_color_899ab6));
        leftAxis.removeAllLimitLines();

        lineChart.getAxisRight().setEnabled(false);
    }

    private void initData() {
        mServerSystemLoadPresenter.getServerSystemLoad(mId);
    }

    @OnClick({R.id.tv_load_des})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_load_des: {
                if (mServerSystemLoadDialog == null) {
                    mServerSystemLoadDialog = new ServerSystemLoadDialog(mContext);
                }
                mServerSystemLoadDialog.show();
                break;
            }
        }
    }

    @Override
    public void showServerSystemLoad(ServerSystemLoadBean systemLoadBean) {
        isFirst = true;
        if (systemLoadBean == null) {
            return;
        }
        mTvOsTime.setText(systemLoadBean.getDate());
        mTvLoginCount.setText(systemLoadBean.getLogin_users() + "");
        mTvRunDuration.setText(systemLoadBean.getRun_time());
        setMinuteLoadStyle(mTvLoad1, systemLoadBean.getOne_minute_load());
        setMinuteLoadStyle(mTvLoad5, systemLoadBean.getFive_minute_load());
        setMinuteLoadStyle(mTvLoad15, systemLoadBean.getFifth_minute_load());
        mTvLoad1.setText(systemLoadBean.getOne_minute_load() + "");
        mTvLoad5.setText(systemLoadBean.getFive_minute_load() + "");
        mTvLoad15.setText(systemLoadBean.getFifth_minute_load() + "");
    }

    private void setMinuteLoadStyle(TextView tv, float value) {
        tv.setEnabled(value < 0.8);
        tv.setSelected(value < 0.8);
    }

    @Override
    public void onActivityDestroy() {
        super.onActivityDestroy();

    }


}
