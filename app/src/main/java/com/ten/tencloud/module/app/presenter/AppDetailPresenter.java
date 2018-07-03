package com.ten.tencloud.module.app.presenter;

import com.blankj.utilcode.util.ObjectUtils;
import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.bean.ServiceBriefBean;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.app.contract.AppDetailContract;
import com.ten.tencloud.module.app.model.AppModel;

import java.util.List;

/**
 * Create by chenxh@10.com on 2018/4/3.
 */
public class AppDetailPresenter extends BasePresenter<AppDetailContract.View> implements AppDetailContract.Presenter<AppDetailContract.View> {

    @Override
    public void getAppById(int id) {
        mSubscriptions.add(AppModel.getInstance().getAppById(id)
                .subscribe(new JesSubscribe<List<AppBean>>(mView) {
                    @Override
                    public void _onSuccess(List<AppBean> appBeanList) {
                        if (appBeanList != null && appBeanList.size() > 0)
                            mView.showAppDetail(appBeanList.get(0));
                    }
                }));
    }

    @Override
    public void getAppServiceBriefById(int app_id) {
        mSubscriptions.add(AppModel.getInstance().getAppServiceBriefById(app_id)
                .subscribe(new JesSubscribe<List<ServiceBriefBean>>(mView) {
                    @Override
                    public void _onSuccess(List<ServiceBriefBean> serviceBriefBeans) {
                        if (!ObjectUtils.isEmpty(serviceBriefBeans) && serviceBriefBeans.size() > 0)
                            mView.showServiceBriefDetails(serviceBriefBeans.get(0));

                    }
                }));
    }
}
