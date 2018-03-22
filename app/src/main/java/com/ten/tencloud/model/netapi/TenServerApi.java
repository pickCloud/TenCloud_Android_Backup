package com.ten.tencloud.model.netapi;

import com.ten.tencloud.base.bean.JesResponse;
import com.ten.tencloud.bean.ClusterInfoBean;
import com.ten.tencloud.bean.ProviderBean;
import com.ten.tencloud.bean.ServerBean;
import com.ten.tencloud.bean.ServerDetailBean;
import com.ten.tencloud.bean.ServerHeatBean;
import com.ten.tencloud.bean.ServerHistoryBean;
import com.ten.tencloud.bean.ServerLogBean;
import com.ten.tencloud.bean.ServerMonitorBean;
import com.ten.tencloud.bean.ServerSystemLoadBean;
import com.ten.tencloud.bean.ServerThresholdBean;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 服务器模块
 * Created by lxq on 2017/11/28.
 */

public interface TenServerApi {

    @GET("/api/cluster/{id}")
    Observable<Response<JesResponse<ClusterInfoBean>>> getServerList(@Path("id") int id);

    @GET("/api/cluster/warn/{id}")
    Observable<Response<JesResponse<List<ServerBean>>>> getWarnServerList(@Path("id") int id);

    @POST("/api/cluster/search")
    Observable<Response<JesResponse<List<ServerBean>>>> searchServer(@Body RequestBody body);

    @GET("/api/cluster/{clusterId}/providers")
    Observable<Response<JesResponse<List<ProviderBean>>>> getProvidersByCluster(@Path("clusterId") String clusterId);

    @GET("/api/server/{id}")
    Observable<Response<JesResponse<ServerDetailBean>>> getServerDetail(@Path("id") String id);

    @GET("/api/server/{id}/systemload")
    Observable<Response<JesResponse<ServerSystemLoadBean>>> getServerSystemLoad(@Path("id") String id);

    @POST("/api/server/del")
    Observable<Response<JesResponse<Object>>> delServer(@Body RequestBody body);

    @GET("/api/server/reboot/{id}")
    Observable<Response<JesResponse<Object>>> rebootServer(@Path("id") String id);

    @GET("/api/server/start/{id}")
    Observable<Response<JesResponse<Object>>> startServer(@Path("id") String id);

    @GET("/api/server/stop/{id}")
    Observable<Response<JesResponse<Object>>> stopServer(@Path("id") String id);

    @GET("/api/server/{id}/status")
    Observable<Response<JesResponse<String>>> queryServerState(@Path("id") String id);

    @GET("/api/server/containers/{id}")
    Observable<Response<JesResponse<List<List<String>>>>> getContaninersByServer(@Path("id") String id);

    @POST("/api/log/operation")
    Observable<Response<JesResponse<List<ServerLogBean.LogInfo>>>> getServerLog(@Body RequestBody body);

    @POST("/api/server/performance")
    Observable<Response<JesResponse<ServerMonitorBean>>> getServerPerformance(@Body RequestBody body);

    @POST("/api/server/performance")
    Observable<Response<JesResponse<List<ServerHistoryBean>>>> getServerHistory(@Body RequestBody body);

    @POST("/api/server/update")
    Observable<Response<JesResponse<Object>>> changeServerInfo(@Body RequestBody body);

    @GET("/api/cluster/summary")
    Observable<Response<JesResponse<Map<String, Integer>>>> summary();

    //阈值
    @GET("/api/server/threshold")
    Observable<Response<JesResponse<ServerThresholdBean>>> getThreshold();

    @GET("/api/server/monitor")
    Observable<Response<JesResponse<List<ServerHeatBean>>>> getServerMonitor();
}
