package com.ten.tencloud.module.app.presenter;

import com.blankj.utilcode.util.ObjectUtils;
import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.app.contract.AppDetailContract;
import com.ten.tencloud.module.app.contract.SubApplicationContract;
import com.ten.tencloud.module.app.model.AppModel;
import com.ten.tencloud.module.main.contract.MainContract;

import java.util.List;

public class SubApplicationPresenter extends BasePresenter<SubApplicationContract.View> implements SubApplicationContract.Presenter<SubApplicationContract.View> {
    @Override
    public void getAppSubApplicationList(int master_app) {
        mSubscriptions.add(AppModel.getInstance().getAppSubApplicationList(master_app)
        .subscribe(new JesSubscribe<List<AppBean>>(mView) {
            @Override
            public void _onSuccess(List<AppBean> appBeans) {
                mView.showSubApplicationList(appBeans);

            }
        }));
    }

    @Override
    public void getSubApplicationListById(int master_app, int id) {
        mSubscriptions.add(AppModel.getInstance().getAppSubApplicationById(master_app, id)
        .subscribe(new JesSubscribe<List<AppBean>>(mView) {
            @Override
            public void _onSuccess(List<AppBean> appBeans) {
                if (!ObjectUtils.isEmpty(appBeans) && appBeans.size() > 0){
                    mView.showSubApplicationDetails(appBeans.get(0));
                }
            }
        }));
    }
}
