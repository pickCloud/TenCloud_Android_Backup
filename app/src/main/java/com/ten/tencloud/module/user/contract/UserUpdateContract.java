package com.ten.tencloud.module.user.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;

/**
 * Created by lxq on 2017/12/14.
 */
public class UserUpdateContract {
    public interface View extends IBaseView {
        void updateSuccess();
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {
        void updateUserInfo(String key, String value);

        void updateCompanyInfo(int cid, String name, String contact, String mobile, String image);
    }
}
