package com.ten.tencloud.module.server.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.base.adapter.CJSVpPagerAdapter;
import com.ten.tencloud.base.view.BaseFragment;
import com.ten.tencloud.base.view.BasePager;
import com.ten.tencloud.bean.ServerBean;
import com.ten.tencloud.bean.ServerHeatBean;
import com.ten.tencloud.bean.ServerMonitorBean;
import com.ten.tencloud.bean.ServerThresholdBean;
import com.ten.tencloud.broadcast.RefreshBroadCastHandler;
import com.ten.tencloud.listener.OnRefreshListener;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.module.server.adapter.RvServerAdapter;
import com.ten.tencloud.module.server.adapter.RvServerHeatChartAdapter;
import com.ten.tencloud.module.server.contract.ServerHomeContract;
import com.ten.tencloud.module.server.contract.ServerMonitorContract;
import com.ten.tencloud.module.server.presenter.ServerHomePresenter;
import com.ten.tencloud.module.server.presenter.ServerMonitorPresenter;
import com.ten.tencloud.utils.Utils;
import com.ten.tencloud.widget.HeatLayout;
import com.ten.tencloud.widget.navigator.ScaleCircleNavigator;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by lxq on 2017/11/22.
 */

public class ServerHomeFragment extends BaseFragment implements ServerHomeContract.View, ServerMonitorContract.View {


    @BindView(R.id.xrv_servers)
    RecyclerView mRvServer;
    @BindView(R.id.empty_view)
    View mEmptyView;
    @BindView(R.id.tv_add_server)
    TextView mTvAddServer;

    @BindView(R.id.tv_total)
    TextView mTvServerTotal;
    @BindView(R.id.tv_alarm)
    TextView mTvServerAlarm;
    @BindView(R.id.tv_cluster)
    TextView mTvServerCluster;

