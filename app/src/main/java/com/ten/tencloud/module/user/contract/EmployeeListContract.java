package com.ten.tencloud.module.user.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.EmployeeBean;

import java.util.List;

/**
 * Created by lxq on 2017/12/14.
 */
public class EmployeeListContract {
    public interface View extends IBaseView {
        void showEmployees(List<EmployeeBean> employees);

        void showEmpty();
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {
        void getEmployees(int cid);
    }
}
