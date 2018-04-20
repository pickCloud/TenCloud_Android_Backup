package com.ten.tencloud.module.app.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.ImageBean;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.app.contract.AppImageContract;
import com.ten.tencloud.module.app.model.AppModel;

import java.util.List;

/**
 * Create by chenxh@10.com on 2018/4/3.
 */
public class AppImagePresenter extends BasePresenter<AppImageContract.View> implements AppImageContract.Presenter<AppImageContract.View> {

    @Override
    public void getAppImageById(String appId) {
        mSubscriptions.add(AppModel.getInstance().getAppImages(appId)
                .subscribe(new JesSubscribe<List<ImageBean>>(mView) {
                    @Override
                    public void _onSuccess(List<ImageBean> images) {
                        if (images != null && images.size() > 0) {
                            mView.showImages(images);
                        } else {
                            mView.showImageEmpty();
                        }
                    }
                }));
    }
}
