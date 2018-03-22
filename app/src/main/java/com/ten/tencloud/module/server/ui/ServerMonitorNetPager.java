package com.ten.tencloud.module.server.ui;

import android.content.Context;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BasePager;

import butterknife.BindView;

/**
 * Created by lxq on 2017/11/29.
 */

public class ServerMonitorNetPager extends BasePager {

    @BindView(R.id.lc_ip)
    LineChart mLcIp;
    @BindView(R.id.lc_tcp)
    LineChart mLcTcp;
    @BindView(R.id.lc_udp)
    LineChart mLcUdp;
    @BindView(R.id.lc_icmp)
    LineChart mLcIcmp;

    private boolean isFirst = true;
    private String mId;

    public ServerMonitorNetPager(Context context) {
        super(context);
    }


    @Override
    public void init() {
        if (isFirst) {
            mId = getArgument("id");
            createView(R.layout.pager_server_monitor_net);
            initView();
            initData();
        }
    }

    private void initView() {
        initLineChart();
    }

    private void initLineChart() {
        mLcIp.setNoDataText("暂无数据");
        mLcTcp.setNoDataText("暂无数据");
        mLcUdp.setNoDataText("暂无数据");
        mLcIcmp.setNoDataText("暂无数据");

        mLcIp.setNoDataTextColor(getResources().getColor(R.color.text_color_899ab6));
        mLcTcp.setNoDataTextColor(getResources().getColor(R.color.text_color_899ab6));
        mLcUdp.setNoDataTextColor(getResources().getColor(R.color.text_color_899ab6));
        mLcIcmp.setNoDataTextColor(getResources().getColor(R.color.text_color_899ab6));

        mLcIp.setScaleEnabled(false);
        mLcTcp.setScaleEnabled(false);
        mLcUdp.setScaleEnabled(false);
        mLcIcmp.setScaleEnabled(false);

        Description description = new Description();
        description.setText("");
        mLcIp.setDescription(description);
        mLcTcp.setDescription(description);
        mLcUdp.setDescription(description);
        mLcIcmp.setDescription(description);

        mLcIp.getLegend().setEnabled(false);//标签设置
        mLcTcp.getLegend().setEnabled(false);//标签设置
        mLcUdp.getLegend().setEnabled(false);//标签设置
        mLcIcmp.getLegend().setEnabled(true);//标签设置
        mLcIcmp.getLegend().setTextSize(10);
        mLcIcmp.getLegend().setForm(Legend.LegendForm.LINE);
        mLcIcmp.getLegend().setFormSize(8);
        mLcIcmp.getLegend().setFormToTextSpace(2);
        mLcIcmp.getLegend().setTextColor(getResources().getColor(R.color.text_color_899ab6));

        setLineChartAxisStyle(mLcIp);
        setLineChartAxisStyle(mLcTcp);
        setLineChartAxisStyle(mLcUdp);
        setLineChartAxisStyle(mLcIcmp);
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


    @Override
    public void onActivityDestroy() {
        super.onActivityDestroy();

    }

}
