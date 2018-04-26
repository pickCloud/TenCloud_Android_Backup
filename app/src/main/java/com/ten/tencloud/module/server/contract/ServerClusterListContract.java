package com.ten.tencloud.module.server.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.ClusterBean;

import java.util.List;

/**
 * Created by lxq on 2017/11/23.
 */

public class ServerClusterListContract {
    public interface View extends IBaseView {
        void showClusterList(List<ClusterBean> data);

        void showEmpty();

    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {
        void getClusterList();
    }
}
