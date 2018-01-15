package com.ten.tencloud.module.login.model;


import android.text.TextUtils;

import com.ten.tencloud.TenApp;
import com.ten.tencloud.bean.JoinComBean;
import com.ten.tencloud.model.HttpResultFunc;
import com.ten.tencloud.utils.RetrofitUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 加入企业
 * Created by lxq on 2017/11/21.
 */

public class JoinModel {

    private static JoinModel INSTANCE;

    private JoinModel() {
    }

    public static synchronized JoinModel getInstance() {
        if (INSTANCE == null) {
            synchronized (JoinModel.class) {
                INSTANCE = new JoinModel();
            }
        }
        return INSTANCE;
    }


    /**
     * 获取加入企业的信息
     *
     * @param code
     * @return
     */
    public Observable<JoinComBean> getJoinInitialize(String code) {
        return TenApp.getRetrofitClient().getTenLoginApi()
                .getJoinInitialize(code)
                .map(new HttpResultFunc<JoinComBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 触发加入企业待加入状态
     *
     * @param code
     * @return
     */
    public Observable<Object> joinWaiting(String code) {
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        RequestBody requestBody = RetrofitUtils.objectToJsonBody(map);
        return TenApp.getRetrofitClient().getTenLoginApi()
                .joinWaiting(requestBody)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 申请加入企业
     *
     * @param code
     * @param mobile
     * @param name
     * @param id_card
     * @return
     */
    public Observable<Object> joinCompany(String code, String mobile, String name, String id_card) {
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        map.put("mobile", mobile);
        if (!TextUtils.isEmpty(name)) {
            map.put("name", name);
        }
        if (!TextUtils.isEmpty(id_card)) {
            map.put("id_card", id_card);
        }
        RequestBody requestBody = RetrofitUtils.objectToJsonBody(map);
        return TenApp.getRetrofitClient().getTenLoginApi()
                .joinCompany(requestBody)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
