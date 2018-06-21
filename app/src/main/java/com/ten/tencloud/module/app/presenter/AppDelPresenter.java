package com.ten.tencloud.module.app.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.ImageBean;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.app.contract.AppImageContract;
import com.ten.tencloud.module.app.contract.ApplicationDelContract;
import com.ten.tencloud.module.app.model.AppModel;

import java.util.List;

/**
 * Create by chenxh@10.com on 2018/4/3.
 */
public class AppDelPresenter extends BasePresenter<ApplicationDelContract.View> implements ApplicationDelContract.Presenter<ApplicationDelContract.View> {

    @Override
    public void deleteApp(int id) {
        mSubscriptions.add(AppModel.getInstance().applicationDel(id)
                .subscribe(new JesSubscribe<Object>(mView) {
                    @Override
                    public void _onSuccess(Object o) {
                        mView.showResult(o);

                    }
                }));
    }
}
