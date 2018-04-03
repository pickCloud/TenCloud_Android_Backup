package com.ten.tencloud.module.server.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.ServerProviderBean;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.server.contract.ServerAddBatchStep1Contract;
import com.ten.tencloud.module.server.model.ServerModel;

import java.util.List;

/**
 * Created by lxq on 2017/11/23.
 */

public class ServerAddBatchStep1Presenter extends BasePresenter<ServerAddBatchStep1Contract.View>
        implements ServerAddBatchStep1Contract.Presenter<ServerAddBatchStep1Contract.View> {

    @Override
    public void getServerProviders() {
        mSubscriptions.add(ServerModel.getInstance().getServerProvides()
                .subscribe(new JesSubscribe<List<ServerProviderBean>>(mView) {
                    @Override
                    public void _onSuccess(List<ServerProviderBean> providerBeans) {
                        mView.showServerProviders(providerBeans);
                    }
                }));
    }
}
