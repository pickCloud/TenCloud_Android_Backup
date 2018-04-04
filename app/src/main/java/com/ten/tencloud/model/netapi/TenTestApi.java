package com.ten.tencloud.model.netapi;

import com.ten.tencloud.base.bean.JesResponse;

import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by lxq on 2017/12/18.
 */

public interface TenTestApi {
    @GET("/api/tmp/user/sms/count/{count}")
    Observable<Response<JesResponse<Object>>> setSMSCount(@Path("count") String count);

    @GET("/api/tmp/user/delete")
    Observable<Response<JesResponse<Object>>> delUser();

    @DELETE("/api/server/clouds/credentials")
    Observable<Response<JesResponse<Object>>> delCloudCredentials();
}
