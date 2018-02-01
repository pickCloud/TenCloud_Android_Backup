package com.ten.tencloud.module.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.constants.GlobalStatusManager;
import com.ten.tencloud.module.user.ui.CompanyListActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class JoinComTipsActivity extends BaseActivity {

    public static final int TYPE_PASS = 0;
    public static final int TYPE_SUBMIT = 1;

    @BindView(R.id.layout)
    View layout;
    @BindView(R.id.tv_tip)
    TextView mTvTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_join_com_tips);
        initTitleBar(false, "邀请加入");
        GlobalStatusManager.getInstance().registerTask(this);
        int type = getIntent().getIntExtra("type", TYPE_PASS);
        if (type == TYPE_PASS) {
            layout.setVisibility(View.GONE);
            mTvTip.setText("你已经加入了该企业，请勿重复提交。");
        } else if (type == TYPE_SUBMIT) {
            layout.setVisibility(View.VISIBLE);
            mTvTip.setText("你已经提交申请，请等待管理员审核。");
        }
    }

    public void btnOk(View view) {
        TenApp.getInstance().jumpMainActivity();
        GlobalStatusManager.getInstance().clearTask();
    }

    public void myCom(View view) {
        Observable.just("").delay(50, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        GlobalStatusManager.getInstance().clearTask();
                        startActivity(new Intent(JoinComTipsActivity.this, CompanyListActivity.class));
                    }
                });
        TenApp.getInstance().jumpMainActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlobalStatusManager.getInstance().unRegisterTask(this);
    }
}
