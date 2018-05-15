package com.ten.tencloud.module.app.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.AppContainerBean;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.app.contract.AppK8sDeployContract;
import com.ten.tencloud.module.app.model.AppModel;

/**
 * Create by chenxh@10.com on 2018/4/2.
 */
public class AppK8sDeployPresenter extends BasePresenter<AppK8sDeployContract.View> implements AppK8sDeployContract.Presenter<AppK8sDeployContract.View> {

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

    @Override
    public void generateYAML(AppContainerBean bean) {
        mSubscriptions.add(AppModel.getInstance().generateDeployYAML(bean)
                .subscribe(new JesSubscribe<Object>(mView) {
                    @Override
                    public void _onSuccess(Object o) {
                        mView.showYAML(o.toString());
                    }
                }));
    }
}
