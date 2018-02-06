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

/**
 * Created by lxq on 2017/11/20.
 */

public class ServerAddFailureDialog extends Dialog {

    private TextView mTvCause;

    public ServerAddFailureDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉头
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        window.setContentView(R.layout.dialog_server_add_failure);

        window.findViewById(R.id.tv_add_server).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        mTvCause = window.findViewById(R.id.tv_cause);

        window.setBackgroundDrawable(new BitmapDrawable());
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    public void setCause(String cause) {
        mTvCause.setText(cause);
    }

}
