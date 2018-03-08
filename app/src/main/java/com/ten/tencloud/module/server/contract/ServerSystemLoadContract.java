package com.ten.tencloud.module.server.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.ServerSystemLoadBean;

/**
 * Created by lxq on 2017/11/23.
 */

public class ServerSystemLoadContract {
    public interface View extends IBaseView {
        void showServerSystemLoad(ServerSystemLoadBean systemLoadBean);
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {
        void getServerSystemLoad(String id);
    }
}
