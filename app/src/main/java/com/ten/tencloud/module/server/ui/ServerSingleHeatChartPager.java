package com.ten.tencloud.module.server.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

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
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BasePager;
import com.ten.tencloud.bean.ContentInfoBean;
import com.ten.tencloud.bean.NetSpeedBean;
import com.ten.tencloud.bean.ServerMonitorBean;
import com.ten.tencloud.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lxq on 2018/3/15.
 */

public class ServerSingleHeatChartPager extends BasePager {

    public static final int TYPE_CPU = 1;
    public static final int TYPE_MEMORY = 2;
    public static final int TYPE_DISK = 3;
    public static final int TYPE_DISK_USAGE = 4;
    public static final int TYPE_NET = 5;

    private int mType = TYPE_CPU;

    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.lc_chart)
    LineChart mLcChart;
    private ServerMonitorBean mDatas;
    private String mId;

    public ServerSingleHeatChartPager(Context context) {
        super(context);
    }

    @Override
    public void init() {
        createView(R.layout.pager_server_single_chart);
        initView();
    }

    private void initView() {

        mLcChart.setNoDataText("暂无数据");
        mLcChart.setNoDataTextColor(getResources().getColor(R.color.text_color_899ab6));
        mLcChart.setScaleEnabled(false);
        mLcChart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ServerDetail2Activity.class);
                intent.putExtra("id", mId);
                mContext.startActivity(intent);
            }
        });
        Description description = new Description();
        description.setPosition(0, 0);
        mLcChart.setDescription(description);
        mLcChart.getLegend().setEnabled(mType == TYPE_NET);//标签设置
        if (mType == TYPE_NET) {
            mLcChart.getLegend().setTextSize(10);
            mLcChart.getLegend().setForm(Legend.LegendForm.LINE);
            mLcChart.getLegend().setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);
            mLcChart.getLegend().setYEntrySpace(2f);
            mLcChart.getLegend().setFormSize(8);
            mLcChart.getLegend().setFormToTextSpace(2);
            mLcChart.getLegend().setTextColor(getResources().getColor(R.color.text_color_66ffffff));
        }
        //设置X轴
        setAxisStyle(mLcChart);
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
        xAxis.setTextColor(getResources().getColor(R.color.text_color_66ffffff));
        xAxis.setLabelRotationAngle(48);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setTextSize(10);
        leftAxis.setDrawGridLines(false);
        leftAxis.setTextColor(getResources().getColor(R.color.text_color_66ffffff));
        leftAxis.removeAllLimitLines();

        if (mType != TYPE_NET) {
            leftAxis.setAxisMinimum(0);
        }
        lineChart.getAxisRight().setEnabled(false);
    }

    public void initData() {
        String dataJson = getArgument("data");
        mDatas = TenApp.getInstance().getGsonInstance().fromJson(dataJson, ServerMonitorBean.class);
        mId = getArgument("serverId");
        mType = getArgument("type", TYPE_CPU);
        switch (mType) {
            case TYPE_CPU:
                setDataWithCPUView(mDatas.getCpu());
                mTvName.setText("CPU(%)");
                break;
            case TYPE_MEMORY:
                setDataWithMemoryView(mDatas.getMemory());
                mTvName.setText("内存(%)");
                break;
            case TYPE_DISK:
                setDataWithDiskView(mDatas.getDisk());
                mTvName.setText("磁盘占用率(%)");
                break;
            case TYPE_DISK_USAGE:
                setDataWithDiskUsageView(mLcChart, mDatas.getDisk(), R.color.color_ccffffff, R.drawable.fade_server_chart_white);
                mTvName.setText("磁盘利用率(%)");
                break;
            case TYPE_NET:
                setDataWithNetView(mDatas.getNet());
                mTvName.setText("网络(kb/s)");
                break;
        }
    }

    private void setDataWithDiskUsageView(LineChart lineChart, List<ContentInfoBean> infoBeans, @ColorRes int color, @DrawableRes int fillDrawable) {
        List<Entry> values = new ArrayList<>();
        final List<String> xValues = new ArrayList<>();
        String format = "HH:mm MM-dd";
        for (int i = 0; i < infoBeans.size(); i++) {
            values.add(new Entry(i, Float.valueOf(infoBeans.get(i).getUtilize())));
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
        mLcChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
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

        if (mLcChart.getData() != null &&
                mLcChart.getData().getDataSetCount() > 1) {
            recvSet = (LineDataSet) mLcChart.getData().getDataSetByIndex(0);
            sendSet = (LineDataSet) mLcChart.getData().getDataSetByIndex(1);
            recvSet.setValues(recvValues);
            sendSet.setValues(sendValues);
            mLcChart.getData().notifyDataChanged();
            mLcChart.notifyDataSetChanged();
        } else {
            recvSet = setLineStyleWithNet(recvValues, "接收", R.color.color_ccffffff, R.drawable.fade_server_chart_white);
            sendSet = setLineStyleWithNet(sendValues, "发送", R.color.color_99000000, R.drawable.fade_server_chart_black);
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(sendSet);
            dataSets.add(recvSet);
            LineData data = new LineData(dataSets);
            mLcChart.setData(data);
        }
        mLcChart.invalidate();
    }

    private LineDataSet setLineStyleWithNet(List<Entry> values, String label, @ColorRes int color, @DrawableRes int fillDrawable) {
        LineDataSet dataSet = new LineDataSet(values, label);
        dataSet.setDrawIcons(false);
        dataSet.setColor(getResources().getColor(color));
        dataSet.setHighlightEnabled(false);
        dataSet.setLineWidth(1f);
        dataSet.setCircleColor(getResources().getColor(color));
        dataSet.setCircleRadius(3f);
        dataSet.setDrawCircleHole(false);//空心
//        dataSet.setCircleHoleRadius(1f);
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
        setDataWithContentInfo(mLcChart, diskInfo, R.color.color_ccffffff, R.drawable.fade_server_chart_white);
    }

    /**
     * 内存
     *
     * @param memoryInfo
     */
    private void setDataWithMemoryView(List<ContentInfoBean> memoryInfo) {
        setDataWithContentInfo(mLcChart, memoryInfo, R.color.color_ccffffff, R.drawable.fade_server_chart_white);
    }

    /**
     * 设置CPU线性图
     *
     * @param cpuInfo
     */
    private void setDataWithCPUView(List<ContentInfoBean> cpuInfo) {
        setDataWithContentInfo(mLcChart, cpuInfo, R.color.color_ccffffff, R.drawable.fade_server_chart_white);
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
