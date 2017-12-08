package com.ten.tencloud.module.login.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.User;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.login.contract.PerfectUserContract;
import com.ten.tencloud.module.login.model.LoginModel;

/**
 * Created by lxq on 2017/11/21.
 */

public class PerfectUserPresenter extends BasePresenter<PerfectUserContract.View>
        implements PerfectUserContract.Presenter<PerfectUserContract.View> {

    private LoginModel mModel;

    public PerfectUserPresenter() {
        mModel = LoginModel.getInstance();
    }

    @Override
    public void setPassword(String new_password) {
        mSubscriptions.add(mModel.setPassword(new_password).subscribe(new JesSubscribe<User>(mView) {
            @Override
            public void _onSuccess(User user) {
                mView.setSuccess();
            }
        }));
    }
}
