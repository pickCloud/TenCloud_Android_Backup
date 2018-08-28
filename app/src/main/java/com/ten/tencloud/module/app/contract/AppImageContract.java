package com.ten.tencloud.module.app.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.ImageBean;

import java.util.List;

/**
 * Create by chenxh@10.com on 2018/4/3.
 */
public interface AppImageContract {

    interface View extends IBaseView {

        void showImages(List<ImageBean> images, boolean isLoadMore);

        void showImageEmpty(boolean isLoadMore);
    }

    interface Presenter<V extends IBaseView> extends IBasePresenter<V> {

        void getAppImageById(Integer app_id);

        void getAppListByPage(int app_id, boolean isLoadMore);

    }
}