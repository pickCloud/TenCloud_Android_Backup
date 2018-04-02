package com.ten.tencloud.module.app.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.bean.AppBrief;
import com.ten.tencloud.bean.ServerBean;
import com.ten.tencloud.bean.ServerHeatBean;
import com.ten.tencloud.bean.ServerThresholdBean;

import java.util.List;

/**
 * Created by chenxh@10.com on 2018/3/26.
 */
public class AppServiceHomeContract {
    public static final int APP_EMPTY_VIEW = 0;
    public static final int DEPLOY_EMPTY_VIEW = 1;
    public static final int SERVICE_EMPTY_VIEW = 2;

    public interface View extends IBaseView {

        void showEmptyView(int type);

        void showAppBfief(AppBrief appBrief);

        void showAppList(List<AppBean> appBeanList);

    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {

        void getAppBrief();

        void getAppList();

    }
}
