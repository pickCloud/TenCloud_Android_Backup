package com.ten.tencloud.module.server.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.ServerHistoryBean;

import java.util.List;

/**
 * Created by lxq on 2017/11/23.
 */

public class ServerHistoryContract {
    public interface View extends IBaseView {
        void showServerHistory(List<ServerHistoryBean> serverHistoryBean,boolean isLoadMore);

        void showNoData(boolean isLoadMore);
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {
        void getServerHistory(boolean isLoadMore, String id, int type, String startTime, String endTime);
    }
}
