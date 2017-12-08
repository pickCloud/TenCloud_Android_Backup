package com.ten.tencloud.base.view;

/**
 * 加载数据的View层
 * Created by lxq on 2017/11/20.
 */
public interface ILoadDataView extends IBaseView {
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
     * 中途停止
     */
    void halfwayStop();


}
