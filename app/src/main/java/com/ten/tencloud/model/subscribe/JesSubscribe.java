package com.ten.tencloud.model.subscribe;


import com.socks.library.KLog;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.model.JesException;
import com.ten.tencloud.utils.NetWorkUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

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
        } else if (e instanceof ConnectException || e instanceof UnknownHostException) {
            exception = new JesException("网络中断，请检查您的网络状态", Constants.NET_CODE_NO_NETWORK);
        } else if (e instanceof JesException) {
            exception = (JesException) e;
            switch (exception.getCode()) {
                case Constants.NET_CODE_RE_LOGIN:
                    if (mView != null) {
                        mView.showMessage("登录过期，请重新登录");
                    }
                    TenApp.getInstance().jumpLoginActivity();
                    return;//不走_onError()
                case Constants.NET_CODE_RE_LOGIN_KICK:
                    TenApp.getInstance().jumpLoginForKickActivity(exception.getMessage());
                    return;
                //处理管理提示
                case Constants.NET_CODE_NOT_EMPLOYEE:
                    if (!AppBaseCache.getInstance().isAdmin()) {
                        exception = new JesException("您已被管理员踢出", Constants.NET_CODE_NOT_EMPLOYEE);
                    }
                    break;
            }
        } else {
            exception = new JesException(e.getMessage(), 100050);
        }
        _onError(exception);
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
