package com.ten.tencloud.model.netapi;

import com.ten.tencloud.base.bean.JesResponse;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.bean.AppBrief;
import com.ten.tencloud.bean.DeploymentBean;
import com.ten.tencloud.bean.DeploymentInfoBean;
import com.ten.tencloud.bean.ImageBean;
import com.ten.tencloud.bean.LabelBean;
import com.ten.tencloud.bean.ReposBean;
import com.ten.tencloud.bean.ServiceBriefBean;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

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

    //应用信息
    @GET("/api/application/brief")
    Observable<Response<JesResponse<AppBrief>>> getAppBrief();

    //应用信息
    @GET("/api/application/brief")
    Observable<Response<JesResponse<AppBrief>>> getAppBrief(@Query("id") Integer id, @Query("master_app") Integer master_app,
                                                            @Query("page") Integer page, @Query("page_num") Integer page_num, @Query("label") Integer label);

    //添加应用
    @POST("/api/application/new")
    Observable<Response<JesResponse<Object>>> newApp(@Body RequestBody body);

    //更新应用
    @POST("/api/application/update")
    Observable<Response<JesResponse<Object>>> updateApp(@Body RequestBody body);

    //应用列表，获取所有
    @GET("/api/application/brief")
    Observable<Response<JesResponse<List<AppBean>>>> getAppList();

    //应用列表，分页
    @GET("/api/application/brief")
    Observable<Response<JesResponse<List<AppBean>>>> getAppListByPage(@Query("page") int page, @Query("page_num") int page_num, @Query("label") Integer label);

    //指定应用
    @GET("/api/application/brief")
    Observable<Response<JesResponse<List<AppBean>>>> getAppById(@Query("id") int id);

    //服务概况
    @GET("/api/service/brief")
    Observable<Response<JesResponse<List<ServiceBriefBean>>>> getAppServiceBriefById(@Query("app_id") int app_id);

    //获取所有子应用
    @GET("/api/sub_application/brief")
    Observable<Response<JesResponse<List<AppBean>>>> getAppSubApplicationList(@Query("master_app") int master_app);

    //获取指定子应用
    @GET("/api/sub_application/brief")
    Observable<Response<JesResponse<List<AppBean>>>> getAppSubApplicationById(@Query("master_app") int master_app, @Query("id") int id);

    //获取仓库列表
    @POST("/api/repos")
    Observable<Response<JesResponse<List<ReposBean>>>> getReposList(@Query("url") String url);

    //获取分支
    @POST("/api/repos/branches")
    Observable<Response<JesResponse<List<Map<String, String>>>>> getReposBranches(@Query("repos_name") String repos_name, @Query("url") String url);

    //新建标签
    @POST("/api/label/new")
    Observable<Response<JesResponse<LabelBean>>> newLabel(@Query("name") String name, @Query("type") int type);

    //标签列表
    @GET("/api/label/list")
    Observable<Response<JesResponse<TreeSet<LabelBean>>>> getLabelList(@Query("type") int type);

    //镜像部署列表
    @GET("/api/image")
    Observable<Response<JesResponse<List<ImageBean>>>> getAppImages(@Query("app_id") String appId);

    //检查部署名称
    @GET("/api/deployment/check_name")
    Observable<Response<JesResponse<Object>>> checkDeployName(@Query("name") String name, @Query("app_id") int appId);

    //生成部署YAML
    @POST("/api/deployment/generate")
    Observable<Response<JesResponse<String>>> generateDeployYAML(@Body RequestBody body);

    //生成ServiceYAML
    @POST("/api/service/generate")
    Observable<Response<JesResponse<String>>> generateServiceYAML(@Body RequestBody body);

    @GET("/api/deployment/list")
    Observable<Response<JesResponse<List<DeploymentBean>>>> getDeployList(@Query("app_id") Integer id, @Query("status") Integer status, @Query("deployment_id") Integer deployment_id,
                                                                          @Query("show_yaml") Integer show_yaml, @Query("show_log") Integer show_log, @Query("page") int page, @Query("page_num") int pageNum);
    //部署的Pod信息
    @GET("/api/deployment/pods")
    Observable<Response<JesResponse<List<DeploymentInfoBean>>>> deploymentPods(@Query("deployment_id") int deployment_id, @Query("show_verbose") Integer show_verbose);

    //部署的ReplicasSet信息
    @GET("/api/deployment/replicas")
    Observable<Response<JesResponse<List<DeploymentInfoBean>>>> deploymentReplicas(@Query("deployment_id") int deployment_id, @Query("show_verbose") Integer show_verbose);

}

