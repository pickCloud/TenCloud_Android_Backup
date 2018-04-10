package com.ten.tencloud.model.netapi;

import com.ten.tencloud.base.bean.JesResponse;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.bean.AppBrief;
import com.ten.tencloud.bean.ReposBean;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Create by chenxh@10.com on 2018/4/2.
 */
public interface TenAppApi {

    //应用概述信息
    @GET("/api/application/brief")
    Observable<Response<JesResponse<AppBrief>>> getAppBrief();

    //添加应用
    @POST("/api/application/new")
    Observable<Response<JesResponse<Object>>> newApp(@Body RequestBody body);

    //更新应用
    @POST("/api/application/update")
    Observable<Response<JesResponse<Object>>> updateApp(@Body RequestBody body);

    //应用列表，获取所有
    @GET("/api/application")
    Observable<Response<JesResponse<List<AppBean>>>> getAppList();

    //应用列表，分页
    @GET("/api/application")
    Observable<Response<JesResponse<List<AppBean>>>> getAppListByPage(@Query("page") int page, @Query("page_num") int page_num);

    //指定应用
    @GET("/api/application")
    Observable<Response<JesResponse<List<AppBean>>>> getAppById(@Query("id") int id);

    //获取仓库列表
    @POST("/api/repos")
    Observable<Response<JesResponse<List<ReposBean>>>> getReposList(@Query("url") String url);


}
