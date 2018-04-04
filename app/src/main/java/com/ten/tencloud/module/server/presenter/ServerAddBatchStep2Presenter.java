package com.ten.tencloud.module.server.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.ServerBatchBean;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.server.contract.ServerAddBatchStep2Contract;
import com.ten.tencloud.module.server.model.ServerModel;

import java.util.List;

/**
 * Created by lxq on 2017/11/23.
 */

public class ServerAddBatchStep2Presenter extends BasePresenter<ServerAddBatchStep2Contract.View>
        implements ServerAddBatchStep2Contract.Presenter<ServerAddBatchStep2Contract.View> {

    @Override
    public void submitCredential(int cloudId, String access_key, String access_secret) {
        mSubscriptions.add(ServerModel.getInstance().submitCloudCredential(cloudId, access_key, access_secret)
                .subscribe(new JesSubscribe<List<ServerBatchBean>>(mView) {
                    @Override
                    public void _onSuccess(List<ServerBatchBean> serverBatchBeans) {
                        mView.showProviderServers(serverBatchBeans);
                    }
                }));
    }
}
