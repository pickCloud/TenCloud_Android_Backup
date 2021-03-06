package com.ten.tencloud.module.login.contract;


import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.model.JesException;

/**
 * Created by lxq on 2017/11/21.
 */

public class ForgetContract {
    public interface View extends IBaseView {
        void findSuccess();

        void findFailed(JesException e);
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {

        void findPassword(String mobile, String new_password, String auth_code, String oldPw);

    }
}
