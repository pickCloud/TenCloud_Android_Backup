package com.ten.tencloud.module.user.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.user.contract.SettingChangePhoneContract;
import com.ten.tencloud.module.user.model.UserModel;

/**
 * Created by lxq on 2017/12/14.
 */

public class SettingChangePhonePresenter extends BasePresenter<SettingChangePhoneContract.View>
        implements SettingChangePhoneContract.Presenter<SettingChangePhoneContract.View> {

    @Override
    public void change(String mobile, String password, String auth_code) {
        mSubscriptions.add(UserModel.getInstance().changePhone(mobile, auth_code, password)
                .subscribe(new JesSubscribe<Object>(mView) {
                    @Override
                    public void _onSuccess(Object o) {
                        mView.changeSuccess();
                    }
                }));
    }
}
