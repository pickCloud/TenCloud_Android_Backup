package com.ten.tencloud.module.app.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.ServerBean;
import com.ten.tencloud.bean.ServerHeatBean;
import com.ten.tencloud.bean.ServerThresholdBean;

import java.util.List;

/**
 * Created by chenxh@10.com on 2018/3/26.
 */
public class AppServiceHomeContract {
    public interface View extends IBaseView {

        void showEmptyView();

    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {

    }
}
