package com.ten.tencloud.module.app.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.AppBean;

import java.util.List;

/**
 * 删除应用
 */
public class ApplicationDelContract {
    public interface View extends IBaseView {

        void showResult(Object o);

    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {

        void deleteApp(int id);


    }
}
