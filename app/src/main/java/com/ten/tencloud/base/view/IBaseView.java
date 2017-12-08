package com.ten.tencloud.base.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.ten.tencloud.model.JesException;


/**
 * Created by lxq on 2017/11/20.
 */

public interface IBaseView {

    void showMessage(@NonNull String message);

    void showMessage(@StringRes int messageId);

    void showLoading();

    void hideLoading();

    void showError(JesException e);

    /**
     * 获得Context
     */
    Context getContext();
}
