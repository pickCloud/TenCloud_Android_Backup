package com.ten.tencloud.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.ten.tencloud.R;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by lxq on 2017/11/20.
 */

public class LoadDialog extends Dialog {

    private static final long MIN_LOADING_TIME = 500;//最少显示的时间

    private long showTime;

    public LoadDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉头
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        window.setContentView(R.layout.dialog_loading);
        window.setBackgroundDrawable(new BitmapDrawable());
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                showTime = System.currentTimeMillis();
            }
        });
    }

    public void cancelDelay() {
        long currentTime = System.currentTimeMillis();
        long intervalTime = currentTime - showTime;
        if (intervalTime < MIN_LOADING_TIME) {
            Observable.just("").delay((MIN_LOADING_TIME - intervalTime), TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Object>() {
                        @Override
                        public void call(Object o) {
                            cancel();
                        }
                    });
        } else {
            cancel();
        }
    }

}
