package com.ten.tencloud.module.user.contract;


import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;

/**
 * Created by lxq on 2017/11/21.
 */

public class SettingChangePhoneContract {
    public interface View extends IBaseView {
        void changeSuccess();
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {

        void change(String mobile, String new_password, String auth_code);

    }
}
