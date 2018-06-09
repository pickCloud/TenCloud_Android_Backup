package com.ten.tencloud.base.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ten.tencloud.model.JesException;
import com.ten.tencloud.widget.dialog.LoadDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lxq on 2017/11/20.
 */
public abstract class BaseFragment extends Fragment implements IBaseView {

    private ProgressDialog mProgressDialog;
    private Toast mToast;
    public Activity mActivity;
    private Unbinder unbinder;
    private LoadDialog mLoadDialog;

    private Bundle arguments;

    private boolean isInit;

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        mActivity = activity;
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;

    }

    protected View createView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @LayoutRes int layoutId) {
        final View fragmentView = inflater.inflate(layoutId, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        isInit = true;
        return fragmentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint() && isInit) {
            onVisible();
        } else {
            onInVisible();
        }
    }

    /**
     * 可见。相当于activity的onResume
     * 有bug需手动处理默认页不调用的情况
     */
    public void onVisible() {

    }

    public void onInVisible() {
        hideKeyboard();
    }

    protected void showToastMessage(String message) {
        if (!TextUtils.isEmpty(message)) {
            if (mToast == null) {
                mToast = Toast.makeText(mActivity, "", Toast.LENGTH_SHORT);
            }
            mToast.setText(message);
            mToast.show();
        }
    }

    protected void showToastMessage(@StringRes int messageId) {
        if (messageId != 0) {
            String message = mActivity.getResources().getString(messageId);
            showToastMessage(message);
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        showToastMessage(message);
    }

    @Override
    public void showMessage(@StringRes int StringRes) {
        showToastMessage(StringRes);
    }


    @Override
    public void showLoading() {
        if (mLoadDialog == null) {
            mLoadDialog = new LoadDialog(mActivity);
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

    @Override
    public Context getContext() {
        return mActivity;
    }

    protected void changeTitle(String title) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).changeTitle(title);
        }
    }

    /**
     * 搜索栏相关
     *
     * @param searchListener
     * @param addListener
     */
    public void showSearchView(String hintText, BaseActivity.OnSearchListener searchListener, View.OnClickListener addListener) {
        if (getActivity() instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) getActivity();
            activity.showSearchView(hintText, searchListener, addListener);
        }
    }

    /**
     * 显示搜索栏
     * @param hintText
     * @param searchListener
     */
    public void showSearchView(String hintText, BaseActivity.OnSearchListener searchListener) {
        if (getActivity() instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) getActivity();
            activity.showSearchView(hintText, searchListener);
        }
    }

    /**
     * 隐藏搜索栏
     */
    public void hideSearchView() {
        if (getActivity() instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) getActivity();
            activity.hideSearchView();
        }
    }

    /**
     * 设置Toolbar没阴影效果
     */
    public void setToolBarNoShade() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (getActivity() instanceof BaseActivity) {
                BaseActivity activity = (BaseActivity) getActivity();
                activity.setToolBarNoShade();
            }
        }
    }

    /**
     * 设置Toolbar没阴影效果
     */
    public void setToolBarHasShade() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (getActivity() instanceof BaseActivity) {
                BaseActivity activity = (BaseActivity) getActivity();
                activity.setToolBarHasShade();
            }
        }
    }

    /**
     * 隐藏AppBar
     */
    public void hideToolBar() {
        if (getActivity() instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) getActivity();
            activity.hideToolBar();
        }
    }

    /**
     * 隐藏AppBar
     */
    public void hideKeyboard() {
        if (getActivity() instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) getActivity();
            activity.hideKeyboard();
        }
    }

    /**
     * 修改标题
     * @param title
     */
    protected void changeTitle(@StringRes int title) {
        if (getActivity() instanceof BaseActivity) {
            String string = getActivity().getString(title);
            ((BaseActivity) getActivity()).changeTitle(string);
        }
    }

    /**
     * 传递参数
     * @param key
     * @param value
     * @return
     */
    public BaseFragment putArgument(String key, String value) {
        if (arguments == null) {
            arguments = new Bundle();
        }
        arguments.putString(key, value);
        setArguments(arguments);
        return this;
    }

    /**
     * 传递参数
     * @param key
     * @param value
     * @return
     */
    public BaseFragment putArgument(String key, int value) {
        if (arguments == null) {
            arguments = new Bundle();
        }
        arguments.putInt(key, value);
        setArguments(arguments);
        return this;
    }

}
