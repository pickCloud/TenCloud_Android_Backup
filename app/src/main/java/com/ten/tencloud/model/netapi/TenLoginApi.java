package com.ten.tencloud.model.netapi;

import com.ten.tencloud.base.bean.JesResponse;
import com.ten.tencloud.bean.LoginInfoBean;
import com.ten.tencloud.bean.User;

import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 通用接口
 * Created by lxq on 2017/11/21.
 */

public interface TenLoginApi {

    @POST("/api/user/login/password")
    Observable<Response<JesResponse<LoginInfoBean>>> loginByPassword(@Body RequestBody body);

    @POST("/api/user/login")
    Observable<Response<JesResponse<LoginInfoBean>>> loginByCode(@Body RequestBody body);

    @POST("/api/user/sms")
    Observable<Response<JesResponse<Object>>> sendSms(@Body RequestBody body);

    @POST("/api/user/password/reset")
    Observable<Response<JesResponse<Object>>> reset(@Body RequestBody body);

    @POST("/api/user/register")
    Observable<Response<JesResponse<LoginInfoBean>>> register(@Body RequestBody body);

    @POST("/api/user/password/set")
    Observable<Response<JesResponse<User>>> setPassword(@Body RequestBody body);


}
