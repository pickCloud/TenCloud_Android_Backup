package com.ten.tencloud.model.netapi;


import com.ten.tencloud.base.bean.JesResponse;
import com.ten.tencloud.bean.MessageBean;

import java.util.List;
import java.util.Map;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 消息模块
 * Created by lxq on 2017/12/14.
 */

public interface TenMsgApi {

    @GET("/api/messages/count")
    Observable<Response<JesResponse<Map<String, Integer>>>> getMsgCount(@Query("status") int status);

    @GET("/api/messages/{status}")
    Observable<Response<JesResponse<List<MessageBean>>>> getMsgListByStatus(@Path("status") String status, @Query("mode") int mode, @Query("page") int page);
}
