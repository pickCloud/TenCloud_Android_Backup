package com.ten.tencloud.module.user.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.CompanyBean;

/**
 * Created by lxq on 2017/12/14.
 */
public class CompanyInfoContract {
    public interface View extends IBaseView {
        void showCompanyInfo(CompanyBean companyInfo);
        void updateSuccess();

    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {
        void getCompanyByCid(int cid);

        void updateCompanyInfo(int cid, String name, String contact, String mobile, String image);
    }
}

