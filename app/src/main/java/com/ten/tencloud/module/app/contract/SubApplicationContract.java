package com.ten.tencloud.module.app.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.bean.DeploymentBean;

import java.util.List;

/**
 * 子应用
 */
public class SubApplicationContract {
    public interface View extends IBaseView {

        void showSubApplicationList(List<AppBean> appBeans);
        void showSubApplicationDetails(AppBean appBean);
        void showDeploymentLatestDetails(DeploymentBean appBean);

    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {

        void getAppSubApplicationList(int master_app);

        void getSubApplicationListById(int master_app, int id);
        void getDeploymentLatestById(int id);

    }
}
