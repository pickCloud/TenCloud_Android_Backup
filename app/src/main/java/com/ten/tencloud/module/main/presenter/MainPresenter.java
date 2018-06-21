package com.ten.tencloud.module.main.presenter;

import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.PermissionTemplate2Bean;
import com.ten.tencloud.bean.User;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.main.contract.MainContract;
import com.ten.tencloud.module.main.model.MsgModel;
import com.ten.tencloud.module.user.model.EmployeesModel;
import com.ten.tencloud.module.user.model.UserModel;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by lxq on 2018/1/2.
 */

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter<MainContract.View> {


    @Override
    public void getMsgCount() {//******************一直循环我擦
//        mSubscriptions.add(Observable.interval(0, 10, TimeUnit.SECONDS)
//                .flatMap(new Func1<Long, Observable<Map<String, Integer>>>() {
//                    @Override
//                    public Observable<Map<String, Integer>> call(Long aLong) {
//                        return MsgModel.getInstance().getMsgCountByStatus(0);
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new JesSubscribe<Map<String, Integer>>(mView) {
//                    @Override
//                    public void _onSuccess(Map<String, Integer> stringIntegerMap) {
//                        mView.showMsgCount(stringIntegerMap.get("num") + "");
//                        Integer permission_changed = stringIntegerMap.get("permission_changed");
//                        Integer admin_changed = stringIntegerMap.get("admin_changed");
//                        if (permission_changed != 0) {
//                            mView.updatePermission();
//                        }
//                        if (admin_changed != 0) {
//                            mView.updateAdminInfo();
//                        }
//                    }
//
//                    @Override
//                    public void onStart() {
//                    }
//                }));

        mSubscriptions.add(MsgModel.getInstance().getMsgCountByStatus(0)
        .subscribe(new JesSubscribe<Map<String, Integer>>(mView) {
            @Override
            public void _onSuccess(Map<String, Integer> stringIntegerMap) {
                mView.showMsgCount(stringIntegerMap.get("num") + "");
                Integer permission_changed = stringIntegerMap.get("permission_changed");
                Integer admin_changed = stringIntegerMap.get("admin_changed");
                if (permission_changed != 0) {
                    mView.updatePermission();
                }
                if (admin_changed != 0) {
                    mView.updateAdminInfo();
                }
            }
        }));
    }

    @Override
    public void getPermission(final int cid) {
        User userInfo = AppBaseCache.getInstance().getUserInfo();
        if (userInfo == null) {
            TenApp.getInstance().jumpLoginActivity();
            return;
        }
        int uid = (int) userInfo.getId();
        mSubscriptions.add(UserModel.getInstance().getUserPermission(cid, uid)
                .subscribe(new JesSubscribe<PermissionTemplate2Bean>(mView) {
                    @Override
                    public void _onSuccess(PermissionTemplate2Bean o) {
                        String s = TenApp.getInstance().getGsonInstance().toJson(o);
                        AppBaseCache.getInstance().setUserPermission(s);
                        mView.updatePermissionSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        getPermission(cid);
                    }
                }));
    }

    @Override
    public void isAdmin() {
        int cid = AppBaseCache.getInstance().getCid();
        mSubscriptions.add(EmployeesModel.getInstance().getEmployeeStatus(cid)
                .subscribe(new JesSubscribe<Map<String, Integer>>(mView) {
                    @Override
                    public void _onSuccess(Map<String, Integer> stringIntegerMap) {
                        Integer is_admin = stringIntegerMap.get("is_admin");
                        AppBaseCache.getInstance().setIsAdmin(is_admin == 1);
                        mView.updatePermissionSuccess();
                    }
                }));
    }
}
