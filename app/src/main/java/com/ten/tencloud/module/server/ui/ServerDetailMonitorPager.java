package com.ten.tencloud.module.server.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.ten.tencloud.base.view.BasePager;
import com.ten.tencloud.bean.ContentInfoBean;
import com.ten.tencloud.bean.NetSpeedBean;
import com.ten.tencloud.bean.ServerMonitorBean;
import com.ten.tencloud.module.server.contract.ServerMonitorContract;
import com.ten.tencloud.module.server.presenter.ServerMonitorPresenter;
import com.ten.tencloud.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lxq on 2017/11/29.
 */

public class ServerDetailMonitorPager extends BasePager implements ServerMonitorContract.View {

    @BindView(R.id.ll_cycle)
    LinearLayout mLLCycle;
    @BindView(R.id.iv_option)
    ImageView mIvOption;
    @BindView(R.id.tv_cycle)
    TextView mTvCycle;
    @BindView(R.id.lc_cpu)
    LineChart mLcCpu;
    @BindView(R.id.lc_memory)
    LineChart mLcMemory;
    @BindView(R.id.lc_disk)
    LineChart mLcDisk;
    @BindView(R.id.lc_net)
    LineChart mLcNet;

    private final static int STATE_HOUR = 1;
    private final static int STATE_DAY = 2;
    private final static int STATE_WEEK = 3;
    private final static int STATE_MONTH = 4;

    private PopupWindow mPopupWindow;
    private TextView mTvOneDay;
    private ImageView mIvDay;
    private ImageView mIvHour;
    private ImageView mIvWeek;
    private ImageView mIvMonth;
    private TextView mTvOneHour;
    private TextView mTvOneWeek;
    private TextView mTvOneMonth;
    private TextView[] mTvCycleArray;
    private ImageView[] mIvCycleArray;
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
        String[] cycle = handCycle(STATE_HOUR);
        mServerMonitorPresenter.getServerMonitorInfo(mId, cycle[0], cycle[1]);
    }

    private void initView() {
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

//        mLcCpu.setDrawMarkers(false);
//        mLcMemory.setDrawMarkers(false);
//        mLcDisk.setDrawMarkers(false);
//        mLcNet.setDrawMarkers(false);

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

    @OnClick({R.id.ll_cycle, R.id.tv_history})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_cycle:
                showCycleWindow();
                break;
            case R.id.tv_history:
                Intent intent = new Intent(mContext, ServerPerformanceHistoryActivity.class);
                intent.putExtra("id", mId);
                intent.putExtra("name", mServerName);
                mContext.startActivity(intent);
                break;
        }
    }

    private String mSelectCycle = "1个小时";//默认选择的周期

    /**
     * 弹出周期pop
     */
    private void showCycleWindow() {
        if (mPopupWindow == null) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.pop_server_monitor_cycle, null);
            mTvOneHour = view.findViewById(R.id.tv_one_hour);
            mTvOneDay = view.findViewById(R.id.tv_one_day);
            mTvOneWeek = view.findViewById(R.id.tv_one_week);
            mTvOneMonth = view.findViewById(R.id.tv_one_month);
            mIvHour = view.findViewById(R.id.iv_hour);
            mIvDay = view.findViewById(R.id.iv_day);
            mIvWeek = view.findViewById(R.id.iv_week);
            mIvMonth = view.findViewById(R.id.iv_month);
            mTvCycleArray = new TextView[]{mTvOneHour, mTvOneDay, mTvOneWeek, mTvOneMonth};
            mIvCycleArray = new ImageView[]{mIvHour, mIvDay, mIvWeek, mIvMonth};
            mTvOneDay.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("24个小时".equals(mSelectCycle)) {
                        return;
                    }
                    mSelectCycle = "24个小时";
                    String[] cycle = handCycle(STATE_DAY);
                    mServerMonitorPresenter.getServerMonitorInfo(mId, cycle[0], cycle[1]);
                    mTvCycle.setText(mSelectCycle);
                    mPopupWindow.dismiss();
                }
            });
            mTvOneHour.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("1个小时".equals(mSelectCycle)) {
                        return;
                    }
                    mSelectCycle = "1个小时";
                    mTvCycle.setText(mSelectCycle);
                    String[] cycle = handCycle(STATE_HOUR);
                    mServerMonitorPresenter.getServerMonitorInfo(mId, cycle[0], cycle[1]);
                    mPopupWindow.dismiss();
                }
            });
            mTvOneWeek.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("1周".equals(mSelectCycle)) {
                        return;
                    }
                    mSelectCycle = "1周";
                    mTvCycle.setText(mSelectCycle);
                    String[] cycle = handCycle(STATE_WEEK);
                    mServerMonitorPresenter.getServerMonitorInfo(mId, cycle[0], cycle[1]);
                    mPopupWindow.dismiss();
                }
            });
            mTvOneMonth.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("1个月".equals(mSelectCycle)) {
                        return;
                    }
                    mSelectCycle = "1个月";
                    mTvCycle.setText(mSelectCycle);
                    String[] cycle = handCycle(STATE_MONTH);
                    mServerMonitorPresenter.getServerMonitorInfo(mId, cycle[0], cycle[1]);
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
        }
        if (!mPopupWindow.isShowing()) {
            mIvOption.animate().rotation(180);
            mTvCycle.setTextColor(getResources().getColor(R.color.text_color_899ab6));
            mLLCycle.setBackgroundResource(R.drawable.shape_round_3f4656_top);
            if ("1个小时".equals(mSelectCycle)) {
                setPopSelect(0);
            } else if ("24个小时".equals(mSelectCycle)) {
                setPopSelect(1);
            } else if ("1周".equals(mSelectCycle)) {
                setPopSelect(2);
            } else if ("1个月".equals(mSelectCycle)) {
                setPopSelect(3);
            }
            mPopupWindow.showAsDropDown(mLLCycle);
        }
    }

    /**
     * 循环设置pop被选中的状态
     *
     * @param pos
     */
    private void setPopSelect(int pos) {
        for (int i = 0; i < mIvCycleArray.length; i++) {
            if (i == pos) {
                mIvCycleArray[i].setVisibility(VISIBLE);
                mTvCycleArray[i].setSelected(true);
            } else {
                mIvCycleArray[i].setVisibility(INVISIBLE);
                mTvCycleArray[i].setSelected(false);
            }
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
            if (i > 6) {
                break;
            }
        }
        mLcNet.getXAxis().setLabelCount(7, true);
        mLcNet.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
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
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
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
        setDataWithContentInfo(mLcCpu, cpuInfo, R.color.colorPrimary, R.drawable.fade_red);
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
            if (i > 6) {
                break;
            }
        }
        lineChart.getXAxis().setLabelCount(7, true);
        lineChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xValues.get((int) value);
            }
        });
        LineDataSet set1;

        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {
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
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
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
     * 根据周期处理开始和结束时间
     *
     * @return
     */
    private String[] handCycle(int cycleId) {
        long endTime = System.currentTimeMillis() / 1000;
        long startTime = endTime;
        switch (cycleId) {
            case STATE_HOUR:
                startTime = endTime - (60 * 60);
                break;
            case STATE_DAY:
                startTime = endTime - (24 * 60 * 60);
                break;
            case STATE_WEEK:
                startTime = endTime - (7 * 24 * 60 * 60);
                break;
            case STATE_MONTH:
                startTime = endTime - (30 * 24 * 60 * 60);
                break;
        }
        return new String[]{startTime + "", endTime + ""};
    }
}
