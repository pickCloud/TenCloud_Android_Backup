package com.ten.tencloud.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.utils.Utils;

/**
 * Created by lxq on 2017/11/20.
 */

public class CommonDialog extends Dialog {

    private String mTitle;
    private String msg;
    private String mPositiveText;
    private String mCancelText;
    private boolean mIsShowTitle = false;//头部标题
    private boolean mIsTwoButton = true;//两个按钮

    private OnButtonClickListener mOnPositiveClickListener;

    private TextView mTvMsg;
    private TextView mTvPositiveButton;
    private TextView mMCancelButton;
    private View mLlBtn1;
    private View mLlBtn2;
    private TextView mTvOk2;
    private TextView mTvTitle;

    public CommonDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉头
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        window.setContentView(R.layout.dialog_common);

        mTvTitle = window.findViewById(R.id.tv_title);
        mTvMsg = window.findViewById(R.id.tv_msg);
        mTvPositiveButton = window.findViewById(R.id.tv_ok);
        mMCancelButton = window.findViewById(R.id.tv_cancel);
        mLlBtn1 = window.findViewById(R.id.ll_btn_1);
        mLlBtn2 = window.findViewById(R.id.ll_btn_2);
        mTvOk2 = window.findViewById(R.id.tv_ok2);

        mLlBtn1.setVisibility(mIsTwoButton ? View.VISIBLE : View.GONE);
        mLlBtn2.setVisibility(!mIsTwoButton ? View.VISIBLE : View.GONE);

        mTvTitle.setVisibility(mIsShowTitle ? View.VISIBLE : View.GONE);
        mTvTitle.setText(mTitle);
        if (mIsShowTitle) {
            mTvMsg.setTextColor(getContext().getResources().getColor(R.color.text_color_556278));
            mTvMsg.setTextSize(12);
        }

        mMCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mTvPositiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnPositiveClickListener != null) {
                    mOnPositiveClickListener.onClick(CommonDialog.this);
                }
            }
        });

        mTvOk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        window.setBackgroundDrawable(new BitmapDrawable());
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void show() {
        super.show();
        mTvMsg.setText(msg);
        mTvPositiveButton.setText(mPositiveText);
        mMCancelButton.setText(Utils.strIsEmptyForDefault(mCancelText, "取消"));
    }

    public CommonDialog setMessage(String msg) {
        this.msg = msg;
        return this;
    }

    public CommonDialog setPositiveButton(String text, OnButtonClickListener onClickListener) {
        mPositiveText = text;
        mOnPositiveClickListener = onClickListener;
        return this;
    }

    public CommonDialog setCancelButton(String text) {
        mCancelText = text;
        return this;
    }

    public CommonDialog setTwoButton(boolean twoButton) {
        mIsTwoButton = twoButton;
        return this;
    }

    public CommonDialog setShowTitle(boolean showTitle) {
        mIsShowTitle = showTitle;
        return this;
    }

    public CommonDialog setTitle(String title) {
        mTitle = title;
        return this;
    }

    public interface OnButtonClickListener {
        void onClick(Dialog dialog);
    }
}
