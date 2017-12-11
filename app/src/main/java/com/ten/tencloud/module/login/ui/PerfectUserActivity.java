package com.ten.tencloud.module.login.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.module.login.contract.PerfectUserContract;
import com.ten.tencloud.module.login.presenter.PerfectUserPresenter;

import butterknife.BindView;

public class PerfectUserActivity extends BaseActivity implements PerfectUserContract.View {

    @BindView(R.id.et_new_pw)
    EditText mEtNewPW;
    @BindView(R.id.et_new_pw_verify)
    EditText mEtNewPWVerify;
    private PerfectUserPresenter mPresenter;
    private String mPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_perfect_user);
        mPhone = getIntent().getStringExtra("phone");
        mPresenter = new PerfectUserPresenter();
        mPresenter.attachView(this);
    }


    public void register(View view) {
        setSuccess();
        String newPW = mEtNewPW.getText().toString().trim();
        String newPWVerify = mEtNewPWVerify.getText().toString().trim();
        if (TextUtils.isEmpty(newPW) || TextUtils.isEmpty(newPWVerify)) {
            showMessage(R.string.tips_verify_password_empty);
            return;
        }
        if (!newPW.equals(newPWVerify)) {
            showMessage(R.string.tips_verify_password_unmatched);
            return;
        }
        if (newPW.length() < 6) {
            showMessage(R.string.tips_verify_password_length);
            return;
        }
        mPresenter.setPassword(newPW);
    }

    @Override
    public void setSuccess() {
        TenApp.getInstance().jumpMainActivity();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
