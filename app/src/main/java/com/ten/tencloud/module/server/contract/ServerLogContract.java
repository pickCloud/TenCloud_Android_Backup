package com.ten.tencloud.module.server.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.ServerLogBean;

import java.util.List;

/**
 * Created by lxq on 2017/11/23.
 */

public class ServerLogContract {
    public interface View extends IBaseView {
        void showServerLogList(List<ServerLogBean.LogInfo> servers);

        void showEmpty();
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {
        void getServerLogList(String id);
    }
}
