package com.ten.tencloud.module.server.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;

/**
 * Created by lxq on 2017/11/23.
 */

public class ServerOperationContract {
    public interface View extends IBaseView {
        void rebootSuccess();

        void delSuccess();

        void startSuccess();

        void stopSuccess();

        void showState(String state);
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {
        void rebootServer(String id);

        void delServer(String id);

        void startServer(String id);

        void stopServer(String id);

        void queryServerState(String id);
    }
}
