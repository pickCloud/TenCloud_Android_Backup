package com.ten.tencloud.module.user.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.CompanyBean;
import com.ten.tencloud.bean.User;

import java.util.List;

/**
 * Created by lxq on 2017/12/14.
 */
public class UserHomeContract {
    public interface View extends IBaseView {
        void showUserInfo(User user);

        void showCompanies(List<CompanyBean> companies);

        void showCompanyInfo(CompanyBean companyInfo);
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {
        void getUserInfo();

        void getCompanies();

        void getCompanyByCid(int cid);
    }
}
