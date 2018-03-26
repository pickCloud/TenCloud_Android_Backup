package com.ten.tencloud.module.other.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.ServerThresholdBean;

/**
 * Created by lxq on 2018/1/2.
 */

public class SplashContract {
    public interface View extends IBaseView {
        void showThreshold(ServerThresholdBean bean);

    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {
        void getThreshold();
    }
}
