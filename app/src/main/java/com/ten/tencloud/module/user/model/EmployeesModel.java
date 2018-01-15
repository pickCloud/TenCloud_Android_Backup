package com.ten.tencloud.module.user.model;

import com.ten.tencloud.TenApp;
import com.ten.tencloud.bean.EmployeeBean;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.model.HttpResultFunc;
import com.ten.tencloud.model.netapi.TenUserApi;
import com.ten.tencloud.utils.RetrofitUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lxq on 2017/12/28.
 */

public class EmployeesModel {
    private static EmployeesModel INSTANCE;
    private final TenUserApi mTenUserApi;
    private final Map<String, Object> mArgMap;

    private EmployeesModel() {
        mTenUserApi = TenApp.getRetrofitClient().getTenUserApi();
        mArgMap = new HashMap<>();
    }

    public static synchronized EmployeesModel getInstance() {
        if (INSTANCE == null) {
            synchronized (EmployeesModel.class) {
                INSTANCE = new EmployeesModel();
            }
        }
        return INSTANCE;
    }

    /**
     * 获取员工列表
     *
     * @param cid
     * @return
     */
    public Observable<List<EmployeeBean>> getEmployeesList(int cid) {
        return mTenUserApi.getEmployeesList(cid)
                .map(new HttpResultFunc<List<EmployeeBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static final int STATUS_EMPLOYEE_SEARCH_ALL = -2;
    public static final int STATUS_EMPLOYEE_SEARCH_PASS = Constants.EMPLOYEE_STATUS_CODE_PASS;
    public static final int STATUS_EMPLOYEE_SEARCH_NO_PASS = Constants.EMPLOYEE_STATUS_CODE_NO_PASS;
    public static final int STATUS_EMPLOYEE_SEARCH_CHECKING = Constants.EMPLOYEE_STATUS_CODE_CHECKING;
    public static final int STATUS_EMPLOYEE_SEARCH_CREATE = Constants.EMPLOYEE_STATUS_CODE_CREATE;

    /**
     * 搜索员工
     * employeeName 员工名字，
     * status 审核状态 -2 全部 1 通过 -1 不通过 0 待审核
     *
     * @return
     */
    public Observable<List<EmployeeBean>> searchEmployees(String employeeName, int status) {
        mArgMap.clear();
        mArgMap.put("employee_name", employeeName);
        if (status != STATUS_EMPLOYEE_SEARCH_ALL)
            mArgMap.put("status", status);
        RequestBody requestBody = RetrofitUtils.objectToJsonBody(mArgMap);
        return mTenUserApi.searchEmployees(requestBody)
                .map(new HttpResultFunc<List<EmployeeBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 解除员工
     *
     * @param id
     * @return
     */
    public Observable<Object> dismissEmployee(int id) {
        mArgMap.clear();
        mArgMap.put("id", id);
        RequestBody body = RetrofitUtils.objectToJsonBody(mArgMap);
        return mTenUserApi.dismissEmployee(body)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 解除公司
     *
     * @param cid
     * @return
     */
    public Observable<Object> dismissCompany(int cid) {
        mArgMap.clear();
        mArgMap.put("id", cid);
        RequestBody body = RetrofitUtils.objectToJsonBody(mArgMap);
        return mTenUserApi.dismissCompany(body)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 获取加入条件
     *
     * @return
     */
    public Observable<Map<String, String>> getJoinSetting() {
        int cid = AppBaseCache.getInstance().getSelectCompanyWithLogin().getCid();
        return mTenUserApi.getJoinSetting(cid)
                .map(new HttpResultFunc<Map<String, String>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 设置加入条件
     *
     * @param setting
     * @return
     */
    public Observable<Object> setJoinSetting(String setting) {
        mArgMap.clear();
        mArgMap.put("setting", setting);
        RequestBody body = RetrofitUtils.objectToJsonBody(mArgMap);
        int cid = AppBaseCache.getInstance().getSelectCompanyWithLogin().getCid();
        return mTenUserApi.setJoinSetting(cid, body)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 生成邀请链接
     *
     * @return
     */
    public Observable<Map<String, String>> generateUrl() {
        int cid = AppBaseCache.getInstance().getSelectCompanyWithLogin().getCid();
        return mTenUserApi.generateUrl(cid)
                .map(new HttpResultFunc<Map<String, String>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 允许加入
     *
     * @param id 员工表的ID，不是uid
     * @return
     */
    public Observable<Object> acceptApplication(int id) {
        mArgMap.clear();
        mArgMap.put("id", id);
        RequestBody body = RetrofitUtils.objectToJsonBody(mArgMap);
        return mTenUserApi.acceptApplication(body)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 拒绝加入
     *
     * @param id 员工表的ID，不是uid
     * @return
     */
    public Observable<Object> rejectApplication(int id) {
        mArgMap.clear();
        mArgMap.put("id", id);
        RequestBody body = RetrofitUtils.objectToJsonBody(mArgMap);
        return mTenUserApi.rejectApplication(body)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
