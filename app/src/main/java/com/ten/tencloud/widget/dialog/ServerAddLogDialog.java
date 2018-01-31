package com.ten.tencloud.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ten.tencloud.R;

/**
 * Created by lxq on 2017/11/20.
 */

public class ServerAddLogDialog extends Dialog {

    private TextView mTvLog;
    private TextView mTvStatus;

    public ServerAddLogDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉头
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        window.setContentView(R.layout.dialog_server_add_log);

        mTvLog = window.findViewById(R.id.tv_log);
        mTvStatus = window.findViewById(R.id.tv_status);
        //设置滚动
        mTvLog.setMovementMethod(ScrollingMovementMethod.getInstance());
        View tvClose = window.findViewById(R.id.tv_close);
        tvClose.setOnClickListener(new View.OnClickListener() {
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

    public void setLog(String text, boolean isAdd) {
        if (mTvLog != null) {
            mTvLog.append(text + "\n");
            //滚动
            int offset = mTvLog.getLineCount() * mTvLog.getLineHeight();
            if (offset > mTvLog.getHeight()) {
                mTvLog.scrollTo(0, offset - mTvLog.getHeight());
            }
        }
        if (mTvStatus != null) {
            mTvStatus.setVisibility(isAdd ? View.VISIBLE : View.INVISIBLE);
        }
    }

}
