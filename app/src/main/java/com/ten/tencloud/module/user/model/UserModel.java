package com.ten.tencloud.module.user.model;

import com.ten.tencloud.TenApp;
import com.ten.tencloud.bean.User;
import com.ten.tencloud.model.HttpResultFunc;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lxq on 2017/12/14.
 */

public class UserModel {
    private static UserModel INSTANCE;

    private UserModel() {
    }

    public static synchronized UserModel getInstance() {
        if (INSTANCE == null) {
            synchronized (UserModel.class) {
                INSTANCE = new UserModel();
            }
        }
        return INSTANCE;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public Observable<User> getUserInfo() {
        return TenApp.getRetrofitClient().getTenUserApi()
                .getUserInfo()
                .map(new HttpResultFunc<User>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
