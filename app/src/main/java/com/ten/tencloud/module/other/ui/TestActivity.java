package com.ten.tencloud.module.other.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.model.HttpResultFunc;
import com.ten.tencloud.module.login.ui.JoinComStep1Activity;
import com.ten.tencloud.widget.StatusSelectPopView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class TestActivity extends BaseActivity {

    @BindView(R.id.et_info)
    EditText mEtInfo;
    @BindView(R.id.spv)
    StatusSelectPopView spv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_test);
        initTitleBar(true, "测试页");
        final List<String> data = new ArrayList<>();
        data.add("全部");
        data.add("全部1");
        data.add("全部2");
        data.add("全部3");
        data.add("全部4");
        spv.setOnSelectListener(new StatusSelectPopView.OnSelectListener() {
            @Override
            public void onSelect(int pos) {
                showMessage(data.get(pos));
            }
        });
        spv.initData(data);
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

    public void joinCom(View view) {
        String code = mEtInfo.getText().toString().trim();
        Intent intent = new Intent(this, JoinComStep1Activity.class);
        intent.putExtra("code", code);
        startActivity(intent);
    }
}
