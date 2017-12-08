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

    @Override
    public void getServerMonitorInfo(String id, String startTime, String endTime) {
        mSubscriptions.add(ServerModel.getInstance().getServerPerformance(id, startTime, endTime)
                .subscribe(new JesSubscribe<ServerMonitorBean>(mView) {
                    @Override
                    public void _onSuccess(ServerMonitorBean o) {
                        mView.showServerMonitorInfo(o);
                    }
                }));
    }
}
