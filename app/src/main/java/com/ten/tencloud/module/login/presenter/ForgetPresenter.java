package com.ten.tencloud.module.login.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.login.contract.ForgetContract;
import com.ten.tencloud.module.login.model.LoginModel;

/**
 * Created by lxq on 2017/11/21.
 */

public class ForgetPresenter extends BasePresenter<ForgetContract.View> implements ForgetContract.Presenter<ForgetContract.View> {

    private LoginModel mModel;

    public ForgetPresenter() {
        mModel = LoginModel.getInstance();
    }


    @Override
    public void findPassword(String mobile, String new_password, String auth_code) {
        mSubscriptions.add(mModel.reset(mobile,new_password,auth_code).subscribe(new JesSubscribe<Object>(mView) {
            @Override
            public void _onSuccess(Object o) {
                mView.findSuccess();
            }
        }));
    }
}
