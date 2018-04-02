package com.ten.tencloud.base.presenter;

import android.support.annotation.NonNull;

import com.ten.tencloud.base.view.IBaseView;

import rx.subscriptions.CompositeSubscription;

/**
 * 辅助presenter类，实现一些通用的方法（不涉及加载数据）
 */

public abstract class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {

    protected V mView;
    //包装所有的订阅者，便于做生命周期的管理
    protected CompositeSubscription mSubscriptions;

    protected BasePresenter() {
        mSubscriptions = new CompositeSubscription();
    }

    protected int page = 1; //当前页
    protected int page_num = 10; //每页几条

    /**
     * 分页加载初始化页数等
     */
    public void initPage() {
        this.page = 1;
    }

    public void handleLoadMore(boolean isLoadMore) {
        if (!isLoadMore) {
            initPage();
        }
    }

    @Override
    public void attachView(@NonNull V view) {
        this.mView = view;
    }

    @Override
    public void showErrorMessage(String error) {
        mView.showMessage(error);
    }

    @Override
    public void detachView() {
        unSubscribe();
        mView.hideLoading();
        this.mView = null;
    }

    public void unSubscribe() {
        mSubscriptions.unsubscribe();
    }
}
