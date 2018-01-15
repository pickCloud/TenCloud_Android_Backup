package com.ten.tencloud.module.login.contract;


import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;

/**
 * Created by lxq on 2017/11/21.
 */

public class JoinCom2Contract {
    public interface View extends IBaseView {
        void success();
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {

        void joinCompany(boolean isNeedInfo,String newPassword, String code, String mobile, String name, String id_card);

    }
}
