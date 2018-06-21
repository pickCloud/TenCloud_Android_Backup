package com.ten.tencloud.module.server.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.widget.RadioButton;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
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
import com.ten.tencloud.widget.ProgressPieView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

/**
 * Created by lxq on 2017/11/29.
 */

public class ServerMonitorPerformancePager extends BasePager {

    @BindView(R.id.pc_progress)
    PieChart mPcProgress;
    @BindView(R.id.lc_cpu)
    LineChart mLcCpu;
    @BindView(R.id.lc_memory)
    LineChart mLcMemory;
    @BindView(R.id.lc_swap)
    LineChart mLcSwap;
    @BindView(R.id.rb_cpu1)
    RadioButton mRbCpu1;
    @BindView(R.id.pv_progress)
    ProgressPieView mPvProgress;

    private boolean isFirst = true;
    private String mId;


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
        mRbCpu1.setChecked(true);
        initLineChart();
    }

    private void initLineChart() {
        mPcProgress.setNoDataText("暂无数据");
        mLcCpu.setNoDataText("暂无数据");
        mLcMemory.setNoDataText("暂无数据");
        mLcSwap.setNoDataText("暂无数据");

        mPcProgress.setNoDataTextColor(getResources().getColor(R.color.text_color_899ab6));
        mLcCpu.setNoDataTextColor(getResources().getColor(R.color.text_color_899ab6));
        mLcMemory.setNoDataTextColor(getResources().getColor(R.color.text_color_899ab6));
        mLcSwap.setNoDataTextColor(getResources().getColor(R.color.text_color_899ab6));

        mLcCpu.setScaleEnabled(false);
        mLcMemory.setScaleEnabled(false);
        mLcSwap.setScaleEnabled(false);

        Description description = new Description();
        description.setText("");
        mPcProgress.setDescription(description);
        mLcCpu.setDescription(description);
        mLcMemory.setDescription(description);
        mLcSwap.setDescription(description);

        mPcProgress.getLegend().setEnabled(false);//标签设置
        mLcCpu.getLegend().setEnabled(false);//标签设置
        mLcMemory.getLegend().setEnabled(false);//标签设置
        mLcSwap.getLegend().setEnabled(true);//标签设置
        mLcSwap.getLegend().setTextSize(10);
        mLcSwap.getLegend().setForm(Legend.LegendForm.LINE);
        mLcSwap.getLegend().setFormSize(8);
        mLcSwap.getLegend().setFormToTextSpace(2);
        mLcSwap.getLegend().setTextColor(getResources().getColor(R.color.text_color_899ab6));

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
        setCPUData();
        setMemoryData();
        setSwapData();
        setProgressData();
        isFirst = false;
    }

    private void setProgressData() {
        Random random = new Random();
        mPvProgress.setThreshold(101);
        mPvProgress.setProgress(random.nextInt(100));
    }

    private void setSwapData() {
        ArrayList<NetSpeedBean> info = new ArrayList<>();
        Random random = new Random();
        info.add(new NetSpeedBean(random.nextInt(100) + "", random.nextInt(100) + "", "14:30 11-27"));
        info.add(new NetSpeedBean(random.nextInt(100) + "", random.nextInt(100) + "", "15:30 11-27"));
        info.add(new NetSpeedBean(random.nextInt(100) + "", random.nextInt(100) + "", "16:30 11-27"));
        info.add(new NetSpeedBean(random.nextInt(100) + "", random.nextInt(100) + "", "17:30 11-27"));
        info.add(new NetSpeedBean(random.nextInt(100) + "", random.nextInt(100) + "", "18:30 11-27"));
        info.add(new NetSpeedBean(random.nextInt(100) + "", random.nextInt(100) + "", "19:30 11-27"));
        info.add(new NetSpeedBean(random.nextInt(100) + "", random.nextInt(100) + "", "20:30 11-27"));
        info.add(new NetSpeedBean(random.nextInt(100) + "", random.nextInt(100) + "", "21:30 11-27"));
        setDataWithNetView(info);
    }

    private void setMemoryData() {
        List<ContentInfoBean> infoBeans = new ArrayList<>();
        Random random = new Random();
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "14:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "15:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "16:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "17:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "18:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "19:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "10:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "21:30 11-27"));
        setDataWithMemoryView(infoBeans);
    }

    private void setCPUData() {
        List<ContentInfoBean> infoBeans = new ArrayList<>();
        Random random = new Random();
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "14:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "15:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "16:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "17:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "18:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "19:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "10:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "21:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "14:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "15:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "16:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "17:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "18:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "19:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "10:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "21:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "14:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "15:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "16:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "17:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "18:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "19:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "10:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "21:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "14:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "15:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "16:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "17:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "18:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "19:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "10:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "21:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "14:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "15:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "16:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "17:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "18:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "19:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "10:30 11-27"));
        infoBeans.add(new ContentInfoBean(random.nextInt(100) + "", "21:30 11-27"));
        setDataWithCPUView(infoBeans);
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
            xValues.add(netInfo.get(i).getCreated_time());
        }
        mLcSwap.getXAxis().setValueFormatter(new IAxisValueFormatter() {
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

        if (mLcSwap.getData() != null &&
                mLcSwap.getData().getDataSetCount() > 1) {
            recvSet = (LineDataSet) mLcSwap.getData().getDataSetByIndex(0);
            sendSet = (LineDataSet) mLcSwap.getData().getDataSetByIndex(1);
            recvSet.setValues(recvValues);
            sendSet.setValues(sendValues);
            mLcSwap.getData().notifyDataChanged();
            mLcSwap.notifyDataSetChanged();
        } else {
            recvSet = setLineStyleWithNet(recvValues, "接收", R.color.color_eb6565, R.drawable.fade_ba5659);
            sendSet = setLineStyleWithNet(sendValues, "发送", R.color.color_95c099, R.drawable.fade_80a487);
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(recvSet);
            dataSets.add(sendSet);
            LineData data = new LineData(dataSets);
            mLcSwap.setData(data);
        }
        mLcSwap.invalidate();
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
            xValues.add(infoBeans.get(i).getCreated_time());
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
