package com.ten.tencloud.module.app.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.ExampleBean;
import com.ten.tencloud.bean.ServiceBean;
import com.ten.tencloud.bean.ServicePortBean;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.app.contract.AppServiceRulesContract;
import com.ten.tencloud.module.app.model.AppModel;

import java.util.List;
import java.util.Map;

public class AppServiceRulesPresenter extends BasePresenter<AppServiceRulesContract.View> implements AppServiceRulesContract.Presenter<AppServiceRulesContract.View> {
    @Override
    public void servicePort(int app_id) {
        mSubscriptions.add(AppModel.getInstance().servicePort(app_id)
                .subscribe(new JesSubscribe<List<ServicePortBean>>(mView) {
                    @Override
                    public void _onSuccess(List<ServicePortBean> servicePortBeanList) {
                        mView.servicePortList(servicePortBeanList);
                    }
                }));
    }

    @Override
    public void ingressIngress(Map<String, Object> objectMap) {
        mSubscriptions.add(AppModel.getInstance().ingressIngress(objectMap)
                .subscribe(new JesSubscribe<Object>(mView) {
                    @Override
                    public void _onSuccess(Object o) {
                        mView.showResult(o);
                    }
                }));

    }

    @Override
    public void podLabels(int app_id) {
        mSubscriptions.add(AppModel.getInstance().podLabels(app_id)
                .subscribe(new JesSubscribe<List<ExampleBean>>(mView) {
                    @Override
                    public void _onSuccess(List<ExampleBean> exampleBeans) {
                        mView.podLabels(exampleBeans);
                    }
                }));
    }
}