    @BindView(R.id.rv_heat)
    RecyclerView mRvHeat;
    @BindView(R.id.hl_single_layout)
    HeatLayout mHlSingleLayout;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.vp_single_heat)
    ViewPager mVpSingleHeat;
    @BindView(R.id.single_indicator)
    MagicIndicator mSingleIndicator;

    //阈值
    @BindView(R.id.tv_cpu_threshold)
    TextView mTvCpuThreshold;
    @BindView(R.id.tv_memory_threshold)
    TextView mTvMemoryThreshold;
    @BindView(R.id.tv_net_in_threshold)
    TextView mTvNetInThreshold;
    @BindView(R.id.tv_net_out_threshold)
    TextView mTvNetOutThreshold;
    @BindView(R.id.tv_block_threshold)
    TextView mTvBlockThreshold;
    @BindView(R.id.tv_disk_threshold)
    TextView mTvDiskThreshold;

    @BindView(R.id.ll_heat)
    LinearLayout mLlHeat;

    private RvServerAdapter mServerAdapter;
    private ServerHomePresenter mPresenter;
    private ServerMonitorPresenter mServerMonitorPresenter;
    private boolean mPermissionAddServer;

    private RefreshBroadCastHandler mPermissionRefreshBroadCastHandler;
    private RefreshBroadCastHandler mSwitchCompanyRefreshBroadCastHandler;
    private RefreshBroadCastHandler mServerRefreshHandler;
    private RvServerHeatChartAdapter mHeatChartAdapter;
    private Subscription mHeatSubscribe;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return createView(inflater, container, R.layout.fragment_server_home);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        OnRefreshListener onRefreshListener = new OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        };

        mPermissionRefreshBroadCastHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.PERMISSION_REFRESH_ACTION);
        mPermissionRefreshBroadCastHandler.registerReceiver(onRefreshListener);
        mSwitchCompanyRefreshBroadCastHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.SWITCH_COMPANY_REFRESH_ACTION);
        mSwitchCompanyRefreshBroadCastHandler.registerReceiver(onRefreshListener);
        mServerRefreshHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.SERVER_LIST_CHANGE_ACTION);
        mServerRefreshHandler.registerReceiver(onRefreshListener);

        mPresenter = new ServerHomePresenter();
        mPresenter.attachView(this);
        //单台服务器时加载监控数据
        mServerMonitorPresenter = new ServerMonitorPresenter();
        mServerMonitorPresenter.attachView(this);
        initView();
        initData();
    }

    private void initData() {
        mSingleServer = null;
        mPresenter.summary();
        mPresenter.getWarnServerList(1);
        mPresenter.getServerMonitor();
        startHeat();
    }

    private void initView() {
        mPermissionAddServer = Utils.hasPermission("添加主机");
        mTvAddServer.setVisibility(mPermissionAddServer ? View.VISIBLE : View.GONE);
        mHeatChartAdapter = new RvServerHeatChartAdapter(mActivity);
        mRvHeat.setAdapter(mHeatChartAdapter);
        mHeatChartAdapter.setOnItemClickListener(new CJSBaseRecyclerViewAdapter.OnItemClickListener<ServerHeatBean>() {
            @Override
            public void onObjectItemClicked(ServerHeatBean serverHeatBean, int position) {
                Intent intent = new Intent(mActivity, ServerDetail2Activity.class);
                intent.putExtra("name", serverHeatBean.getName());
                intent.putExtra("id", serverHeatBean.getServerID() + "");
                startActivity(intent);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;//禁止滑动，处理与scrollview的冲突
            }
        };
        mRvServer.setLayoutManager(layoutManager);
        mServerAdapter = new RvServerAdapter(mActivity);
        mServerAdapter.setOnItemClickListener(new CJSBaseRecyclerViewAdapter.OnItemClickListener<ServerBean>() {
            @Override
            public void onObjectItemClicked(ServerBean serverBean, int position) {
                Intent intent = new Intent(mActivity, ServerDetail2Activity.class);
                intent.putExtra("name", serverBean.getName());
                intent.putExtra("id", serverBean.getId());
                startActivity(intent);
            }
        });
        mRvServer.setAdapter(mServerAdapter);
        mRvServer.setFocusableInTouchMode(false);//处理自动滚动
        mRvHeat.setFocusableInTouchMode(false);
        showThreshold(AppBaseCache.getInstance().getServerThreshold());
    }

    //热图单台服务器
    private ServerHeatBean mSingleServer;

    private void initHeatChart(final List<ServerHeatBean> datas) {
        int size = datas.size();
        if (size == 1) {
            mRvHeat.setVisibility(View.GONE);
            mHlSingleLayout.setVisibility(View.VISIBLE);
            mSingleServer = datas.get(0);
            mTvTitle.setText(mSingleServer.getName());
            mHlSingleLayout.setHeatLevel(mSingleServer.getColorType());
            mServerMonitorPresenter.getServerMonitorInfo(mSingleServer.getServerID() + "", ServerMonitorPresenter.STATE_HOUR);
            return;
        }
        int spanCount = 1;
        int type = 0;
        if (datas.size() >= 2 && datas.size() <= 4) {
            spanCount = 2;
            type = RvServerHeatChartAdapter.TYPE_TWO;
        } else if (datas.size() > 4 && datas.size() <= 9) {
            spanCount = 3;
            type = RvServerHeatChartAdapter.TYPE_THREE;
        } else if (datas.size() > 9) {
            spanCount = 4;
            type = RvServerHeatChartAdapter.TYPE_FOUR;
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, spanCount) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRvHeat.setLayoutManager(gridLayoutManager);
        mHeatChartAdapter.setType(type);
        mHeatChartAdapter.setDatas(datas);
    }

    private void initMagicIndicator() {
        ScaleCircleNavigator circleNavigator = new ScaleCircleNavigator(mActivity);
        circleNavigator.setCircleCount(4);
        circleNavigator.setSkimOver(true);
        circleNavigator.setNormalCircleColor(getResources().getColor(R.color.color_33ffffff));
        circleNavigator.setSelectedCircleColor(getResources().getColor(R.color.color_80ffffff));
        circleNavigator.setCircleClickListener(new ScaleCircleNavigator.OnCircleClickListener() {
            @Override
            public void onClick(int index) {
                mVpSingleHeat.setCurrentItem(index);
            }
        });
        mSingleIndicator.setNavigator(circleNavigator);
        ViewPagerHelper.bind(mSingleIndicator, mVpSingleHeat);
    }

    private void startHeat() {
        if (mHeatSubscribe == null || mHeatSubscribe.isUnsubscribed()) {
            mHeatSubscribe = Observable.interval(20, 20, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            mPresenter.getWarnServerList(1);
                            mPresenter.getServerMonitor();
                        }
                    });
        }
    }

    @OnClick({R.id.tv_add_server, R.id.tv_more, R.id.rl_server,
            R.id.rl_cluster, R.id.rl_alarm, R.id.hl_single_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add_server:
                startActivity(new Intent(mActivity, ServerAddActivity.class));
                break;
            case R.id.tv_more:
                startActivity(new Intent(mActivity, ServerListActivity.class));
                break;
            case R.id.rl_server:
                startActivity(new Intent(mActivity, ServerListActivity.class));
                break;
            case R.id.rl_cluster:

                break;
            case R.id.rl_alarm:

                break;
            case R.id.hl_single_layout:

                break;
        }
    }

    @Override
    public void showServerMonitorInfo(ServerMonitorBean serverMonitor) {
        String json = TenApp.getInstance().getGsonInstance().toJson(serverMonitor);
        ArrayList<BasePager> pagers = new ArrayList<>();
        pagers.add(new ServerSingleHeatChartPager(mActivity).putArgument("data", json)
                .putArgument("serverId", mSingleServer.getServerID() + "")
                .putArgument("type", ServerSingleHeatChartPager.TYPE_CPU));
        pagers.add(new ServerSingleHeatChartPager(mActivity).putArgument("data", json)
                .putArgument("serverId", mSingleServer.getServerID() + "")
                .putArgument("type", ServerSingleHeatChartPager.TYPE_MEMORY));
        pagers.add(new ServerSingleHeatChartPager(mActivity).putArgument("data", json)
                .putArgument("serverId", mSingleServer.getServerID() + "")
                .putArgument("type", ServerSingleHeatChartPager.TYPE_DISK));
        pagers.add(new ServerSingleHeatChartPager(mActivity).putArgument("data", json)
                .putArgument("serverId", mSingleServer.getServerID() + "")
                .putArgument("type", ServerSingleHeatChartPager.TYPE_NET));
        mVpSingleHeat.setAdapter(new CJSVpPagerAdapter(pagers));
        for (BasePager pager : pagers) {
            pager.init();
        }
        initMagicIndicator();
    }

    @Override
    public void showWarnServerList(List<ServerBean> servers) {
        mServerAdapter.setDatas(servers);
        mLlHeat.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyView() {
        mEmptyView.setVisibility(View.VISIBLE);
        mLlHeat.setVisibility(View.GONE);
        mServerAdapter.clear();
    }

    @Override
    public void showSummary(int server_num, int warn_num, int payment_num) {
        mTvServerTotal.setText(server_num + "");
        mTvServerAlarm.setText(warn_num + "");
        mTvServerCluster.setText(payment_num + "");
        mTvServerTotal.setSelected(server_num != 0);
        mTvServerAlarm.setSelected(warn_num != 0);
        mTvServerCluster.setSelected(payment_num != 0);
    }

    @Override
    public void showThreshold(ServerThresholdBean serverThresholdBean) {
        if (serverThresholdBean != null) {
            mTvCpuThreshold.setText(serverThresholdBean.getCpu_threshold() + "%");
            mTvBlockThreshold.setText(serverThresholdBean.getBlock_threshold() + "%");
            mTvDiskThreshold.setText(serverThresholdBean.getDisk_threshold() + "%");
            mTvMemoryThreshold.setText(serverThresholdBean.getMemory_threshold() + "%");
            mTvNetInThreshold.setText(serverThresholdBean.getNet_threshold() + "%");
            mTvNetOutThreshold.setText(serverThresholdBean.getNet_threshold() + "%");
        }
    }

    @Override
    public void showServerMonitor(List<ServerHeatBean> data) {
        initHeatChart(data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPermissionRefreshBroadCastHandler.unregisterReceiver();
        mPermissionRefreshBroadCastHandler = null;
        mSwitchCompanyRefreshBroadCastHandler.unregisterReceiver();
        mSwitchCompanyRefreshBroadCastHandler = null;
        mServerRefreshHandler.unregisterReceiver();
        mServerRefreshHandler = null;
        mPresenter.detachView();
        mServerMonitorPresenter.detachView();
        if (mHeatSubscribe != null) {
            mHeatSubscribe.unsubscribe();
        }
    }
}
