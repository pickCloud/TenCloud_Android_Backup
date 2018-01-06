package com.ten.tencloud.module.user.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.user.contract.EmployeeInviteContract;
import com.ten.tencloud.module.user.model.EmployeesModel;

import java.util.Map;

/**
 * Created by lxq on 2017/12/14.
 */

public class EmployeesInvitePresenter extends BasePresenter<EmployeeInviteContract.View>
        implements EmployeeInviteContract.Presenter<EmployeeInviteContract.View> {

    @Override
    public void generateUrl() {
        mSubscriptions.add(EmployeesModel.getInstance().generateUrl()
                .subscribe(new JesSubscribe<Map<String, String>>(mView) {
                    @Override
                    public void _onSuccess(Map<String, String> map) {
                        mView.showUrl(map.get("url"));
                    }
                }));
    }
}
