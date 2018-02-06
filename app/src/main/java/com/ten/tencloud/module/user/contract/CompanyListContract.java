package com.ten.tencloud.module.user.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.CompanyBean;

import java.util.List;

/**
 * Created by lxq on 2017/12/14.
 */
public class CompanyListContract {
    public interface View extends IBaseView {

        void showCompanies(List<CompanyBean> companies);

        void showEmptyView();
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {

        void getCompanies();

    }
}
