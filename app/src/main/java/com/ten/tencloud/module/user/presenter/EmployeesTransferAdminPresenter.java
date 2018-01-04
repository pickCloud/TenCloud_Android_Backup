package com.ten.tencloud.module.user.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.user.contract.EmployeeTransferAdminContract;
import com.ten.tencloud.module.user.model.UserModel;

/**
 * Created by lxq on 2017/12/14.
 */

public class EmployeesTransferAdminPresenter extends BasePresenter<EmployeeTransferAdminContract.View>
        implements EmployeeTransferAdminContract.Presenter<EmployeeTransferAdminContract.View> {

    @Override
    public void transferAdmin(int uid) {
        mSubscriptions.add(UserModel.getInstance().transferAmdin(uid)
                .subscribe(new JesSubscribe<Object>(mView) {
                    @Override
                    public void _onSuccess(Object o) {
                        mView.transferSuccess();
                    }
                }));
    }
}
