package com.ten.tencloud.module.app.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.ReposBean;

import java.util.List;

/**
 * Create by chenxh@10.com on 2018/4/2.
 */
public class AppReposListContract {

    public interface View extends IBaseView {

        void showEmpty();

        void showReposList(List<ReposBean> reposBeans);

        void goAuth(String url);

        void githubClearSuc();
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {

        void getReposList(String url);
        void githubClear();

    }
}
