package com.ten.tencloud.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ten.tencloud.R;

/**
 * Created by lxq on 2017/11/20.
 */

public class LoginTipsDialog extends Dialog {


    private Context mContext;
    private TextView mTvMsg;

    public LoginTipsDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉头
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        window.setContentView(R.layout.dialog_login_tips);
        window.setBackgroundDrawable(new BitmapDrawable());

        window.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        mTvMsg = window.findViewById(R.id.tv_msg);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    public void setMsg(String msg) {
        int start = msg.indexOf("于") + 1;
        int end = msg.indexOf("在") + 2;
        String replace = msg.replace("于", "于\n").replace("在", "\n在");
        SpannableStringBuilder style = new SpannableStringBuilder(replace);
        int color = mContext.getResources().getColor(R.color.colorPrimary);
        style.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvMsg.setText(style);
    }
}
