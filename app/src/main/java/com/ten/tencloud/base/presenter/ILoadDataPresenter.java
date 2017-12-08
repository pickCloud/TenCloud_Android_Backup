package com.ten.tencloud.base.presenter;


import com.ten.tencloud.base.view.IBaseView;

/**
 *
 * Created by lxq on 2017/11/20.
 */

public interface ILoadDataPresenter<V extends IBaseView> extends IBasePresenter<V> {
    /**
     * 显示加载滚动条
     */
    void showLoading();

    /**
     * 隐藏加载滚动条
     */
    void hideLoading();

    /**
     * 显示加载重试
     */
    void showRetry();

    /**
     * 隐藏加载重试
     */
    void hideRetry();

    /**
     * 中途停止任务
     */
    void halfwayStop();
}
