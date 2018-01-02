package com.ten.tencloud.module.user.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.CompanyBean;
import com.ten.tencloud.bean.EmployeeBean;

import java.util.List;

/**
 * Created by lxq on 2017/12/14.
 */
public class UserHomeContract {
    public interface View extends IBaseView {

        void showCompanies(List<CompanyBean> companies);

        void showCompanyInfo(CompanyBean companyInfo);

        void showEmployees(List<EmployeeBean> employees);
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {

        void getCompanies();

        void getCompanyByCid(int cid);

        void getEmployees(int cid);
    }
}
