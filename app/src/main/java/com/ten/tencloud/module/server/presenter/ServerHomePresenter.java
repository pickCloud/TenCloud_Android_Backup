package com.ten.tencloud.module.server.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.ServerBean;
import com.ten.tencloud.bean.ServerThresholdBean;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.server.contract.ServerHomeContract;
import com.ten.tencloud.module.server.model.ServerModel;

import java.util.List;
import java.util.Map;

/**
 * Created by lxq on 2017/11/23.
 */

public class ServerHomePresenter extends BasePresenter<ServerHomeContract.View>
        implements ServerHomeContract.Presenter<ServerHomeContract.View> {

    @Override
    public void getWarnServerList(int id) {
        mSubscriptions.add(ServerModel.getInstance().getWarnServerList(id)
                .subscribe(new JesSubscribe<List<ServerBean>>(mView) {
                    @Override
                    public void _onSuccess(List<ServerBean> serverBeans) {
                        if (serverBeans == null || serverBeans.size() == 0) {
                            mView.showEmptyView();
                        } else {
                            mView.showWarnServerList(serverBeans);
                        }
                    }
                }));
    }

    @Override
    public void getThreshold() {
        mSubscriptions.add(ServerModel.getInstance().getThreshold()
                .subscribe(new JesSubscribe<ServerThresholdBean>(mView) {
                    @Override
                    public void _onSuccess(ServerThresholdBean thresholdBean) {
                        mView.showThreshold(thresholdBean);
                    }
                }));
    }

    @Override
    public void summary() {
        mSubscriptions.add(ServerModel.getInstance().summary()
                .subscribe(new JesSubscribe<Map<String, Integer>>(mView) {
                    @Override
                    public void _onSuccess(Map<String, Integer> stringIntegerMap) {
                        mView.showSummary(stringIntegerMap.get("server_num"),
                                stringIntegerMap.get("warn_num"),
                                stringIntegerMap.get("payment_num"));
                    }
                }));
    }
}
