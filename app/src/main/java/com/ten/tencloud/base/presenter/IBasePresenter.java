package com.ten.tencloud.base.presenter;


import com.ten.tencloud.base.view.IBaseView;

/**
 *
 * Created by lxq on 2017/11/20.
 */
public interface IBasePresenter<V extends IBaseView> {
    /**
     * 绑定View
     * @param view
     */
    void attachView(V view);

    /**
     * 解绑View
     */
    void detachView();

    void showErrorMessage(String error);
}
