package com.ten.tencloud.module.user.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.User;

/**
 * Created by lxq on 2017/12/14.
 */
public class UserInfoContract {
    public interface View extends IBaseView {
        void showUserInfo(User user);
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {
        void getUserInfo();
    }
}
