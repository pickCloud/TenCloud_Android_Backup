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

    private String msg;
    private String mPositiveText;
    private String mCancelText;
    private OnButtonClickListener mOnPositiveClickListener;

    private TextView mTvMsg;
    private TextView mTvPositiveButton;
    private TextView mMCancelButton;

    public CommonDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉头
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        window.setContentView(R.layout.dialog_common);

        mTvMsg = window.findViewById(R.id.tv_msg);
        mTvPositiveButton = window.findViewById(R.id.tv_ok);
        mMCancelButton = window.findViewById(R.id.tv_cancel);

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

    public interface OnButtonClickListener {
        void onClick(Dialog dialog);
    }
}
