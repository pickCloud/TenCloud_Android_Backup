package com.ten.tencloud.module.server.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.ServerBatchBean;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.server.contract.ServerAddBatchStep3Contract;
import com.ten.tencloud.module.server.model.ServerModel;

import java.util.List;

/**
 * Created by lxq on 2017/11/23.
 */

public class ServerAddBatchStep3Presenter extends BasePresenter<ServerAddBatchStep3Contract.View>
        implements ServerAddBatchStep3Contract.Presenter<ServerAddBatchStep3Contract.View> {

    @Override
    public void importServers(List<ServerBatchBean> data) {
        mSubscriptions.add(ServerModel.getInstance().importServers(data)
                .subscribe(new JesSubscribe<Object>(mView) {
                    @Override
                    public void _onSuccess(Object o) {
                        mView.importServersSuccess(o);
                    }
                }));
    }
}
