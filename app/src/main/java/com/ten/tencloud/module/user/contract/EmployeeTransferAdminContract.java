package com.ten.tencloud.module.user.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;

/**
 * Created by lxq on 2017/12/14.
 */
public class EmployeeTransferAdminContract {
    public interface View extends IBaseView {
        void transferSuccess();
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {
        void transferAdmin(int uid);
    }
}
