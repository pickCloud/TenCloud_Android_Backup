package com.ten.tencloud.module.server.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.ServerBatchBean;

import java.util.List;

/**
 * Created by lxq on 2017/11/23.
 */

public class ServerAddBatchStep2Contract {
    public interface View extends IBaseView {
        void showProviderServers(List<ServerBatchBean> servers);
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {
        void submitCredential(int cloudId, String access_key, String access_secret);
    }
}
