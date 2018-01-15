package com.ten.tencloud.model.netapi;

import com.ten.tencloud.base.bean.JesResponse;
import com.ten.tencloud.bean.JoinComBean;
import com.ten.tencloud.bean.LoginInfoBean;
import com.ten.tencloud.bean.User;

import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
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

    /**
     * 员工加入企业
     */
    @GET("/api/company/application")
    Observable<Response<JesResponse<JoinComBean>>> getJoinInitialize(@Query("code") String code);

    /**
     * 提交申请
     *
     * @param body
     * @return
     */
    @POST("/api/company/application")
    Observable<Response<JesResponse<Object>>> joinCompany(@Body RequestBody body);

    @POST("/api/company/application/waiting")
    Observable<Response<JesResponse<Object>>> joinWaiting(@Body RequestBody body);


}
