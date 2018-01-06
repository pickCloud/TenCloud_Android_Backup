package com.ten.tencloud.module.user.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.EmployeeBean;
import com.ten.tencloud.constants.GlobalStatusManager;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.user.contract.EmployeeListContract;
import com.ten.tencloud.module.user.model.EmployeesModel;

import java.util.List;

/**
 * Created by lxq on 2017/12/14.
 */

public class EmployeesListPresenter extends BasePresenter<EmployeeListContract.View>
        implements EmployeeListContract.Presenter<EmployeeListContract.View> {

    @Override
    public void getEmployees(String key, int status) {
        mSubscriptions.add(EmployeesModel.getInstance().searchEmployees(key, status)
                .subscribe(new JesSubscribe<List<EmployeeBean>>(mView) {
                    @Override
                    public void _onSuccess(List<EmployeeBean> employeeBeans) {
                        GlobalStatusManager.getInstance().setEmployeeListNeedRefresh(false);
                        if (employeeBeans == null || employeeBeans.size() == 0) {
                            mView.showEmpty();
                        } else {
                            mView.showEmployees(employeeBeans);
                        }
                    }
                }));
    }
}
