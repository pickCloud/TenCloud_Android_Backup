package com.ten.tencloud.module.server.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BasePager;
import com.ten.tencloud.bean.ContentInfoBean;
import com.ten.tencloud.bean.NetSpeedBean;
import com.ten.tencloud.bean.ServerMonitorBean;
import com.ten.tencloud.module.server.contract.ServerMonitorContract;
import com.ten.tencloud.module.server.presenter.ServerMonitorPresenter;
import com.ten.tencloud.utils.DateUtils;
import com.ten.tencloud.widget.StatusSelectPopView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.ten.tencloud.module.server.presenter.ServerMonitorPresenter.STATE_DAY;
import static com.ten.tencloud.module.server.presenter.ServerMonitorPresenter.STATE_HOUR;
import static com.ten.tencloud.module.server.presenter.ServerMonitorPresenter.STATE_MONTH;
import static com.ten.tencloud.module.server.presenter.ServerMonitorPresenter.STATE_WEEK;

/**
 * Created by lxq on 2017/11/29.
 */

public class ServerDetailMonitorPager extends BasePager implements ServerMonitorContract.View {

    @BindView(R.id.spv_cycle)
    StatusSelectPopView mSpvCycle;
    @BindView(R.id.lc_cpu)
    LineChart mLcCpu;
    @BindView(R.id.lc_memory)
    LineChart mLcMemory;
    @BindView(R.id.lc_disk)
    LineChart mLcDisk;
    @BindView(R.id.lc_net)
    LineChart mLcNet;

    private Integer[] mCycleData = {STATE_HOUR, STATE_DAY, STATE_WEEK, STATE_MONTH};

    private ServerMonitorPresenter mServerMonitorPresenter;
    private String mId;
    private String mServerName;

    public ServerDetailMonitorPager(Context context) {
        super(context);
    }

    @Override
    public void init() {
        mId = getArgument("id");
        mServerName = getArgument("name");
        createView(R.layout.pager_server_detail_monitor);
        initView();
        mServerMonitorPresenter = new ServerMonitorPresenter();
        mServerMonitorPresenter.attachView(this);
        mServerMonitorPresenter.getServerMonitorInfo(mId, STATE_HOUR);
    }

    private void initView() {

        //周期选择器
        List<String> cycleTitles = new ArrayList<>();
        cycleTitles.add("一个小时");
        cycleTitles.add("24个小时");
        cycleTitles.add("1周");
        cycleTitles.add("1个月");
        mSpvCycle.initData(cycleTitles);
        mSpvCycle.setOnSelectListener(new StatusSelectPopView.OnSelectListener() {
            @Override
            public void onSelect(int pos) {
                mServerMonitorPresenter.getServerMonitorInfo(mId, mCycleData[pos]);
            }
        });


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

        setAxisStyle(mLcCpu);
        setAxisStyle(mLcMemory);
        setAxisStyle(mLcDisk);
        setAxisStyle(mLcNet);
    }

