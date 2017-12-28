package com.ten.tencloud.module.user.model;

import com.ten.tencloud.TenApp;
import com.ten.tencloud.bean.EmployeeBean;
import com.ten.tencloud.model.HttpResultFunc;
import com.ten.tencloud.model.netapi.TenUserApi;

import java.util.List;

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
}
