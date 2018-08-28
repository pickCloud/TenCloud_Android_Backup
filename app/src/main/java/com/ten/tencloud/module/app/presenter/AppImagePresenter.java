package com.ten.tencloud.module.app.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.AppBean;
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
    public void getAppImageById(Integer app_id) {
        mSubscriptions.add(AppModel.getInstance().getAppImages(app_id)
                .subscribe(new JesSubscribe<List<ImageBean>>(mView) {
                    @Override
                    public void _onSuccess(List<ImageBean> images) {
                        if (images != null && images.size() > 0) {
                            mView.showImages(images, false);
                        } else {
                            mView.showImageEmpty(false);
                        }
                    }
                }));
    }

    @Override
    public void getAppListByPage(int app_id, final boolean isLoadMore) {
        handleLoadMore(isLoadMore);
        mSubscriptions.add(AppModel.getInstance().getAppImages(app_id, page, page_num)
                .subscribe(new JesSubscribe<List<ImageBean>>(mView) {
                    @Override
                    public void _onSuccess(List<ImageBean> appBeans) {
                        if (appBeans == null || appBeans.size() == 0) {
                            mView.showImageEmpty(isLoadMore);
                            return;
                        }
                        page++;
                        mView.showImages(appBeans, isLoadMore);
                    }
                }));
    }
}
