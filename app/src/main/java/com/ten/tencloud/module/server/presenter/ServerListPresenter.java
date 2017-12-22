package com.ten.tencloud.module.server.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.ProviderBean;
import com.ten.tencloud.bean.ServerBean;
import com.ten.tencloud.constants.GlobalStatusManager;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.server.contract.ServerListContract;
import com.ten.tencloud.module.server.model.ServerModel;

import java.util.List;
import java.util.Set;

/**
 * Created by lxq on 2017/11/23.
 */

public class ServerListPresenter extends BasePresenter<ServerListContract.View>
        implements ServerListContract.Presenter<ServerListContract.View> {

    @Override
    public void getServerList(int id) {
        mSubscriptions.add(ServerModel.getInstance().getServerList(id).subscribe(new JesSubscribe<List<ServerBean>>(mView) {
            @Override
            public void _onSuccess(List<ServerBean> serverBeans) {
                GlobalStatusManager.getInstance().setServerListNeedRefresh(false);
                mView.showServerList(serverBeans);
            }
        }));
    }

    @Override
    public void getProvidersByCluster(String id) {
        mSubscriptions.add(ServerModel.getInstance().getProvidersByCluster(id).subscribe(new JesSubscribe<List<ProviderBean>>(mView) {
            @Override
            public void _onSuccess(List<ProviderBean> providerBeans) {
                mView.showProviders(providerBeans);
            }
        }));
    }

    @Override
    public void search(String clusterId, String serverName, List<String> region, Set<String> provider) {
        mSubscriptions.add(ServerModel.getInstance().search(clusterId, serverName, region, provider)
                .subscribe(new JesSubscribe<List<ServerBean>>(mView) {
                    @Override
                    public void _onSuccess(List<ServerBean> serverBeans) {
                        if (serverBeans == null || serverBeans.size() == 0) {
                            mView.showEmpty();
                        } else {
                            mView.showServerList(serverBeans);
                        }
                    }
                }));
    }


}
