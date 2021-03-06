package com.ten.tencloud.module.server.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.ContentInfoBean;
import com.ten.tencloud.bean.NetSpeedBean;
import com.ten.tencloud.bean.ServerDetailBean;
import com.ten.tencloud.bean.ServerMonitorBean;
import com.ten.tencloud.bean.ServerSystemLoadBean;
import com.ten.tencloud.bean.ServerThresholdBean;
import com.ten.tencloud.broadcast.RefreshBroadCastHandler;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.listener.OnRefreshListener;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.module.server.contract.ServerDetailContract;
import com.ten.tencloud.module.server.contract.ServerMonitorContract;
import com.ten.tencloud.module.server.contract.ServerSystemLoadContract;
import com.ten.tencloud.module.server.presenter.ServerDetailPresenter;
import com.ten.tencloud.module.server.presenter.ServerMonitorPresenter;
import com.ten.tencloud.module.server.presenter.ServerSystemLoadPresenter;
import com.ten.tencloud.utils.DateUtils;
import com.ten.tencloud.widget.ProgressRingView;
import com.ten.tencloud.widget.ProgressPieView;
import com.ten.tencloud.widget.ProgressRectView;
import com.ten.tencloud.widget.ProgressSemiCircleView;
import com.ten.tencloud.widget.blur.BlurBuilder;
import com.ten.tencloud.widget.dialog.ServerSystemLoadDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static com.ten.tencloud.module.server.presenter.ServerMonitorPresenter.STATE_DAY;
import static com.ten.tencloud.module.server.presenter.ServerMonitorPresenter.STATE_HOUR;
import static com.ten.tencloud.module.server.presenter.ServerMonitorPresenter.STATE_WEEK;

