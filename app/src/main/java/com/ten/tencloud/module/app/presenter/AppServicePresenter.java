package com.ten.tencloud.module.app.presenter;

import android.app.Service;

import com.blankj.utilcode.util.ObjectUtils;
import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.AppBrief;
import com.ten.tencloud.bean.ServiceBean;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.app.contract.AppServiceContract;
import com.ten.tencloud.module.app.model.AppModel;

import java.util.List;

public class AppServicePresenter extends BasePresenter<AppServiceContract.View> implements AppServiceContract.Presenter<AppServiceContract.View> {
    @Override
    public void getServiceList(int app_id) {
        mSubscriptions.add(AppModel.getInstance().serviceList(app_id)
                .subscribe(new JesSubscribe<List<ServiceBean>>(mView) {
                    @Override
                    public void _onSuccess(List<ServiceBean> serviceBeans) {
                        mView.showServiceList(serviceBeans);
                    }
                }));
    }

    @Override
    public void getServiceDetails(int app_id, int service_id, int show_yaml) {
        mSubscriptions.add(AppModel.getInstance().serviceDetail(app_id, service_id, show_yaml)
                .subscribe(new JesSubscribe<List<ServiceBean>>(mView) {
                    @Override
                    public void _onSuccess(List<ServiceBean> serviceBeans) {
                        if (ObjectUtils.isEmpty(serviceBeans) || serviceBeans.size() == 0)
                            return;
                        mView.showServiceDetails(serviceBeans.get(0));
                    }
                }));
    }

    @Override
    public void deleteService(int app_id, int service_id) {
        mSubscriptions.add(AppModel.getInstance().serviceDel(app_id, service_id)
                .subscribe(new JesSubscribe<Object>(mView) {
                    @Override
                    public void _onSuccess(Object o) {
                        mView.showResult(o);
                    }
                }));
    }

    @Override
    public void ingressInfo(int app_id, int show_detail) {
        mSubscriptions.add(AppModel.getInstance().ingressInfo(app_id, show_detail)
                .subscribe(new JesSubscribe<ServiceBean>(mView) {
                    @Override
                    public void _onSuccess(ServiceBean serviceBean) {
                        mView.showIngressInfo(serviceBean);
                    }
                }));
    }
}
