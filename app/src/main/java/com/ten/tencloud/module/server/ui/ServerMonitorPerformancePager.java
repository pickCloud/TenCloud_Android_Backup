package com.ten.tencloud.module.server.ui;

import android.content.Context;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BasePager;
import com.ten.tencloud.widget.dialog.ServerSystemLoadDialog;

import butterknife.BindView;

/**
 * Created by lxq on 2017/11/29.
 */

public class ServerMonitorPerformancePager extends BasePager {

    @BindView(R.id.lc_progress)
    LineChart mLcProgress;
    @BindView(R.id.lc_cpu)
    LineChart mLcCpu;
    @BindView(R.id.lc_memory)
    LineChart mLcMemory;
    @BindView(R.id.lc_swap)
    LineChart mLcSwap;

    private boolean isFirst = true;
    private String mId;
    private ServerSystemLoadDialog mServerSystemLoadDialog;

    public ServerMonitorPerformancePager(Context context) {
        super(context);
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
        mLcProgress.setNoDataText("暂无数据");
        mLcCpu.setNoDataText("暂无数据");
        mLcMemory.setNoDataText("暂无数据");
        mLcSwap.setNoDataText("暂无数据");

        mLcProgress.setNoDataTextColor(getResources().getColor(R.color.text_color_899ab6));
        mLcCpu.setNoDataTextColor(getResources().getColor(R.color.text_color_899ab6));
        mLcMemory.setNoDataTextColor(getResources().getColor(R.color.text_color_899ab6));
        mLcSwap.setNoDataTextColor(getResources().getColor(R.color.text_color_899ab6));

        mLcProgress.setScaleEnabled(false);
        mLcCpu.setScaleEnabled(false);
        mLcMemory.setScaleEnabled(false);
        mLcSwap.setScaleEnabled(false);

        Description description = new Description();
        description.setText("");
        mLcProgress.setDescription(description);
        mLcCpu.setDescription(description);
        mLcMemory.setDescription(description);
        mLcSwap.setDescription(description);

        mLcProgress.getLegend().setEnabled(false);//标签设置
        mLcCpu.getLegend().setEnabled(false);//标签设置
        mLcMemory.getLegend().setEnabled(false);//标签设置
        mLcSwap.getLegend().setEnabled(true);//标签设置
        mLcSwap.getLegend().setTextSize(10);
        mLcSwap.getLegend().setForm(Legend.LegendForm.LINE);
        mLcSwap.getLegend().setFormSize(8);
        mLcSwap.getLegend().setFormToTextSpace(2);
        mLcSwap.getLegend().setTextColor(getResources().getColor(R.color.text_color_899ab6));

        setLineChartAxisStyle(mLcProgress);
        setLineChartAxisStyle(mLcCpu);
        setLineChartAxisStyle(mLcMemory);
        setLineChartAxisStyle(mLcSwap);
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

    }
}
