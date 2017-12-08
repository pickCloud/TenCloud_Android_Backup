package com.ten.tencloud.module.login.contract;


import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;

/**
 * Created by lxq on 2017/11/21.
 */

public class LoginContract {
    public interface View extends IBaseView {
        //登陆成功
        void loginSuccess();

        void unregistered();

    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {
        void loginByPassword(String phone, String password);

        void loginByCode(String phone, String code);

    }
}