    /**
     * 处理坐标轴
     *
     * @param lineChart
     */
    private void setAxisStyle(LineChart lineChart) {
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

    @OnClick({R.id.tv_history})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_history:
                Intent intent = new Intent(mContext, ServerPerformanceHistoryActivity.class);
                intent.putExtra("id", mId);
                intent.putExtra("name", mServerName);
                mContext.startActivity(intent);
                break;
        }
    }

    @Override
    public void showServerMonitorInfo(ServerMonitorBean serverMonitor) {
        setDataWithCPUView(serverMonitor.getCpu());
        setDataWithMemoryView(serverMonitor.getMemory());
        setDataWithDiskView(serverMonitor.getDisk());
        setDataWithNetView(serverMonitor.getNet());
    }

    /**
     * 设置网络数据
     *
     * @param netInfo
     */
    private void setDataWithNetView(List<NetSpeedBean> netInfo) {
        List<Entry> recvValues = new ArrayList<>();
        List<Entry> sendValues = new ArrayList<>();
        final List<String> xValues = new ArrayList<>();
        String format = "HH:mm MM-dd";
        for (int i = 0; i < netInfo.size(); i++) {
            recvValues.add(new Entry(i, Float.valueOf(netInfo.get(i).getInput())));
            sendValues.add(new Entry(i, Float.valueOf(netInfo.get(i).getOutput())));
            xValues.add(DateUtils.timestampToString(Long.valueOf(netInfo.get(i).getCreated_time()), format));
        }
        mLcNet.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (((int) value) > xValues.size() - 1) {
                    return "";
                }
                return xValues.get((int) value);
            }
        });
        LineDataSet recvSet;
        LineDataSet sendSet;

        if (mLcNet.getData() != null &&
                mLcNet.getData().getDataSetCount() > 1) {
            recvSet = (LineDataSet) mLcNet.getData().getDataSetByIndex(0);
            sendSet = (LineDataSet) mLcNet.getData().getDataSetByIndex(1);
            recvSet.setValues(recvValues);
            sendSet.setValues(sendValues);
            mLcNet.getData().notifyDataChanged();
            mLcNet.notifyDataSetChanged();
        } else {
            recvSet = setLineStyleWithNet(recvValues, "接收", R.color.color_eb6565, R.drawable.fade_ba5659);
            sendSet = setLineStyleWithNet(sendValues, "发送", R.color.color_95c099, R.drawable.fade_80a487);
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(recvSet);
            dataSets.add(sendSet);
            LineData data = new LineData(dataSets);
            mLcNet.setData(data);
        }
        mLcNet.invalidate();
    }

    private LineDataSet setLineStyleWithNet(List<Entry> values, String label, @ColorRes int color, @DrawableRes int fillDrawable) {
        LineDataSet dataSet = new LineDataSet(values, label);
        dataSet.setDrawIcons(false);
        dataSet.setColor(getResources().getColor(color));
        dataSet.setHighlightEnabled(false);
        dataSet.setLineWidth(1f);
        dataSet.setCircleColor(getResources().getColor(color));
        dataSet.setCircleRadius(3f);
        dataSet.setDrawCircleHole(true);//空心
        dataSet.setCircleHoleRadius(1f);
        dataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        dataSet.setCubicIntensity(0.6f);//折线平滑度
        dataSet.setValueTextSize(9f);
        dataSet.setDrawValues(false);
        dataSet.setDrawFilled(true);
        dataSet.setFormLineWidth(5f);
        dataSet.setFormSize(15.f);
        //填充色
        if (Utils.getSDKInt() >= 18) {
            Drawable drawable = ContextCompat.getDrawable(mContext, fillDrawable);
            dataSet.setFillDrawable(drawable);
        } else {
            dataSet.setFillColor(Color.TRANSPARENT);
        }
        return dataSet;
    }

    /**
     * 磁盘使用
     *
     * @param diskInfo
     */
    private void setDataWithDiskView(List<ContentInfoBean> diskInfo) {
        setDataWithContentInfo(mLcDisk, diskInfo, R.color.color_95c099, R.drawable.fade_80a487);
    }

    /**
     * 内存
     *
     * @param memoryInfo
     */
    private void setDataWithMemoryView(List<ContentInfoBean> memoryInfo) {
        setDataWithContentInfo(mLcMemory, memoryInfo, R.color.color_eb6565, R.drawable.fade_ba5659);
    }

    /**
     * 设置CPU线性图
     *
     * @param cpuInfo
     */
    private void setDataWithCPUView(List<ContentInfoBean> cpuInfo) {
        setDataWithContentInfo(mLcCpu, cpuInfo, R.color.colorPrimary, R.drawable.fade_blue);
    }

    /**
     * 设置ContentInfo数据(CPU，内存，磁盘)
     */
    private void setDataWithContentInfo(LineChart lineChart, List<ContentInfoBean> infoBeans, @ColorRes int color, @DrawableRes int fillDrawable) {
        List<Entry> values = new ArrayList<>();
        final List<String> xValues = new ArrayList<>();
        String format = "HH:mm MM-dd";
        for (int i = 0; i < infoBeans.size(); i++) {
            values.add(new Entry(i, Float.valueOf(infoBeans.get(i).getPercent())));
            xValues.add(DateUtils.timestampToString(Long.valueOf(infoBeans.get(i).getCreated_time()), format));
        }
        lineChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (((int) value) > xValues.size() - 1) {
                    return "";
                }
                return xValues.get((int) value);
            }
        });
        LineDataSet set1;
        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 1) {
            set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "");
            set1.setDrawIcons(false);
            set1.setColor(getResources().getColor(color));
            set1.setHighlightEnabled(false);
            set1.setLineWidth(1f);
            set1.setCircleColor(getResources().getColor(color));
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(true);//空心
            set1.setCircleHoleRadius(1f);
            set1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            set1.setCubicIntensity(0.6f);
            set1.setValueTextSize(9f);
            set1.setDrawValues(false);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(5f);
            set1.setFormSize(15.f);
            //填充色
            if (Utils.getSDKInt() >= 18) {
                Drawable drawable = ContextCompat.getDrawable(mContext, fillDrawable);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.TRANSPARENT);
            }
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            lineChart.setData(data);
        }
        lineChart.invalidate();
    }
}
