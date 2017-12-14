package com.ten.tencloud.model.netapi;


import com.ten.tencloud.base.bean.JesResponse;
import com.ten.tencloud.bean.User;

import retrofit2.Response;
import retrofit2.http.GET;
import rx.Observable;

/**
 * 用户模块
 * Created by lxq on 2017/12/14.
 */

public interface TenUserApi {

    @GET("/api/user")
    Observable<Response<JesResponse<User>>> getUserInfo();

}
