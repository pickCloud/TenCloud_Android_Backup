package com.ten.tencloud.module.login.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.LoginInfoBean;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.model.SPFHelper;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.login.contract.RegisterContract;
import com.ten.tencloud.module.login.model.LoginModel;

/**
 * Created by lxq on 2017/11/21.
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter<RegisterContract.View> {

    private LoginModel mModel;

    public RegisterPresenter() {
        mModel = LoginModel.getInstance();
    }

    @Override
    public void register(final String mobile, String password, String auth_code) {
        mSubscriptions.add(mModel.register(mobile, password, auth_code).subscribe(new JesSubscribe<LoginInfoBean>(mView) {
            @Override
            public void _onSuccess(LoginInfoBean result) {
                AppBaseCache.getInstance().saveUserInfoWithLogin(result);
                SPFHelper spfHelper = new SPFHelper(mView.getContext(), "");
                spfHelper.putString("loginName", mobile);
                mView.registerSuccess();
            }
        }));
    }
}
