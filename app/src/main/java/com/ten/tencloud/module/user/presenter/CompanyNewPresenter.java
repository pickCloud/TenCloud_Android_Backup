package com.ten.tencloud.module.user.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.constants.GlobalStatusManager;
import com.ten.tencloud.model.JesException;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.user.contract.CompanyNewContract;
import com.ten.tencloud.module.user.model.UserModel;

/**
 * Created by lxq on 2017/12/14.
 */

public class CompanyNewPresenter extends BasePresenter<CompanyNewContract.View>
        implements CompanyNewContract.Presenter<CompanyNewContract.View> {

    @Override
    public void createCompany(String name, String contact, String phone) {
        mSubscriptions.add(UserModel.getInstance().createCompany(name, contact, phone)
                .subscribe(new JesSubscribe<Object>(mView) {
                    @Override
                    public void _onSuccess(Object o) {
                        GlobalStatusManager.getInstance().setUserInfoNeedRefresh(true);
                        mView.showSuccess();
                    }

                    @Override
                    public void _onError(JesException e) {
                        mView.showFailed(e);
                    }
                }));
    }
}
