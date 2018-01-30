package com.ten.tencloud.module.main.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;

/**
 * Created by lxq on 2018/1/2.
 */

public class MainContract {
    public interface View extends IBaseView {
        void showMsgCount(String count);

        void updatePermission();

        void updatePermissionSuccess();
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {
        void getMsgCount();

        void getPermission(final int cid);
    }
}
