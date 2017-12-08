package com.ten.tencloud.module.login.contract;


import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;

/**
 * Created by lxq on 2017/11/21.
 */

public class ForgetContract {
    public interface View extends IBaseView {
        void findSuccess();
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {

        void findPassword(String mobile, String new_password, String auth_code);

    }
}
