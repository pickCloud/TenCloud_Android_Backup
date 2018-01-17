package com.ten.tencloud.module.login.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.JoinComBean;
import com.ten.tencloud.bean.User;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.login.contract.JoinCom2Contract;
import com.ten.tencloud.module.login.model.JoinModel;
import com.ten.tencloud.module.login.model.LoginModel;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;

/**
 * Created by lxq on 2017/11/21.
 */

public class JoinCom2Presenter extends BasePresenter<JoinCom2Contract.View>
        implements JoinCom2Contract.Presenter<JoinCom2Contract.View> {
    @Override
    public void joinCompany(boolean isNeedInfo, String newPassword, String code, String mobile, String name, String id_card) {
        if (isNeedInfo) {
            mSubscriptions.add(Observable.zip(LoginModel.getInstance().setPassword(newPassword),
                    JoinModel.getInstance().joinCompany(code, mobile, name, id_card),
                    new Func2<User, Object, Boolean>() {
                        @Override
                        public Boolean call(User user, Object o) {
                            return true;
                        }
                    }).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new JesSubscribe<Boolean>(mView) {
                        @Override
                        public void _onSuccess(Boolean aBoolean) {
                            mView.success();
                        }
                    }));
        } else {
            mSubscriptions.add(JoinModel.getInstance().joinCompany(code, mobile, name, id_card)
                    .subscribe(new JesSubscribe<Object>(mView) {
                        @Override
                        public void _onSuccess(Object o) {
                            mView.success();
                        }
                    }));
        }
    }

    @Override
    public void getJoinInitialize(String code) {
        mSubscriptions.add(JoinModel.getInstance().getJoinInitialize(code)
                .subscribe(new JesSubscribe<JoinComBean>(mView) {
                    @Override
                    public void _onSuccess(JoinComBean joinComBean) {
                        mView.showInitialize(joinComBean);
                    }
                }));
    }

}
