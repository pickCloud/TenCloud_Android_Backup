package com.ten.tencloud.module.login.contract;


import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;

/**
 * Created by lxq on 2017/11/21.
 */

public class LogoutContract {
    public interface View extends IBaseView {
        void logoutSuccess();

    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {

        void logout();

    }
}
