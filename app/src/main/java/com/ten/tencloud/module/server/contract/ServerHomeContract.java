package com.ten.tencloud.module.server.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.ServerBean;

import java.util.List;

/**
 * Created by lxq on 2017/11/23.
 */

public class ServerHomeContract {
    public interface View extends IBaseView {
        void showWarnServerList(List<ServerBean> servers);

        void showEmptyView();

        void showSummary(int server_num, int warn_num, int payment_num);
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {
        void getWarnServerList(int id);

        void summary();
    }
}
