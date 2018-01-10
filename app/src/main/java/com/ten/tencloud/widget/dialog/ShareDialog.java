package com.ten.tencloud.widget.dialog;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ten.tencloud.R;
import com.ten.tencloud.utils.Utils;

/**
 * 分享弹窗
 * <p>
 * Created by lxq on 2018/1/2.
 */

public class ShareDialog extends BottomSheetDialog {

    private Context mContext;
    private String mContent;

    public ShareDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    public void setContent(String content) {
        mContent = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_share, null);
        View tvCancel = view.findViewById(R.id.tv_cancel);
        View tvWeixin = view.findViewById(R.id.tv_weixin);
        View tvQQ = view.findViewById(R.id.tv_qq);
        View tvMessage = view.findViewById(R.id.tv_message);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvWeixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.sendMsg(mContext, mContent);
                dismiss();
            }
        });
        setContentView(view);
        Window window = getWindow();
        window.setBackgroundDrawable(new BitmapDrawable());
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }
}
