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

import com.ten.tencloud.R;

/**
 * Created by lxq on 2017/11/20.
 */

public class ImageAddSuccessDialog extends Dialog {

    public ImageAddSuccessDialog(@NonNull Context context, OnBtnListener onBtnListener) {
        super(context);
        mOnBtnListener = onBtnListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉头
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        window.setContentView(R.layout.dialog_image_add_success);

        window.findViewById(R.id.tv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        window.findViewById(R.id.tv_select_file).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBtnListener != null) {
                    mOnBtnListener.onSelectFile();
                }
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

    private OnBtnListener mOnBtnListener;

    public interface OnBtnListener {
        void onSelectFile();
    }

}
