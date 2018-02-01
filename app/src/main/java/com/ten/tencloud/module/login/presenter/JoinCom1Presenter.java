package com.ten.tencloud.module.login.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.JoinComBean;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.login.contract.JoinCom1Contract;
import com.ten.tencloud.module.login.model.JoinModel;
import com.ten.tencloud.module.user.model.EmployeesModel;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by lxq on 2017/11/21.
 */

public class JoinCom1Presenter extends BasePresenter<JoinCom1Contract.View> implements JoinCom1Contract.Presenter<JoinCom1Contract.View> {

    private JoinModel mModel;

    public JoinCom1Presenter() {
        mModel = JoinModel.getInstance();
    }


    @Override
    public void getJoinInitialize(String code) {
        mSubscriptions.add(mModel.getJoinInitialize(code)
                .subscribe(new JesSubscribe<JoinComBean>(mView) {
                    @Override
                    public void _onSuccess(JoinComBean joinComBean) {
                        mView.showInitialize(joinComBean);
                    }
                }));
    }


    @Override
    public void joinWaiting(String code) {
        mSubscriptions.add(mModel.joinWaiting(code)
                .subscribe(new JesSubscribe<Object>(mView) {
                    @Override
                    public void _onSuccess(Object o) {
                        mView.joinWaiting();
                    }
                }));
    }

    @Override
    public void getEmployeeStatus(int cid) {
        mSubscriptions.add(EmployeesModel.getInstance().getEmployeeStatus(cid)
                .subscribe(new JesSubscribe<Map<String, Integer>>(mView) {
                    @Override
                    public void _onSuccess(Map<String, Integer> stringIntegerMap) {
                        Integer status = stringIntegerMap.get("status");
                        mView.showEmployeeStatus(status);
                    }
                }));
    }

    public void temp() {
        mSubscriptions.add(rx.Observable.just("").delay(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new JesSubscribe<String>(mView) {
                    @Override
                    public void _onSuccess(String s) {
                        mView.loginSuccess();
                    }
                }));
    }
}
