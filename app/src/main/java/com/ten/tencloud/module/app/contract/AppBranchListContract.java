package com.ten.tencloud.module.app.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;

import java.util.List;

/**
 * Create by chenxh@10.com on 2018/4/2.
 */
public class AppBranchListContract {

    public interface View extends IBaseView {

        void showEmpty();

        void showBranchList(List<String> branches);

        void goAuth(String url);
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {

        void getBranchList(String name, String url);

    }
}
