package com.ten.tencloud.module.other.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.model.HttpResultFunc;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class TestActivity extends BaseActivity {

    @BindView(R.id.et_info)
    EditText mEtInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_test);
        initTitleBar(true, "测试页");
    }

    public void logout(View view) {
        TenApp.getInstance().jumpLoginActivity();
    }

    public void delUser(View view) {
        TenApp.getRetrofitClient().getTestApi()
                .delUser()
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        showMessage("删除成功");
                    }
                });
    }

    public void setCodeCount(View view) {
        String count = mEtInfo.getText().toString().trim();
        TenApp.getRetrofitClient().getTestApi()
                .setSMSCount(count)
                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        showMessage("设置成功");
                    }
                });
    }
}
