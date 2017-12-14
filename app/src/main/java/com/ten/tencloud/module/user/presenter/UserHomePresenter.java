package com.ten.tencloud.module.user.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.User;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.user.contract.UserHomeContract;
import com.ten.tencloud.module.user.model.UserModel;

/**
 * Created by lxq on 2017/12/14.
 */

public class UserHomePresenter extends BasePresenter<UserHomeContract.View>
        implements UserHomeContract.Presenter<UserHomeContract.View> {

    @Override
    public void getUserInfo() {
        mSubscriptions.add(UserModel.getInstance().getUserInfo()
                .subscribe(new JesSubscribe<User>(mView) {
                    @Override
                    public void _onSuccess(User user) {
                        AppBaseCache.getInstance().setUser(user);
                        mView.showUserInfo(user);
                    }
                }));
    }
}
