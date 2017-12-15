package com.ten.tencloud.model.netapi;


import com.ten.tencloud.base.bean.JesResponse;
import com.ten.tencloud.bean.CompanyBean;
import com.ten.tencloud.bean.User;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 用户模块
 * Created by lxq on 2017/12/14.
 */

public interface TenUserApi {

    @GET("/api/user")
    Observable<Response<JesResponse<User>>> getUserInfo();

    /**
     * 获取公司列表
     *
     * @param type -1拒绝, 0审核中, 1通过, 2创始人, 3获取通过的，以及作为创始人的公司列表, 4获取所有和该用户相关的公司列表
     * @return
     */
    @GET("/api/companies/list/{type}")
    Observable<Response<JesResponse<List<CompanyBean>>>> getCompaniesWithType(@Path("type") int type);

    @POST("/api/user/update")
    Observable<Response<JesResponse<Object>>> updateUserInfo(@Body RequestBody body);
}
