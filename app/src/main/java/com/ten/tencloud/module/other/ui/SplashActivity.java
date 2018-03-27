package com.ten.tencloud.module.other.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.WindowManager;

import com.ten.tencloud.BuildConfig;
import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.ServerThresholdBean;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.constants.Url;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.model.SPFHelper;
import com.ten.tencloud.module.login.ui.LoginActivity;
import com.ten.tencloud.module.main.ui.MainActivity;
import com.ten.tencloud.module.other.contract.SplashContract;
import com.ten.tencloud.module.other.presenter.SplashPresenter;
import com.ten.tencloud.utils.StatusBarUtils;
import com.ten.tencloud.utils.ToastUtils;

import java.util.concurrent.TimeUnit;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

@RuntimePermissions
public class SplashActivity extends BaseActivity implements SplashContract.View {

    private SplashPresenter mSplashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_splash);
        hideToolBar();
        StatusBarUtils.setTransparent(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        if (BuildConfig.DEBUG) {
            ToastUtils.showLongToast("测试版，当前服务器地址:" + Url.BASE_URL);
        }
        mSplashPresenter = new SplashPresenter();
        mSplashPresenter.attachView(this);
        init();
    }

    private void init() {
        PackageManager pkm = getPackageManager();
        boolean has_permission = (
                PackageManager.PERMISSION_GRANTED == pkm.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, getPackageName()) &&
                        PackageManager.PERMISSION_GRANTED == pkm.checkPermission(Manifest.permission.READ_PHONE_STATE, getPackageName())
        );
        if (has_permission) {
            goMain();
        } else {
            SplashActivityPermissionsDispatcher.goMainWithCheck(SplashActivity.this);
        }
    }

    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE})
    void goMain() {
        mSplashPresenter.getThreshold();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SplashActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE})
    void showDenied() {
        showMessage("权限被拒绝");
        finish();
    }

    @OnNeverAskAgain({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE})
    void showNeverAsk() {
        showMessage("请开启权限再使用");
        Uri packageURI = Uri.parse("package:" + getPackageName());
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
        startActivity(intent);
    }

    @Override
    public void showThreshold(ServerThresholdBean bean) {
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
                        boolean isFirstOpen = new SPFHelper(TenApp.getInstance(), "").getBoolean(Constants.FIRST_OPEN, true);
                        if (isFirstOpen) {
                            startActivityNoValue(mContext, WelcomeActivity.class);
                        } else {
                            if (isEmpty) {
                                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                            } else {
                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            }
                        }
                        finish();
                    }
                });
    }
}
