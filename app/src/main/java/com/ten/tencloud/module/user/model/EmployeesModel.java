package com.ten.tencloud.module.user.model;

import com.ten.tencloud.TenApp;
import com.ten.tencloud.bean.EmployeeBean;
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

    private EmployeesModel() {
        mTenUserApi = TenApp.getRetrofitClient().getTenUserApi();
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

    /**
     * 解除员工
     *
     * @param uid
     * @return
     */
    public Observable<Object> dismissEmployee(int uid) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", uid);
        String json = TenApp.getInstance().getGsonInstance().toJson(map);
        RequestBody body = RetrofitUtils.stringToJsonBody(json);
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
        Map<String, Object> map = new HashMap<>();
        map.put("id", cid);
        String json = TenApp.getInstance().getGsonInstance().toJson(map);
        RequestBody body = RetrofitUtils.stringToJsonBody(json);
        return mTenUserApi.dismissCompany(body)
                .map(new HttpResultFunc<Object>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<Map<String, String>> getJoinSetting() {
        int cid = AppBaseCache.getInstance().getSelectCompanyWithLogin().getCid();
        return mTenUserApi.getJoinSetting(cid)
                .map(new HttpResultFunc<Map<String, String>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Object> setJoinSetting(String setting) {
        Map<String, Object> map = new HashMap<>();
        map.put("setting", setting);
        String json = TenApp.getInstance().getGsonInstance().toJson(map);
        RequestBody body = RetrofitUtils.stringToJsonBody(json);
        int cid = AppBaseCache.getInstance().getSelectCompanyWithLogin().getCid();
        return mTenUserApi.setJoinSetting(cid, body)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
