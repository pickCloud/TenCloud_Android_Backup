package com.ten.tencloud.module.user.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.SendSMSBean;
import com.ten.tencloud.module.login.contract.LoginCaptchaContract;
import com.ten.tencloud.module.login.presenter.LoginCaptchaPresenter;
import com.ten.tencloud.module.user.contract.SettingChangePhoneContract;
import com.ten.tencloud.module.user.presenter.SettingChangePhonePresenter;
import com.ten.tencloud.utils.Utils;

import butterknife.BindView;

public class SettingChangePhoneActivity extends BaseActivity
        implements LoginCaptchaContract.View, SettingChangePhoneContract.View {

    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.et_new_phone)
    EditText mEtPhone;
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.btn_send_code)
    Button mBtnSendCode;

    private LoginCaptchaPresenter mLoginCaptchaPresenter;
    private String mMobile;
    private SettingChangePhonePresenter mSettingChangePhonePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_setting_change_phone);
        initTitleBar(true, "修改手机号");
        mLoginCaptchaPresenter = new LoginCaptchaPresenter();
        mLoginCaptchaPresenter.attachView(this);
        mLoginCaptchaPresenter.geeInit(this);//初始化
        mSettingChangePhonePresenter = new SettingChangePhonePresenter();
        mSettingChangePhonePresenter.attachView(this);
    }

    public void sendCode(View view) {
        SendSMSBean sendSMSBean = new SendSMSBean();
        mMobile = mEtPhone.getText().toString().trim();
        if (Utils.isMobile(mMobile)) {
            sendSMSBean.setMobile(mMobile);
            mLoginCaptchaPresenter.sendSMSCode(sendSMSBean);
        } else {
            showMessage(R.string.tips_verify_phone_error);
        }
    }

    public void submit(View view) {
        String password = mEtPassword.getText().toString().trim();
        String phone = mEtPhone.getText().toString().trim();
        String code = mEtCode.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            showMessage(R.string.tips_verify_phone_empty);
            return;
        }
        if (!Utils.isMobile(phone)) {
            showMessage(R.string.tips_verify_phone_error);
            return;
        }
        if (TextUtils.isEmpty(code)) {
            showMessage(R.string.tips_verify_code_empty);
            return;
        }
        if (TextUtils.isEmpty(password)) {
            showMessage(R.string.tips_verify_password_empty);
            return;
        }
        if (password.length() < 6) {
            showMessage(R.string.tips_verify_password_length);
            return;
        }
        mSettingChangePhonePresenter.change(phone, password, code);
    }

    @Override
    public void showSmsCodeTimeOut() {
        mLoginCaptchaPresenter.geeStart(this);
    }

    @Override
    public void showSmsCodeSuccess() {
        mLoginCaptchaPresenter.countdown();
    }

    @Override
    public void geeCaptchaSuccess() {
        mLoginCaptchaPresenter.gt3TestFinish();
        mLoginCaptchaPresenter.countdown();
    }

    @Override
    public void geeCaptchaFailed() {
        mLoginCaptchaPresenter.gt3TestClose();
    }

    /**
     * 倒计时相关
     *
     * @param text
     */
    @Override
    public void countdowning(String text) {
        mBtnSendCode.setText(text);
    }

    @Override
    public void countdownStop() {
        mBtnSendCode.setEnabled(true);
        mBtnSendCode.setText("发送验证码");
    }

    @Override
    public void countdownStart() {
        mBtnSendCode.setEnabled(false);
    }

    /**
     * Gee二次验证返回时
     *
     * @param result
     */
    @Override
    public void gt3GetDialogResult(String result) {
        SendSMSBean sendSMSBean = TenApp.getInstance().getGsonInstance().fromJson(result, SendSMSBean.class);
        sendSMSBean.setMobile(mMobile);
        mLoginCaptchaPresenter.sendSMSCodeByGee(sendSMSBean);
    }

    @Override
    public void changeSuccess() {
        showMessage("修改成功");
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginCaptchaPresenter.cancelUtils();
    }
}
