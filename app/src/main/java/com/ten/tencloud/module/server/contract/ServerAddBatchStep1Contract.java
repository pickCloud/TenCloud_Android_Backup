package com.ten.tencloud.module.server.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.ServerProviderBean;

import java.util.List;

/**
 * Created by lxq on 2017/11/23.
 */

public class ServerAddBatchStep1Contract {
    public interface View extends IBaseView {
        void showServerProviders(List<ServerProviderBean> providerBeans);
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {
        void getServerProviders();
    }
}
