package com.ten.tencloud.module.server.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.ServerLogBean;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.server.contract.ServerLogContract;
import com.ten.tencloud.module.server.model.ServerModel;

import java.util.List;

/**
 * Created by lxq on 2017/11/23.
 */

public class ServerLogPresenter extends BasePresenter<ServerLogContract.View>
        implements ServerLogContract.Presenter<ServerLogContract.View> {

    @Override
    public void getServerLogList(String id) {
        mSubscriptions.add(ServerModel.getInstance().getServerLog(id)
                .subscribe(new JesSubscribe<List<ServerLogBean.LogInfo>>(mView) {
                    @Override
                    public void _onSuccess(List<ServerLogBean.LogInfo> logInfos) {
                        if (logInfos == null || logInfos.size() == 0) {
                            mView.showEmpty();
                            return;
                        }
                        mView.showServerLogList(logInfos);
                    }
                }));
    }
}
