package com.ten.tencloud.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.ten.tencloud.R;
import com.ten.tencloud.widget.ProgressRingView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lxq on 2017/11/20.
 */

public class ServerImportProgressDialog extends Dialog {

    @BindView(R.id.prv_progress)
    ProgressRingView mProgress;

    public ServerImportProgressDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉头
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        window.setContentView(R.layout.dialog_server_import_progress);

        window.setBackgroundDrawable(new BitmapDrawable());
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        ButterKnife.bind(this);

    }

    public void setProgress(int progress) {
        mProgress.setProgress(progress);
    }

}
