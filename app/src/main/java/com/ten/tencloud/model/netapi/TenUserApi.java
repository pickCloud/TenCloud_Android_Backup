package com.ten.tencloud.model.netapi;


import com.ten.tencloud.base.bean.JesResponse;
import com.ten.tencloud.bean.CompanyBean;
import com.ten.tencloud.bean.EmployeeBean;
import com.ten.tencloud.bean.PermissionTemplate2Bean;
import com.ten.tencloud.bean.PermissionTemplateBean;
import com.ten.tencloud.bean.PermissionTreeNodeBean;
import com.ten.tencloud.bean.User;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
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
    Observable<Response<JesResponse<PermissionTemplate2Bean>>> getUserPermission(@Path("cid") int cid, @Path("uid") int uid);

    @GET("/api/permission/{cid}/user/{uid}/detail/format/0")
    Observable<Response<JesResponse<List<PermissionTreeNodeBean>>>> viewUserPermission(@Path("cid") int cid, @Path("uid") int uid);

    @POST("/api/permission/user/update")
    Observable<Response<JesResponse<Object>>> updateUserPermission(@Body RequestBody body);

    @POST("/api/company/admin/transfer")
    Observable<Response<JesResponse<Object>>> transferAdmin(@Body RequestBody body);

    /**
     * ========================template
     */
    @GET("/api/permission/template/list/{cid}")
    Observable<Response<JesResponse<List<PermissionTemplateBean>>>> getTemplatesByCid(@Path("cid") int cid);

    @POST("/api/permission/template/{ptId}/del")
    Observable<Response<JesResponse<Object>>> delTemplate(@Path("ptId") int ptId, @Body RequestBody body);

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


    @POST("/api/company/employee/search")
    Observable<Response<JesResponse<List<EmployeeBean>>>> searchEmployees(@Body RequestBody body);


    /**
     * 管理员解除员工
     */
    @POST("/api/company/application/dismission")
    Observable<Response<JesResponse<Object>>> dismissEmployee(@Body RequestBody body);

    /**
     * 员工退出公司
     *
     * @param body
     * @return
     */
    @POST("/api/company/employee/dismission")
    Observable<Response<JesResponse<Object>>> dismissCompany(@Body RequestBody body);

    @GET("/api/company/{cid}/entry/setting")
    Observable<Response<JesResponse<Map<String, String>>>> getJoinSetting(@Path("cid") int cid);

    @POST("/api/company/{cid}/entry/setting")
    Observable<Response<JesResponse<Object>>> setJoinSetting(@Path("cid") int cid, @Body RequestBody body);

    @GET("/api/company/{cid}/entry/url")
    Observable<Response<JesResponse<Map<String, String>>>> generateUrl(@Path("cid") int cid);

    /**
     * 允许加入
     *
     * @param body
     * @return
     */
    @POST("/api/company/application/accept")
    Observable<Response<JesResponse<Object>>> acceptApplication(@Body RequestBody body);

    /**
     * 拒绝加入
     *
     * @param body
     * @return
     */
    @POST("/api/company/application/reject")
    Observable<Response<JesResponse<Object>>> rejectApplication(@Body RequestBody body);

    @GET("/api/company/employee/status")
    Observable<Response<JesResponse<Map<String, Integer>>>> getEmployeeStatus(@Query("id") int cid);
}
