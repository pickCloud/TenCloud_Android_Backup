package com.ten.tencloud.module.app.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.AppBean;

/**
 * Create by chenxh@10.com on 2018/4/3.
 */
public interface AppDetailContract {

    interface View extends IBaseView {

        void showAppDetail(AppBean appBean);
    }

    interface Presenter<V extends IBaseView> extends IBasePresenter<V> {

        void getAppById(int id);
    }
}