package com.ten.tencloud.module.other.model;

import com.ten.tencloud.TenApp;
import com.ten.tencloud.bean.User;
import com.ten.tencloud.model.HttpResultFunc;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lxq on 2018/1/2.
 */

public class QiniuModel {
    private static QiniuModel INSTANCE;

    private QiniuModel() {
    }

    public static synchronized QiniuModel getInstance() {
        if (INSTANCE == null) {
            synchronized (QiniuModel.class) {
                INSTANCE = new QiniuModel();
            }
        }
        return INSTANCE;
    }

    /**
     * 获取上传token
     *
     * @return
     */
    public Observable<User> getUploadToken() {
        return TenApp.getRetrofitClient().getTenUserApi()
                .getUploadToken()
                .map(new HttpResultFunc<User>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
