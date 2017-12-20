package com.ten.tencloud.module.login.model;


import android.text.TextUtils;

import com.ten.tencloud.TenApp;
import com.ten.tencloud.bean.LoginInfoBean;
import com.ten.tencloud.bean.SendSMSBean;
import com.ten.tencloud.bean.User;
import com.ten.tencloud.model.HttpResultFunc;
import com.ten.tencloud.utils.RetrofitUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lxq on 2017/11/21.
 */

public class LoginModel {

    private static LoginModel INSTANCE;

    private LoginModel() {
    }

    public static synchronized LoginModel getInstance() {
        if (INSTANCE == null) {
            synchronized (LoginModel.class) {
                INSTANCE = new LoginModel();
            }
        }
        return INSTANCE;
    }

    /**
     * 密码登录
     *
     * @param userName
     * @param password
     * @return
     */
    public Observable<LoginInfoBean> loginByPassword(String userName, String password) {
        User user = new User(userName, password);
        RequestBody body = RetrofitUtils.stringToJsonBody(TenApp.getInstance().getGsonInstance().toJson(user));
        return TenApp.getRetrofitClient().getTenLoginApi().loginByPassword(body)
                .map(new HttpResultFunc<LoginInfoBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 验证码登录
     *
     * @param phone
     * @param code
     * @return
     */
    public Observable<LoginInfoBean> loginByCode(String phone, String code) {
        Map<String, String> map = new HashMap<>();
        map.put("mobile", phone);
        map.put("auth_code", code);
        RequestBody body = RetrofitUtils.stringToJsonBody(TenApp.getInstance().getGsonInstance().toJson(map));
        return TenApp.getRetrofitClient().getTenLoginApi().loginByCode(body)
                .map(new HttpResultFunc<LoginInfoBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 发送验证码
     *
     * @param smsBean
     * @return
     */
    public Observable<Object> sendSMS(SendSMSBean smsBean) {
        RequestBody body = RetrofitUtils.stringToJsonBody(TenApp.getInstance().getGsonInstance().toJson(smsBean));
        return TenApp.getRetrofitClient().getTenLoginApi().sendSms(body)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 重置密码
     *
     * @param mobile
     * @param new_password
     * @param auth_code
     * @return
     */
    public Observable<Object> reset(String mobile, String new_password, String auth_code, String old_password) {
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("new_password", new_password);
        map.put("auth_code", auth_code);
        if (!TextUtils.isEmpty(old_password)) {
            map.put("old_password", old_password);
        }
        RequestBody body = RetrofitUtils.stringToJsonBody(TenApp.getInstance().getGsonInstance().toJson(map));
        return TenApp.getRetrofitClient().getTenLoginApi().reset(body)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 注册
     *
     * @param mobile
     * @param password
     * @param auth_code
     * @return
     */
    public Observable<LoginInfoBean> register(String mobile, String password, String auth_code) {
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("password", password);
        map.put("auth_code", auth_code);
        RequestBody body = RetrofitUtils.stringToJsonBody(TenApp.getInstance().getGsonInstance().toJson(map));
        return TenApp.getRetrofitClient().getTenLoginApi().register(body)
                .map(new HttpResultFunc<LoginInfoBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 设置密码
     *
     * @param password
     * @return
     */
    public Observable<User> setPassword(String password) {
        Map<String, String> map = new HashMap<>();
        map.put("password", password);
        RequestBody body = RetrofitUtils.stringToJsonBody(TenApp.getInstance().getGsonInstance().toJson(map));
        return TenApp.getRetrofitClient().getTenLoginApi().setPassword(body)
                .map(new HttpResultFunc<User>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
