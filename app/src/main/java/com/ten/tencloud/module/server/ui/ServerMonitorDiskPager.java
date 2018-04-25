package com.ten.tencloud.module.server.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
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
import com.ten.tencloud.bean.ServerMonitorDiskBean;
import com.ten.tencloud.module.server.adapter.RvServerMonitorDiskAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

/**
 * Created by lxq on 2017/11/29.
 */

public class ServerMonitorDiskPager extends BasePager {

    @BindView(R.id.lc_disk)
    LineChart mLcDisk;
    @BindView(R.id.rv_disk)
    RecyclerView mRvDisk;

    private boolean isFirst = true;
    private String mId;
    private RvServerMonitorDiskAdapter mDiskAdapter;

    public ServerMonitorDiskPager(Context context) {
        super(context);
    }


    @Override
    public void init() {
        if (isFirst) {
            mId = getArgument("id");
            createView(R.layout.pager_server_monitor_disk);
            initView();
            initData();
        }
    }

    private void initView() {
        LinearLayoutManager layout = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRvDisk.setLayoutManager(layout);
        mDiskAdapter = new RvServerMonitorDiskAdapter(mContext);
        mRvDisk.setAdapter(mDiskAdapter);
        initLineChart();
    }

    private void initLineChart() {
        mLcDisk.setNoDataText("暂无数据");

        mLcDisk.setNoDataTextColor(getResources().getColor(R.color.text_color_899ab6));

        mLcDisk.setScaleEnabled(false);

        Description description = new Description();
        description.setText("");
        mLcDisk.setDescription(description);

        mLcDisk.getLegend().setEnabled(false);//标签设置

        setLineChartAxisStyle(mLcDisk);
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
        setDiskData();
        List<ServerMonitorDiskBean> datas = new ArrayList<>();
        datas.add(new ServerMonitorDiskBean());
        datas.add(new ServerMonitorDiskBean());
        datas.add(new ServerMonitorDiskBean());
        datas.add(new ServerMonitorDiskBean());
        datas.add(new ServerMonitorDiskBean());
        datas.add(new ServerMonitorDiskBean());
        mDiskAdapter.setDatas(datas);
        isFirst = false;
    }

    private void setDiskData() {
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
        setDataWithDiskView(infoBeans);
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
