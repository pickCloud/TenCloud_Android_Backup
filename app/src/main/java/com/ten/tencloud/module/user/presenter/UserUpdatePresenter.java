package com.ten.tencloud.module.user.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.constants.GlobalStatusManager;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.user.contract.UserUpdateContract;
import com.ten.tencloud.module.user.model.UserModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lxq on 2017/12/14.
 */

public class UserUpdatePresenter extends BasePresenter<UserUpdateContract.View>
        implements UserUpdateContract.Presenter<UserUpdateContract.View> {

    @Override
    public void updateUserInfo(String key, String value) {
        Map<String, String> map = new HashMap<>();
        map.put(key, value);
        mSubscriptions.add(UserModel.getInstance().updateUserInfo(map)
                .subscribe(new JesSubscribe<Object>(mView) {
                    @Override
                    public void _onSuccess(Object o) {
                        GlobalStatusManager.getInstance().setUserInfoNeedRefresh(true);
                        mView.updateSuccess();
                    }
                }));
    }

    @Override
    public void updateCompanyInfo(int cid, String name, String contact, String mobile) {
        Map<String, Object> map = new HashMap<>();
        map.put("cid", cid);
        map.put("name", name);
        map.put("contact", contact);
        map.put("mobile", mobile);

        mSubscriptions.add(UserModel.getInstance().updateCompanyInfo(map)
                .subscribe(new JesSubscribe<Object>(mView) {
                    @Override
                    public void _onSuccess(Object o) {
                        GlobalStatusManager.getInstance().setUserInfoNeedRefresh(true);
                        mView.updateSuccess();
                    }
                }));
    }
}
