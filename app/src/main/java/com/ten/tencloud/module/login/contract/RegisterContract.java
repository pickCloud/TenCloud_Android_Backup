package com.ten.tencloud.module.login.contract;


import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;

/**
 * Created by lxq on 2017/11/21.
 */

public class RegisterContract {
    public interface View extends IBaseView {
        //登陆成功
        void registerSuccess();

    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {

        void register(String mobile, String new_password, String auth_code);

    }
}
