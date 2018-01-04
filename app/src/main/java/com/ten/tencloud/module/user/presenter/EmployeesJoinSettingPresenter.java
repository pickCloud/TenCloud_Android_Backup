package com.ten.tencloud.module.user.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.user.contract.EmployeeJoinSettingContract;
import com.ten.tencloud.module.user.model.EmployeesModel;

import java.util.Map;

/**
 * Created by lxq on 2017/12/14.
 */

public class EmployeesJoinSettingPresenter extends BasePresenter<EmployeeJoinSettingContract.View>
        implements EmployeeJoinSettingContract.Presenter<EmployeeJoinSettingContract.View> {

    @Override
    public void setJoinSetting(String setting) {
        mSubscriptions.add(EmployeesModel.getInstance().setJoinSetting(setting)
                .subscribe(new JesSubscribe<Object>(mView) {
                    @Override
                    public void _onSuccess(Object o) {
                        mView.setJoinSettingSuccess();
                    }
                }));
    }

    @Override
    public void getJoinSetting() {
        mSubscriptions.add(EmployeesModel.getInstance().getJoinSetting()
                .subscribe(new JesSubscribe<Map<String, String>>(mView) {
                    @Override
                    public void _onSuccess(Map<String, String> map) {
                        mView.showJoinSetting(map.get("setting"));
                    }
                }));
    }
}
