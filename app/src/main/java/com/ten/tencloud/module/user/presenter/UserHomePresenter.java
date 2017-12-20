package com.ten.tencloud.module.user.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.CompanyBean;
import com.ten.tencloud.bean.User;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.user.contract.UserHomeContract;
import com.ten.tencloud.module.user.model.UserModel;

import java.util.List;

/**
 * Created by lxq on 2017/12/14.
 */

public class UserHomePresenter extends BasePresenter<UserHomeContract.View>
        implements UserHomeContract.Presenter<UserHomeContract.View> {

    @Override
    public void getUserInfo() {
        mSubscriptions.add(UserModel.getInstance()
                .getUserInfo()
                .subscribe(new JesSubscribe<User>(mView) {
                    @Override
                    public void _onSuccess(User user) {
                        AppBaseCache.getInstance().setUserInfo(user);
                        mView.showUserInfo(user);
                    }
                }));
    }

    @Override
    public void getCompanies() {
        mSubscriptions.add(UserModel.getInstance()
                .getCompaniesWithType(UserModel.COMPANIES_TYPE_PASS_AND_CREATE)
                .subscribe(new JesSubscribe<List<CompanyBean>>(mView) {
                    @Override
                    public void _onSuccess(List<CompanyBean> companyBeans) {
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
                }));
    }
}
