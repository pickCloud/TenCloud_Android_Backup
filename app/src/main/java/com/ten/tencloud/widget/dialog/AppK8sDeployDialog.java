package com.ten.tencloud.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.utils.UiUtils;

/**
 * Created by lxq on 2017/11/20.
 */

public class AppK8sDeployDialog extends Dialog {

    private TextView mTvLog;
    private TextView mTvStatus;

    public AppK8sDeployDialog(@NonNull Context context) {
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
        if (mTvLog != null && !TextUtils.isEmpty(text)) {
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

    public void setStatus(boolean isSuccess) {
        mTvStatus.setText(isSuccess ? "部署成功" : "部署失败");
        mTvStatus.setTextColor(isSuccess ? UiUtils.getColor(R.color.text_color_09bb07)
                : UiUtils.getColor(R.color.text_color_ef9a9a));
    }

}
