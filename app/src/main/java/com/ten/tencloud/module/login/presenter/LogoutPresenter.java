package com.ten.tencloud.module.login.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.login.contract.LogoutContract;
import com.ten.tencloud.module.login.model.LoginModel;

/**
 * Created by lxq on 2017/11/21.
 */

public class LogoutPresenter extends BasePresenter<LogoutContract.View> implements LogoutContract.Presenter<LogoutContract.View> {

    private LoginModel mModel;

    public LogoutPresenter() {
        mModel = LoginModel.getInstance();
    }


    @Override
    public void logout() {
        mSubscriptions.add(mModel.logout().subscribe(new JesSubscribe<Object>(mView) {
            @Override
            public void _onSuccess(Object o) {
                mView.logoutSuccess();
            }
        }));
    }
}
