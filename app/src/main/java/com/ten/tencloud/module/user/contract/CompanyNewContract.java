package com.ten.tencloud.module.user.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.CompanyBean;
import com.ten.tencloud.model.JesException;

/**
 * Created by lxq on 2017/12/14.
 */
public class CompanyNewContract {
    public interface View extends IBaseView {

        void showSuccess(CompanyBean bean);

        void showFailed(JesException e);
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {

        void createCompany(String name, String contact, String phone);

    }
}
