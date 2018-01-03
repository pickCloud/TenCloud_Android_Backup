package com.ten.tencloud.model.netapi;


import com.ten.tencloud.base.bean.JesResponse;
import com.ten.tencloud.bean.CompanyBean;
import com.ten.tencloud.bean.EmployeeBean;
import com.ten.tencloud.bean.PermissionTemplateBean;
import com.ten.tencloud.bean.PermissionTreeNodeBean;
import com.ten.tencloud.bean.User;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 用户模块
 * Created by lxq on 2017/12/14.
 */

public interface TenUserApi {

    @GET("/api/user/token")
    Observable<Response<JesResponse<User>>> getUploadToken();

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

    @POST("/api/company/new")
    Observable<Response<JesResponse<CompanyBean>>> createCompany(@Body RequestBody body);

    @GET("/api/company/{id}")
    Observable<Response<JesResponse<List<CompanyBean>>>> getCompanyInfoByCid(@Path("id") int cid);

    @POST("/api/user/update")
    Observable<Response<JesResponse<Object>>> updateUserInfo(@Body RequestBody body);

    @POST("/api/company/update")
    Observable<Response<JesResponse<Object>>> updateCompanyInfo(@Body RequestBody body);

    @POST("/api/user/mobile/reset")
    Observable<Response<JesResponse<Object>>> changePhone(@Body RequestBody body);

    @GET("/api/permission/{cid}/user/{uid}/detail/format/1")
    Observable<Response<JesResponse<Object>>> getUserPermission(@Path("cid") int cid, @Path("uid") int uid);

    /**
     * ========================template
     */
    @GET("/api/permission/template/list/{cid}")
    Observable<Response<JesResponse<List<PermissionTemplateBean>>>> getTemplatesByCid(@Path("cid") int cid);

    @GET("/api/permission/resource/{cid}")
    Observable<Response<JesResponse<List<PermissionTreeNodeBean>>>> getTemplateResource(@Path("cid") int cid);

    @GET("/api/permission/template/{ptId}/format/0")
    Observable<Response<JesResponse<PermissionTreeNodeBean>>> getTemplate(@Path("ptId") int ptId);

    @PUT("/api/permission/template/{pt_id}/update")
    Observable<Response<JesResponse<Object>>> updatePermissionTemplate(@Path("pt_id") int ptId, @Body RequestBody body);

    @POST("/api/permission/template/{pt_id}/rename")
    Observable<Response<JesResponse<Object>>> renameTemplate(@Path("pt_id") int ptId, @Body RequestBody body);

    @POST("/api/permission/template/add")
    Observable<Response<JesResponse<Object>>> addTemplate(@Body RequestBody body);


    /**
     * =======================员工
     */
    @GET("/api/company/{cid}/employees")
    Observable<Response<JesResponse<List<EmployeeBean>>>> getEmployeesList(@Path("cid") int cid);
}
