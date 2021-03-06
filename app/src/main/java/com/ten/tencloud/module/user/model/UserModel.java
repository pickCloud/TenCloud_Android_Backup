package com.ten.tencloud.module.user.model;

import com.ten.tencloud.TenApp;
import com.ten.tencloud.bean.CompanyBean;
import com.ten.tencloud.bean.PermissionTemplate2Bean;
import com.ten.tencloud.bean.PermissionTemplateBean;
import com.ten.tencloud.bean.PermissionTreeNodeBean;
import com.ten.tencloud.bean.User;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.model.HttpResultFunc;
import com.ten.tencloud.utils.RetrofitUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lxq on 2017/12/14.
 */

public class UserModel {
    private static UserModel INSTANCE;

    private UserModel() {
    }

    public static synchronized UserModel getInstance() {
        if (INSTANCE == null) {
            synchronized (UserModel.class) {
                INSTANCE = new UserModel();
            }
        }
        return INSTANCE;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public Observable<User> getUserInfo() {
        return TenApp.getRetrofitClient().getTenUserApi()
                .getUserInfo()
                .map(new HttpResultFunc<User>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取公司列表TYPE
     */
    public static final int COMPANIES_TYPE_REJECT = 1;
    public static final int COMPANIES_TYPE_INREVIEW = 2;
    public static final int COMPANIES_TYPE_PASS = 3;
    public static final int COMPANIES_TYPE_CREATE = 4;
    public static final int COMPANIES_TYPE_waiting = 5;
    public static final int COMPANIES_TYPE_PASS_AND_CREATE = 6;
    public static final int COMPANIES_TYPE_ALL = 7;

    /**
     * 获取公司列表
     *
     * @param type -1拒绝
     *             0审核中
     *             1通过
     *             2创始人
     *             3获取通过的，以及作为创始人的公司列表
     *             4获取所有和该用户相关的公司列表
     * @return
     */
    public Observable<List<CompanyBean>> getCompaniesWithType(int type) {
        return TenApp.getRetrofitClient().getTenUserApi()
                .getCompaniesWithType(type)
                .map(new HttpResultFunc<List<CompanyBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 创建新公司
     *
     * @param name
     * @param contact
     * @param mobile
     * @return
     */
    public Observable<CompanyBean> createCompany(String name, String contact, String mobile) {
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("contact", contact);
        map.put("mobile", mobile);
        String json = TenApp.getInstance().getGsonInstance().toJson(map);
        RequestBody body = RetrofitUtils.stringToJsonBody(json);
        return TenApp.getRetrofitClient().getTenUserApi()
                .createCompany(body)
                .map(new HttpResultFunc<CompanyBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取公司详情
     *
     * @param cid
     * @return
     */
    public Observable<List<CompanyBean>> getCompanyInfoByCid(int cid) {
        return TenApp.getRetrofitClient().getTenUserApi()
                .getCompanyInfoByCid(cid)
                .map(new HttpResultFunc<List<CompanyBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 修改用户信息
     *
     * @param map
     * @return
     */
    public Observable<Object> updateUserInfo(Map<String, String> map) {
        String json = TenApp.getInstance().getGsonInstance().toJson(map);
        RequestBody body = RetrofitUtils.stringToJsonBody(json);
        return TenApp.getRetrofitClient().getTenUserApi()
                .updateUserInfo(body)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 修改企业信息
     *
     * @param map
     * @return
     */
    public Observable<Object> updateCompanyInfo(Map<String, Object> map) {
        String json = TenApp.getInstance().getGsonInstance().toJson(map);
        RequestBody body = RetrofitUtils.stringToJsonBody(json);
        return TenApp.getRetrofitClient().getTenUserApi()
                .updateCompanyInfo(body)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<Object> changePhone(String new_mobile, String auth_code, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("new_mobile", new_mobile);
        map.put("auth_code", auth_code);
        map.put("password", password);
        String json = TenApp.getInstance().getGsonInstance().toJson(map);
        RequestBody body = RetrofitUtils.stringToJsonBody(json);
        return TenApp.getRetrofitClient().getTenUserApi()
                .changePhone(body)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 权限模版列表
     *
     * @param cid
     * @return
     */
    public Observable<List<PermissionTemplateBean>> getTemplatesByCid(int cid) {
        return TenApp.getRetrofitClient().getTenUserApi()
                .getTemplatesByCid(cid)
                .map(new HttpResultFunc<List<PermissionTemplateBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 删除模版
     *
     * @param ptId
     * @return
     */
    public Observable<Object> delTemplate(int ptId) {
        Map<String, Object> map = new HashMap<>();
        map.put("cid", AppBaseCache.getInstance().getCid());
        RequestBody requestBody = RetrofitUtils.objectToJsonBody(map);
        return TenApp.getRetrofitClient().getTenUserApi()
                .delTemplate(ptId, requestBody)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取所有权限的模版，不区分是否有权限
     *
     * @param cid
     * @return
     */
    public Observable<List<PermissionTreeNodeBean>> getTemplateResource(int cid) {
        return TenApp.getRetrofitClient().getTenUserApi()
                .getTemplateResource(cid)
                .map(new HttpResultFunc<List<PermissionTreeNodeBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取具体的某个模版拥有的权限
     *
     * @param ptId
     * @return
     */
    public Observable<PermissionTreeNodeBean> getTemplate(int ptId) {
        return TenApp.getRetrofitClient().getTenUserApi()
                .getTemplate(ptId)
                .map(new HttpResultFunc<PermissionTreeNodeBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 根据权限模版
     *
     * @param ptId
     * @return
     */
    public Observable<Object> updatePermissionTemplate(int ptId, PermissionTemplateBean bean) {
        String json = TenApp.getInstance().getGsonInstance().toJson(bean);
        RequestBody body = RetrofitUtils.stringToJsonBody(json);
        return TenApp.getRetrofitClient().getTenUserApi()
                .updatePermissionTemplate(ptId, body)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 重命名
     *
     * @param ptId
     * @param cid
     * @param name
     * @return
     */
    public Observable<Object> renameTemplate(int ptId, int cid, String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("cid", cid);
        map.put("name", name);
        String json = TenApp.getInstance().getGsonInstance().toJson(map);
        RequestBody body = RetrofitUtils.stringToJsonBody(json);
        return TenApp.getRetrofitClient().getTenUserApi()
                .renameTemplate(ptId, body)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 新增模版
     *
     * @param map
     * @return
     */
    public Observable<Object> addTemplate(Map<String, Object> map) {
        String json = TenApp.getInstance().getGsonInstance().toJson(map);
        RequestBody body = RetrofitUtils.stringToJsonBody(json);
        return TenApp.getRetrofitClient().getTenUserApi()
                .addTemplate(body)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 获取用户权限
     *
     * @param cid
     * @return
     */
    public Observable<PermissionTemplate2Bean> getUserPermission(int cid, int uid) {
        return TenApp.getRetrofitClient().getTenUserApi()
                .getUserPermission(cid, uid)
                .map(new HttpResultFunc<PermissionTemplate2Bean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取用户权限
     *
     * @param cid
     * @return
     */
    public Observable<List<PermissionTreeNodeBean>> viewUserPermission(int cid, int uid) {
        return TenApp.getRetrofitClient().getTenUserApi()
                .viewUserPermission(cid, uid)
                .map(new HttpResultFunc<List<PermissionTreeNodeBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 更新用户权限
     *
     * @return
     */
    public Observable<Object> updateUserPermission(PermissionTemplateBean bean) {
        String json = TenApp.getInstance().getGsonInstance().toJson(bean);
        RequestBody body = RetrofitUtils.stringToJsonBody(json);
        return TenApp.getRetrofitClient().getTenUserApi()
                .updateUserPermission(body)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 移交管理员
     *
     * @param uid
     * @return
     */
    public Observable<Object> transferAmdin(int uid) {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("cid", AppBaseCache.getInstance().getSelectCompanyWithLogin().getCid());
        String json = TenApp.getInstance().getGsonInstance().toJson(map);
        RequestBody body = RetrofitUtils.stringToJsonBody(json);
        return TenApp.getRetrofitClient().getTenUserApi()
                .transferAdmin(body)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
