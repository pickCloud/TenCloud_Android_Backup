package com.ten.tencloud.module.server.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.ProviderBean;
import com.ten.tencloud.bean.ServerBean;

import java.util.List;
import java.util.Set;

/**
 * Created by lxq on 2017/11/23.
 */

public class ServerListContract {
    public interface View extends IBaseView {
        void showServerList(List<ServerBean> servers);

        void showEmpty();

        void showProviders(List<ProviderBean> providers);
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {
        void getServerList(int id);

        void getProvidersByCluster(String id);

        void search(String clusterId, String serverName,
                    List<String> region, Set<String> provider);
    }
}
