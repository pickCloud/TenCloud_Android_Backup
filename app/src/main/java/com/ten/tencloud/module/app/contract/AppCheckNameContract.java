package com.ten.tencloud.module.app.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;

/**
 * Create by chenxh@10.com on 2018/4/2.
 */
public class AppCheckNameContract {

    public interface View extends IBaseView {
        void checkResult();
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {
        void checkDeployName(String name, int appId);
    }
}
