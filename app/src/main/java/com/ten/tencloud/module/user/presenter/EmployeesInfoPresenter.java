package com.ten.tencloud.module.user.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.constants.GlobalStatusManager;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.user.contract.EmployeeInfoContract;
import com.ten.tencloud.module.user.model.EmployeesModel;

/**
 * Created by lxq on 2017/12/14.
 */

public class EmployeesInfoPresenter extends BasePresenter<EmployeeInfoContract.View>
        implements EmployeeInfoContract.Presenter<EmployeeInfoContract.View> {

    @Override
    public void employeeDismissCompany(int cid) {
        mSubscriptions.add(EmployeesModel.getInstance().dismissCompany(cid)
                .subscribe(new JesSubscribe<Object>(mView) {
                    @Override
                    public void _onSuccess(Object o) {
                        GlobalStatusManager.getInstance().setCompanyListNeedRefresh(true);
                        mView.employeeDismissCompanySuccess();
                    }
                }));
    }

    @Override
    public void companyDismissEmployee(int uid) {
        mSubscriptions.add(EmployeesModel.getInstance().dismissEmployee(uid)
                .subscribe(new JesSubscribe<Object>(mView) {
                    @Override
                    public void _onSuccess(Object o) {
                        mView.companyDismissEmployeeSuccess();
                    }
                }));
    }

    @Override
    public void acceptApplication(int id) {
        mSubscriptions.add(EmployeesModel.getInstance().acceptApplication(id)
                .subscribe(new JesSubscribe<Object>(mView) {
                    @Override
                    public void _onSuccess(Object o) {
                        GlobalStatusManager.getInstance().setEmployeeListNeedRefresh(true);
                        mView.acceptApplicationSuccess();
                    }
                }));
    }

    @Override
    public void rejectApplication(int id) {
        mSubscriptions.add(EmployeesModel.getInstance().rejectApplication(id)
                .subscribe(new JesSubscribe<Object>(mView) {
                    @Override
                    public void _onSuccess(Object o) {
                        GlobalStatusManager.getInstance().setEmployeeListNeedRefresh(true);
                        mView.rejectApplicationSuccess();
                    }
                }));
    }
}
