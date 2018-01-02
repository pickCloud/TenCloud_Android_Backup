package com.ten.tencloud.module.user.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.User;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.user.contract.UserInfoContract;
import com.ten.tencloud.module.user.model.UserModel;

/**
 * Created by lxq on 2017/12/14.
 */

public class UserInfoPresenter extends BasePresenter<UserInfoContract.View>
        implements UserInfoContract.Presenter<UserInfoContract.View> {

    @Override
    public void getUserInfo() {
        mSubscriptions.add(UserModel.getInstance()
                .getUserInfo()
                .subscribe(new JesSubscribe<User>(mView) {
                    @Override
                    public void _onSuccess(User user) {
                        AppBaseCache.getInstance().setUserInfo(user);
                        mView.showUserInfo(user);
                    }
                }));
    }
}
