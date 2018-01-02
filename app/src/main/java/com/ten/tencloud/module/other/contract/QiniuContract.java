package com.ten.tencloud.module.other.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;

/**
 * Created by lxq on 2018/1/2.
 */

public class QiniuContract {
    public interface View extends IBaseView {
        void uploadSuccess(String path);

        void uploadFiled();

    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {
        void uploadFile(String path);
    }
}
