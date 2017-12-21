package com.ten.tencloud.module.user.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.CompanyBean;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.user.contract.CompanyListContract;
import com.ten.tencloud.module.user.model.UserModel;

import java.util.List;

/**
 * Created by lxq on 2017/12/14.
 */

public class CompanyListPresenter extends BasePresenter<CompanyListContract.View>
        implements CompanyListContract.Presenter<CompanyListContract.View> {

    @Override
    public void getCompanies() {
        mSubscriptions.add(UserModel.getInstance()
                .getCompaniesWithType(UserModel.COMPANIES_TYPE_ALL)
                .subscribe(new JesSubscribe<List<CompanyBean>>(mView) {
                    @Override
                    public void _onSuccess(List<CompanyBean> companyBeans) {
                        if (companyBeans == null || companyBeans.size() == 0) {
                            mView.showMessage("暂无公司信息");
                            return;
                        }
                        mView.showCompanies(companyBeans);
                    }
                }));
    }
}
