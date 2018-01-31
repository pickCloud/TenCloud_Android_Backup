package com.ten.tencloud.module.login.contract;


import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.JoinComBean;

/**
 * Created by lxq on 2017/11/21.
 */

public class JoinCom1Contract {
    public interface View extends IBaseView {
        void showInitialize(JoinComBean bean);

        void loginSuccess();

        void joinWaiting();

        void showEmployeeStatus(int status);
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {

        void getJoinInitialize(String code);

        void joinWaiting(String code);

        void getEmployeeStatus();

    }
}
