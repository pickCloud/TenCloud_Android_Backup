package com.ten.tencloud.model.subscribe;


import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.model.JesException;
import com.ten.tencloud.utils.NetWorkUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * 通用Subscribe
 * 统一处理异常
 * Created by lxq on 2017/11/20.
 */

public abstract class JesSubscribe<T> extends Subscriber<T> {

    private IBaseView mView;

    public JesSubscribe(IBaseView view) {
        this.mView = view;
    }

    @Override
    public void onStart() {
        //处理无网络情况
        boolean networkAvailable = NetWorkUtils.isNetworkAvailable(TenApp.getInstance());
        if (!networkAvailable) {
            onError(new JesException("网络中断，请检查您的网络状态", Constants.NET_CODE_NO_NETWORK));
            unsubscribe();
            return;
        }
        _onStart();
    }

    public void _onStart() {
        if (mView != null) {
            mView.showLoading();
        }
    }

    @Override
    public void onCompleted() {
        if (mView != null) {
            mView.hideLoading();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (mView != null) {
            mView.hideLoading();
        }
        e.printStackTrace();
        JesException exception;
        if (e instanceof SocketTimeoutException) {
            exception = new JesException("连接超时", Constants.NET_CODE_TIME_OUT);
        } else if (e instanceof ConnectException) {
            exception = new JesException("网络中断，请检查您的网络状态", Constants.NET_CODE_NO_NETWORK);
        } else if (e instanceof JesException) {
            exception = (JesException) e;
            handleJesException(exception);
        } else {
            exception = new JesException(e.getMessage(), 100050);
        }
        _onError(exception);
    }

    private void handleJesException(JesException exception) {
        switch (exception.getCode()) {
            case Constants.NET_CODE_RE_LOGIN:
                if (mView != null) {
                    mView.showMessage("登录过期，请重新登录");
                }
                TenApp.getInstance().jumpLoginActivity();
                break;
        }
    }

    @Override
    public void onNext(T result) {
        _onSuccess(result);
    }

    public abstract void _onSuccess(T t);

    public void _onError(JesException e) {
        if (mView != null) {
            mView.showError(e);
        }
    }

}
