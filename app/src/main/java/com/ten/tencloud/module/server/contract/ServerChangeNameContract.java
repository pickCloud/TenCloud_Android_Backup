package com.ten.tencloud.module.server.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;

/**
 * Created by lxq on 2017/11/23.
 */

public class ServerChangeNameContract {
    public interface View extends IBaseView {
        void changeSuccess();
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {
        void changeName(String id, String name);
    }
}
