package com.ten.tencloud.module.login.presenter;

import android.text.TextUtils;

import com.ten.tencloud.R;
import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.User;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.model.JesException;
import com.ten.tencloud.model.SPFHelper;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.login.contract.LoginContract;
import com.ten.tencloud.module.login.model.LoginModel;
import com.ten.tencloud.utils.Utils;

import rx.Subscription;

/**
 * Created by lxq on 2017/11/21.
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter<LoginContract.View> {

    private LoginModel mModel;

    public LoginPresenter() {
        mModel = LoginModel.getInstance();
    }

    @Override
    public void loginByPassword(final String phone, final String password) {
        if (!verifyAccountByPassword(phone, password)) {
            return;
        }
        Subscription mSubscribe = mModel.loginByPassword(phone, password)
                .subscribe(new JesSubscribe<User>(mView) {
                    @Override
                    public void _onSuccess(User result) {
                        AppBaseCache.getInstance().setToken(result.getToken());
                        SPFHelper spfHelper = new SPFHelper(mView.getContext(), "");
                        spfHelper.putString("loginName", phone);
                        mView.loginSuccess();
                    }

                    @Override
                    public void _onError(JesException e) {
                        mView.showMessage(e.getMessage());
                        if (e.getCode() == 10404) {
                            mView.unregistered();
                        }
                    }
                });
        mSubscriptions.add(mSubscribe);
    }

    @Override
    public void loginByCode(final String phone, String code) {
        if (!verifyAccountByCode(phone, code)) {
            return;
        }
        mSubscriptions.add(mModel.loginByCode(phone, code).subscribe(new JesSubscribe<User>(mView) {
            @Override
            public void _onSuccess(User user) {
                AppBaseCache.getInstance().setToken(user.getToken());
                SPFHelper spfHelper = new SPFHelper(mView.getContext(), "");
                spfHelper.putString("loginName", phone);
                mView.loginSuccess();
            }

            @Override
            public void _onError(JesException e) {
                mView.showMessage(e.getMessage());
                if (e.getCode() == 10404) {
                    mView.unregistered();
                }
            }
        }));
    }

    private boolean verifyAccountByCode(String phone, String code) {

        if (TextUtils.isEmpty(phone)) {
            mView.showMessage(R.string.tips_verify_phone_empty);
            return false;
        }
        if (TextUtils.isEmpty(code)) {
            mView.showMessage(R.string.tips_verify_code_empty);
            return false;
        }
        if (!Utils.isMobile(phone)) {
            mView.showMessage(R.string.tips_verify_phone_error);
            return false;
        }
        if (code.length() != 6) {
            mView.showMessage(R.string.tips_verify_code_length);
            return false;
        }

        return true;
    }

    /**
     * 简单验证下账户
     *
     * @param phone
     * @param password
     */
    private boolean verifyAccountByPassword(String phone, String password) {
        if (TextUtils.isEmpty(phone)) {
            mView.showMessage(R.string.tips_verify_phone_empty);
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            mView.showMessage(R.string.tips_verify_password_empty);
            return false;
        }
        if (!Utils.isMobile(phone)) {
            mView.showMessage(R.string.tips_verify_phone_error);
            return false;
        }
        if (password.length() < 6) {
            mView.showMessage(R.string.tips_verify_password_length);
            return false;
        }
        return true;
    }
}
