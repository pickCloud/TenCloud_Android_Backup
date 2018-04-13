package com.ten.tencloud.module.server.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.ServerBatchBean;

import java.util.List;

/**
 * Created by lxq on 2017/11/23.
 */

public class ServerAddBatchStep3Contract {
    public interface View extends IBaseView {
        void importServersSuccess(Object o);
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {
        void importServers(List<ServerBatchBean> data);
    }
}
