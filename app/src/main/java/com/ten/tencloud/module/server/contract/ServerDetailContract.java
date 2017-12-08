package com.ten.tencloud.module.server.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.ServerDetailBean;

/**
 * Created by lxq on 2017/11/23.
 */

public class ServerDetailContract {
    public interface View extends IBaseView {
        void showServerDetail(ServerDetailBean serverDetailBean);
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {
        void getServerDetail(String id);
    }
}
