package com.ten.tencloud.module.app.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.AppContainerBean;

/**
 * Create by chenxh@10.com on 2018/4/2.
 */
public class AppK8sDeployContract {

    public interface View extends IBaseView {
        void checkResult();

        void showYAML(String yaml);
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {
        void checkDeployName(String name, int appId);

        void generateYAML(AppContainerBean bean);
    }
}
