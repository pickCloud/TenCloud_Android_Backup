package com.ten.tencloud.module.server.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.ServerMonitorBean;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.server.contract.ServerMonitorContract;
import com.ten.tencloud.module.server.model.ServerModel;

/**
 * Created by lxq on 2017/11/23.
 */

public class ServerMonitorPresenter extends BasePresenter<ServerMonitorContract.View>
        implements ServerMonitorContract.Presenter<ServerMonitorContract.View> {

    //监控周期
    public final static int STATE_HOUR = 1;
    public final static int STATE_DAY = 2;
    public final static int STATE_WEEK = 3;
    public final static int STATE_MONTH = 4;


    @Override
    public void getServerMonitorInfo(String id, int cycleState) {
        String[] cycle = handCycle(cycleState);
        mSubscriptions.add(ServerModel.getInstance().getServerPerformance(id, cycle[0], cycle[1])
                .subscribe(new JesSubscribe<ServerMonitorBean>(mView) {
                    @Override
                    public void _onSuccess(ServerMonitorBean o) {
                        mView.showServerMonitorInfo(o);
                    }
                }));
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
