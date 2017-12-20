package com.ten.tencloud.module.user.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.CompanyBean;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.user.contract.CompanyInfoContract;
import com.ten.tencloud.module.user.model.UserModel;

import java.util.List;

/**
 * Created by lxq on 2017/12/14.
 */

public class CompanyInfoPresenter extends BasePresenter<CompanyInfoContract.View>
        implements CompanyInfoContract.Presenter<CompanyInfoContract.View> {
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
