package com.ten.tencloud.module.user.presenter;

import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.CompanyBean;
import com.ten.tencloud.bean.EmployeeBean;
import com.ten.tencloud.bean.PermissionTemplate2Bean;
import com.ten.tencloud.bean.User;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.constants.GlobalStatusManager;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.model.JesException;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.user.contract.UserHomeContract;
import com.ten.tencloud.module.user.model.EmployeesModel;
import com.ten.tencloud.module.user.model.UserModel;

import java.util.List;

/**
 * Created by lxq on 2017/12/14.
 */

public class UserHomePresenter extends BasePresenter<UserHomeContract.View>
        implements UserHomeContract.Presenter<UserHomeContract.View> {

    @Override
    public void getCompanies() {
        mSubscriptions.add(UserModel.getInstance()
                .getCompaniesWithType(UserModel.COMPANIES_TYPE_ALL)
                .subscribe(new JesSubscribe<List<CompanyBean>>(mView) {
                    @Override
                    public void _onSuccess(List<CompanyBean> companyBeans) {
                        GlobalStatusManager.getInstance().setCompanyListNeedRefresh(false);
                        if (companyBeans == null || companyBeans.size() == 0) {
                            mView.showMessage("暂无公司信息");
                        }
                        mView.showCompanies(companyBeans);
                    }
                }));
    }

    @Override
    public void getCompanyByCid(int cid) {
        mSubscriptions.add(UserModel.getInstance().getCompanyInfoByCid(cid)
                .subscribe(new JesSubscribe<List<CompanyBean>>(mView) {
                    @Override
                    public void _onSuccess(List<CompanyBean> companyBean) {
                        if (companyBean != null && companyBean.size() > 0) {
                            mView.showCompanyInfo(companyBean.get(0));
                        }
                    }

                    @Override
                    public void _onError(JesException e) {
                        mView.showOwnerInfo();
                    }
                }));
    }

    @Override
    public void getEmployees(int cid) {
        mSubscriptions.add(EmployeesModel.getInstance().searchEmployees("", EmployeesModel.STATUS_EMPLOYEE_SEARCH_ALL)
                .subscribe(new JesSubscribe<List<EmployeeBean>>(mView) {
                    @Override
                    public void _onSuccess(List<EmployeeBean> employeeBeans) {
                        mView.showEmployees(employeeBeans);
                    }
                }));
    }

    int tryCount = 0;

    @Override
    public void getPermission(final int cid) {
        AppBaseCache.getInstance().setUserPermission("");//清空
        if (cid == 0) {
            return;
        }
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
                        mView.showPermissionSuccess();
                    }

                    @Override
                    public void _onError(JesException e) {
                        super._onError(e);
                        if (e.getCode() != Constants.NET_CODE_NOT_EMPLOYEE && tryCount < 3) {//非公司员工
                            tryCount++;
                            getPermission(cid);
                        }
                    }
                }));
    }
}
