package com.ten.tencloud.module.app.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.AppServiceYAMLBean;

/**
 * Create by chenxh@10.com on 2018/4/2.
 */
public class AppServiceYamlContract {

    public interface View extends IBaseView {
        void showYAML(String yaml);
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {

        void generateYAML(AppServiceYAMLBean bean);
    }
}
