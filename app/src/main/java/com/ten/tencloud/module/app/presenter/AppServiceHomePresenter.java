package com.ten.tencloud.module.app.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.bean.AppBrief;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.app.contract.AppServiceHomeContract;
import com.ten.tencloud.module.app.model.AppModel;

import java.util.List;

/**
 * Created by chenxh@10.com on 2018/3/26.
 */
public class AppServiceHomePresenter extends BasePresenter<AppServiceHomeContract.View>
        implements AppServiceHomeContract.Presenter<AppServiceHomeContract.View> {


    @Override
    public void getAppBrief() {
        mSubscriptions.add(AppModel.getInstance().getAppBrief()
                .subscribe(new JesSubscribe<AppBrief>(mView) {
                    @Override
                    public void _onSuccess(AppBrief appBrief) {
                        mView.showAppBfief(appBrief);
                    }
                }));
    }

    @Override
    public void getAppList() {
        mSubscriptions.add(AppModel.getInstance().getAppListByPage(1, 2)
                .subscribe(new JesSubscribe<List<AppBean>>(mView) {
                    @Override
                    public void _onSuccess(List<AppBean> appBeanList) {
                        if (appBeanList == null || appBeanList.size() == 0)
                            mView.showEmptyView(AppServiceHomeContract.APP_EMPTY_VIEW);
                        else mView.showAppList(appBeanList);
                    }
                }));
    }


}
