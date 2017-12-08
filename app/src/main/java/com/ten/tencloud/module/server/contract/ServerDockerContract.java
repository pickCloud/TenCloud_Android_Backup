package com.ten.tencloud.module.server.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;

import java.util.List;

/**
 * Created by lxq on 2017/11/23.
 */

public class ServerDockerContract {
    public interface View extends IBaseView {
        void showDocker(List<List<String>> dockers);
        void showEmpty();
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {
        void getDockerList(String id);
    }
}