public class ServerDetail2Activity extends BaseActivity
        implements ServerDetailContract.View, ServerMonitorContract.View, ServerSystemLoadContract.View {

    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.tv_ip)
    TextView mTvIP;
    @BindView(R.id.iv_provider)
    ImageView mIvProvider;

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

    //配置信息
    @BindView(R.id.tv_os_name)
    TextView mTvOsName;
    @BindView(R.id.tv_cpu)
    TextView mTvCpu;
    @BindView(R.id.tv_memory)
    TextView mTvMemory;
    @BindView(R.id.ll_disk_info)
    LinearLayout mLlDiskInfo;

    @BindView(R.id.lc_cpu)
    LineChart mLcCpu;
    @BindView(R.id.lc_memory)
    LineChart mLcMemory;
    @BindView(R.id.lc_disk)
    LineChart mLcDisk;
    @BindView(R.id.lc_net)
    LineChart mLcNet;

    //周期
    @BindView(R.id.rg_cycle)
    RadioGroup mRgCycle;
    @BindView(R.id.btn_one_hour)
    RadioButton mBtnOneHour;
    @BindView(R.id.btn_one_day)
    RadioButton mBtnOneDay;
    @BindView(R.id.btn_one_week)
    RadioButton mBtnOneWeek;

    //--图表
    @BindView(R.id.tv_progress_cpu)
    TextView mTvProgressCpu;
    @BindView(R.id.pv_cpu)
    ProgressSemiCircleView mPvCpu;
    @BindView(R.id.tv_progress_memory)
    TextView mTvProgressMemory;
    @BindView(R.id.pv_memory)
    ProgressPieView mPvMemory;
    @BindView(R.id.tv_progress_disk_util)
    TextView mTvProgressDiskUtil;
    @BindView(R.id.pv_disk_util)
    ProgressRingView mPvDiskUtil;
    @BindView(R.id.tv_progress_disk_usage)
    TextView mTvProgressDiskUsage;
    @BindView(R.id.pv_disk_usage)
    ProgressRingView mPvDiskUsage;
    @BindView(R.id.tv_progress_net_in)
    TextView mTvProgressNetIn;
    @BindView(R.id.pv_net_in)
    ProgressRectView mPvNetIn;
    @BindView(R.id.tv_progress_net_out)
    TextView mTvProgressNetOut;
    @BindView(R.id.pv_net_out)
    ProgressRectView mPvNetOut;
    @BindView(R.id.tv_progress_net_in_max)
    TextView mTvProgressNetInMax;
    @BindView(R.id.tv_progress_net_out_max)
    TextView mTvProgressNetOutMax;

    private String mServerName;
    private String mServerId;

    //当前周期
    private int mCycle = STATE_HOUR;

    private ServerDetailPresenter mServerDetailPresenter;
    private ServerMonitorPresenter mServerMonitorPresenter;
    private ServerSystemLoadPresenter mServerSystemLoadPresenter;
    private RefreshBroadCastHandler mBroadCastHandler;
    private ServerSystemLoadDialog mServerSystemLoadDialog;
    private Subscription mLoopSubscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_server_detail2);
        initTitleBar(true, "主机详情");
        mServerName = getIntent().getStringExtra("name");
        mServerId = getIntent().getStringExtra("id");

        mServerDetailPresenter = new ServerDetailPresenter();
        mServerDetailPresenter.attachView(this);
        mServerSystemLoadPresenter = new ServerSystemLoadPresenter();
        mServerSystemLoadPresenter.attachView(this);
        mServerMonitorPresenter = new ServerMonitorPresenter();
        mServerMonitorPresenter.attachView(this);

        mBroadCastHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.SERVER_INFO_CHANGE_ACTION);
        mBroadCastHandler.registerReceiver(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mServerDetailPresenter.getServerDetail(mServerId);
            }
        });
        initView();
        initData();
    }

    private void initData() {
        mServerDetailPresenter.getServerDetail(mServerId);
        mServerMonitorPresenter.getServerMonitorInfo(mServerId, mCycle);
        mLoopSubscribe = Observable.interval(0, 30, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        mServerSystemLoadPresenter.getServerSystemLoad(mServerId);
                    }
                });
    }

    private void initView() {
        mTvName.setText(mServerName);
        initLineChart();
        mRgCycle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.btn_one_hour: {
                        if (mCycle != STATE_HOUR) {
                            mCycle = STATE_HOUR;
                            mServerMonitorPresenter.getServerMonitorInfo(mServerId, mCycle);
                        }
                        break;
                    }
                    case R.id.btn_one_day: {
                        if (mCycle != STATE_DAY) {
                            mCycle = STATE_DAY;
                            mServerMonitorPresenter.getServerMonitorInfo(mServerId, mCycle);
                        }
                        break;
                    }
                    case R.id.btn_one_week: {
                        if (mCycle != STATE_WEEK) {
                            mCycle = STATE_WEEK;
                            mServerMonitorPresenter.getServerMonitorInfo(mServerId, mCycle);
                        }
                        break;
                    }
                }
            }
        });
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

    @OnClick({R.id.rl_basic_detail, R.id.tv_more, R.id.btn_toolbox,
            R.id.tv_load_des, R.id.tv_config_more, R.id.tv_cost_more,
            R.id.tv_res_more, R.id.tv_res_more_})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_basic_detail: {
                Intent intent = new Intent(mContext, ServerDetailBasicActivity.class);
                intent.putExtra("id", mServerId);
                startActivityForResult(intent, 0);
                break;
            }
            case R.id.tv_more: {
                Intent intent = new Intent(mContext, ServerPerformanceHistoryActivity.class);
                intent.putExtra("id", mServerId);
                intent.putExtra("name", mServerName);
                mContext.startActivity(intent);
                break;
            }
            case R.id.btn_toolbox: {
                BlurBuilder.snapShotWithoutStatusBar(this);
                Intent intent = new Intent(mContext, ServerToolBoxActivity.class);
                intent.putExtra("serverId", mServerId);
                startActivity(intent);
                overridePendingTransition(0, 0);
                break;
            }
            case R.id.tv_load_des: {
                if (mServerSystemLoadDialog == null) {
                    mServerSystemLoadDialog = new ServerSystemLoadDialog(mContext);
                }
                mServerSystemLoadDialog.show();
                break;
            }
            case R.id.tv_config_more: {
                Intent intent = new Intent(mContext, ServerDetailConfigActivity.class);
                intent.putExtra("serverId", mServerId);
                startActivity(intent);
                break;
            }
            case R.id.tv_cost_more: {

                break;
            }
            case R.id.tv_res_more_:
            case R.id.tv_res_more: {
                Intent intent = new Intent(mContext, ServerMonitorActivity.class);
                intent.putExtra("serverId", mServerId);
                startActivity(intent);
                break;
            }
        }
    }

    @Override
    public void showServerDetail(ServerDetailBean serverDetailBean) {
        mTvName.setText(serverDetailBean.getBasic_info().getName());
        mTvIP.setText(serverDetailBean.getBasic_info().getPublic_ip());
        //设置运营商icon
        String provider = serverDetailBean.getBusiness_info().getProvider();
        if ("阿里云".equals(provider)) {
            mIvProvider.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_aliyun));
        } else if ("亚马逊云".equals(provider)) {
            mIvProvider.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_amazon));
        } else if ("微软云".equals(provider)) {
            mIvProvider.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_microyun));
        }
        //设置状态
        String state = serverDetailBean.getBasic_info().getMachine_status();
        mTvStatus.setText(state);
        mTvStatus.setSelected(true);
        mTvStatus.setEnabled(!"已关机".equals(state));
        if ("故障".equals(state) || ("异常").equals(state)) {
            mTvStatus.setSelected(false);
        }
        //配置信息
        ServerDetailBean.SystemInfoBean.ConfigBean config = serverDetailBean.getSystem_info().getConfig();
        mTvCpu.setText(com.ten.tencloud.utils.Utils.strIsEmptyForDefault(config.getCpu() + "", "无"));
        mTvMemory.setText(com.ten.tencloud.utils.Utils.strIsEmptyForDefault(config.getMemory() / 1024 + "GB", "无"));
        mTvOsName.setText(com.ten.tencloud.utils.Utils.strIsEmptyForDefault(config.getOs_name(), "无"));
        addDiskInfoUI(serverDetailBean.getSystem_info().getConfig().getDisk_info());
    }

    private void addDiskInfoUI(List<ServerDetailBean.BusinessInfoBean.DiskInfo> disk_info) {
        mLlDiskInfo.removeAllViews();
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < disk_info.size(); i++) {
            View view = inflater.inflate(R.layout.item_server_monitor_disk_info, null);
            TextView tvName = view.findViewById(R.id.tv_disk_name);
            TextView tvName_ = view.findViewById(R.id.tv_disk_name_);
            TextView tvType = view.findViewById(R.id.tv_disk_type);
            TextView tvSize = view.findViewById(R.id.tv_disk_size);
            if (disk_info.size() == 1) {
                tvName.setText("存储");
                tvName_.setText("存储");
            } else {
                tvName.setText("存储" + (i + 1));
                tvName_.setText("存储" + (i + 1));
            }
            tvType.setText(disk_info.get(i).getSystem_disk_type());
            tvSize.setText(disk_info.get(i).getSystem_disk_size() + "");
            mLlDiskInfo.addView(view);
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

    @Override
    public void showServerSystemLoad(ServerSystemLoadBean systemLoadBean) {
        if (systemLoadBean == null) {
            return;
        }
        mTvOsTime.setText(systemLoadBean.getDate());
        mTvLoginCount.setText(systemLoadBean.getLogin_users() + "");
        mTvRunDuration.setText(systemLoadBean.getRun_time());
        setMinuteLoadStyle(mTvLoad1, systemLoadBean.getOne_minute_load(), 0.7f);
        setMinuteLoadStyle(mTvLoad5, systemLoadBean.getFive_minute_load(), 0.7f);
        setMinuteLoadStyle(mTvLoad15, systemLoadBean.getFifth_minute_load(), 0.7f);
        mTvLoad1.setText(systemLoadBean.getOne_minute_load() + "");
        mTvLoad5.setText(systemLoadBean.getFive_minute_load() + "");
        mTvLoad15.setText(systemLoadBean.getFifth_minute_load() + "");

        ServerThresholdBean serverThreshold = AppBaseCache.getInstance().getServerThreshold();
        ServerSystemLoadBean.Monitor monitor = systemLoadBean.getMonitor();
        String[] net = monitor.getNetUsageRate().split("/");
        mTvProgressNetInMax.setText(monitor.getNetInputMax());
        mTvProgressNetOutMax.setText(monitor.getNetOutputMax());
        setMinuteLoadStyle(mTvProgressCpu, monitor.getCpuUsageRate(), serverThreshold.getCpu_threshold());
        setMinuteLoadStyle(mTvProgressMemory, monitor.getMemUsageRate(), serverThreshold.getMemory_threshold());
        setMinuteLoadStyle(mTvProgressDiskUsage, monitor.getDiskUsageRate(), serverThreshold.getDisk_threshold());
        setMinuteLoadStyle(mTvProgressDiskUtil, monitor.getDiskUtilize(), serverThreshold.getBlock_threshold());
        setMinuteLoadStyle(mTvProgressNetIn, Float.parseFloat(net[0]), serverThreshold.getNet_threshold());
        setMinuteLoadStyle(mTvProgressNetOut, Float.parseFloat(net[1]), serverThreshold.getNet_threshold());
        mTvProgressCpu.setText(monitor.getCpuUsageRate() + "%");
        mPvCpu.setThreshold(serverThreshold.getCpu_threshold());
        mPvCpu.setProgress((int) monitor.getCpuUsageRate());
        mTvProgressMemory.setText(monitor.getMemUsageRate() + "%");
        mPvMemory.setThreshold(serverThreshold.getMemory_threshold());
        mPvMemory.setProgress((int) monitor.getMemUsageRate());
        mTvProgressDiskUtil.setText(monitor.getDiskUtilize() + "%");
        mPvDiskUtil.setThreshold(serverThreshold.getBlock_threshold());
        mPvDiskUtil.setProgress((int) monitor.getDiskUtilize());
        mTvProgressDiskUsage.setText(monitor.getDiskUsageRate() + "%");
        mPvDiskUsage.setThreshold(serverThreshold.getDisk_threshold());
        mPvDiskUsage.setProgress((int) monitor.getDiskUsageRate());
        mPvNetIn.setThreshold(serverThreshold.getNet_threshold());
        mPvNetIn.setProgress((int) Float.parseFloat(net[0]));
        mPvNetOut.setThreshold(serverThreshold.getNet_threshold());
        mPvNetOut.setProgress((int) Float.parseFloat(net[1]));
        mTvProgressNetIn.setText(monitor.getNetDownload());
        mTvProgressNetOut.setText(monitor.getNetUpload());
    }

    private void setMinuteLoadStyle(TextView tv, float value, float threshold) {
        tv.setEnabled(value < threshold);
        tv.setSelected(value < threshold);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.ACTIVITY_RESULT_CODE_FINISH) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mServerDetailPresenter.detachView();
        mServerDetailPresenter = null;
        mServerMonitorPresenter.detachView();
        mServerMonitorPresenter = null;
        mServerSystemLoadPresenter.detachView();
        mServerSystemLoadPresenter = null;
        mBroadCastHandler.unregisterReceiver();
        mLoopSubscribe.unsubscribe();
    }
}
