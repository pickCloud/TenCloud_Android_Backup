package com.ten.tencloud.module.app.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.bean.MessageBean;

import java.util.List;

/**
 * Create by chenxh@10.com on 2018/4/2.
 */
public class AppListContract {

    public interface View extends IBaseView {

        void showEmpty(boolean isLoadMore);

        void showAppList(List<AppBean> msg, boolean isLoadMore);

    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {

        void getAppList();

        void getAppListByPage(boolean isLoadMore);

    }
}
