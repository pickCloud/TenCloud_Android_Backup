package com.ten.tencloud.module.server.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;

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
import com.ten.tencloud.bean.NetSpeedBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

/**
 * Created by lxq on 2017/11/29.
 */

public class ServerMonitorNetPager extends BasePager {

    @BindView(R.id.lc_net)
    LineChart mLcNet;

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
        mLcNet.setNoDataText("暂无数据");

        mLcNet.setNoDataTextColor(getResources().getColor(R.color.text_color_899ab6));

        mLcNet.setScaleEnabled(false);

        Description description = new Description();
        description.setText("");
        mLcNet.setDescription(description);

        mLcNet.getLegend().setEnabled(true);//标签设置
        mLcNet.getLegend().setTextSize(10);
        mLcNet.getLegend().setForm(Legend.LegendForm.LINE);
        mLcNet.getLegend().setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);
        mLcNet.getLegend().setFormSize(8);
        mLcNet.getLegend().setFormToTextSpace(2);
        mLcNet.getLegend().setTextColor(getResources().getColor(R.color.text_color_899ab6));

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
        setNetData();
        isFirst = false;
    }

    private void setNetData() {
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

    @Override
    public void onActivityDestroy() {
        super.onActivityDestroy();

    }

}
