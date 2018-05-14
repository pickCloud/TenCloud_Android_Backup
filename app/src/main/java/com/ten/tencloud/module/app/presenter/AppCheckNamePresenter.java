package com.ten.tencloud.module.app.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.app.contract.AppCheckNameContract;
import com.ten.tencloud.module.app.model.AppModel;

/**
 * Create by chenxh@10.com on 2018/4/2.
 */
public class AppCheckNamePresenter extends BasePresenter<AppCheckNameContract.View> implements AppCheckNameContract.Presenter<AppCheckNameContract.View> {

    @Override
    public void checkDeployName(String name, int appId) {
        mSubscriptions.add(AppModel.getInstance().checkDeployName(name, appId)
                .subscribe(new JesSubscribe<Object>(mView) {
                    @Override
                    public void _onSuccess(Object o) {
                        mView.checkResult();
                    }
                }));
    }
}
