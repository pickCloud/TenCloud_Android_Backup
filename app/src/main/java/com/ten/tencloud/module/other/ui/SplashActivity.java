package com.ten.tencloud.module.other.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.module.login.ui.LoginActivity;
import com.ten.tencloud.module.main.ui.MainActivity;
import com.ten.tencloud.utils.StatusBarUtils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_splash);
        hideToolBar();
        StatusBarUtils.setTransparent(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        init();
    }

    private void init() {
        Observable.just(AppBaseCache.getInstance().getToken()).delay(3, TimeUnit.SECONDS)
                .map(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return TextUtils.isEmpty(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean isEmpty) {
                        if (isEmpty) {
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        } else {
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        }
                        finish();
                    }
                });
    }
}
