package com.ten.tencloud.base.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.ten.tencloud.model.JesException;
import com.ten.tencloud.utils.ToastUtils;
import com.ten.tencloud.widget.dialog.LoadDialog;

import java.util.HashMap;

import butterknife.ButterKnife;

/**
 * Created by lxq on 2017/11/21.
 */

public abstract class BasePager extends LinearLayout implements IBaseView {

    protected Context mContext;

    private LoadDialog mLoadDialog;

    private HashMap<String, Object> args = new HashMap<>();

    public BasePager(Context context) {
        this(context, null);
    }

    public BasePager(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasePager(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    public BasePager putArgument(String key, Object value) {
        args.put(key, value);
        return this;
    }

    protected <T> T getArgument(String key) {
        return (T) args.get(key);
    }

    protected <T> T getArgument(String key, T defValue) {
        T value = (T) args.get(key);
        if (value == null) {
            return defValue;
        }
        return value;
    }

    public View createView(@LayoutRes int layoutId) {
        View view = View.inflate(mContext, layoutId, this);
        ButterKnife.bind(this, view);
        return view;
    }

    public abstract void init();

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    public void onActivityDestroy() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtils.showShortToast(message);
    }

    @Override
    public void showMessage(@StringRes int messageId) {
        ToastUtils.showShortToast(messageId);
    }

    @Override
    public void showLoading() {
        if (mLoadDialog == null) {
            mLoadDialog = new LoadDialog(mContext);
        }
        mLoadDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mLoadDialog != null) {
            mLoadDialog.cancelDelay();
        }
    }

    @Override
    public void showError(JesException e) {
        showMessage(e.getMessage());
    }
}
