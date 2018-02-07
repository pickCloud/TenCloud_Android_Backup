package com.ten.tencloud.module.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.constants.GlobalStatusManager;
import com.ten.tencloud.module.other.ui.WelcomeActivity;
import com.ten.tencloud.module.user.ui.CompanyListActivity;

import java.util.concurrent.TimeUnit;

import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class JoinComStep3Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_join_com_step3);
        initTitleBar(false, "完善资料");
        GlobalStatusManager.getInstance().registerTask(this);
    }

    public void btnOk(View view) {
//        TenApp.getInstance().jumpMainActivity();
        GlobalStatusManager.getInstance().clearTask();
        startActivityNoValue(this, WelcomeActivity.class);
    }

    @OnClick({R.id.tv_company})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_company:
                Observable.just("").delay(50, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                startActivity(new Intent(JoinComStep3Activity.this, CompanyListActivity.class));
                                GlobalStatusManager.getInstance().clearTask();
                            }
                        });
                TenApp.getInstance().jumpMainActivity();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlobalStatusManager.getInstance().unRegisterTask(this);
    }
}
