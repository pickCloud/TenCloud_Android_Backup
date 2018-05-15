package com.ten.tencloud.module.app.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.DeploymentBean;

/**
 * Create by chenxh@10.com on 2018/4/3.
 */
public interface AppDeployListContract {

    interface View extends IBaseView {

        void showList(DeploymentBean data);
    }

    interface Presenter<V extends IBaseView> extends IBasePresenter<V> {

        void getDeployList(int id, int page);
    }
}