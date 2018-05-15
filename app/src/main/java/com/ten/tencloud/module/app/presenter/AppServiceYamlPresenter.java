package com.ten.tencloud.module.app.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.AppServiceYAMLBean;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.app.contract.AppServiceYamlContract;
import com.ten.tencloud.module.app.model.AppModel;

/**
 * Create by chenxh@10.com on 2018/4/2.
 */
public class AppServiceYamlPresenter extends BasePresenter<AppServiceYamlContract.View> implements AppServiceYamlContract.Presenter<AppServiceYamlContract.View> {

    @Override
    public void generateYAML(AppServiceYAMLBean bean) {
        mSubscriptions.add(AppModel.getInstance().generateServiceYAML(bean)
                .subscribe(new JesSubscribe<Object>(mView) {
                    @Override
                    public void _onSuccess(Object o) {
                        mView.showYAML(o.toString());
                    }
                }));
    }
}
