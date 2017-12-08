package com.ten.tencloud.module.login.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.socks.library.KLog;
import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.SendSMSBean;
import com.ten.tencloud.module.login.contract.ForgetContract;
import com.ten.tencloud.module.login.contract.LoginCaptchaContract;
import com.ten.tencloud.module.login.presenter.ForgetPresenter;
import com.ten.tencloud.module.login.presenter.LoginCaptchaPresenter;
import com.ten.tencloud.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetStep01Activity extends BaseActivity implements LoginCaptchaContract.View, ForgetContract.View {

    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.btn_send_code)
    Button mBtnSendCode;
    @BindView(R.id.et_new_pw)
    EditText mEtNewPW;
    @BindView(R.id.et_new_pw_verify)
    EditText mEtNewPWVerify;

    private LoginCaptchaPresenter mLoginCaptchaPresenter;
    private String mMobile;
    private ForgetPresenter mForgetPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_forget_step01);
        initTitleBar(true, "找回密码");
        init();
    }

    private void init() {
        mLoginCaptchaPresenter = new LoginCaptchaPresenter();
        mLoginCaptchaPresenter.attachView(this);
        mLoginCaptchaPresenter.geeInit(this);//初始化
        mForgetPresenter = new ForgetPresenter();
        mForgetPresenter.attachView(this);
    }

    public void retrieve(View view) {
        String phone = mEtPhone.getText().toString().trim();
        String code = mEtCode.getText().toString().trim();
        String newPW = mEtNewPW.getText().toString().trim();
        String newPWVerify = mEtNewPWVerify.getText().toString().trim();
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
        mForgetPresenter.findPassword(phone, newPW, code);
    }

    @OnClick({R.id.btn_send_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_code:
                SendSMSBean sendSMSBean = new SendSMSBean();
                mMobile = mEtPhone.getText().toString().trim();
                if (Utils.isMobile(mMobile)) {
                    sendSMSBean.setMobile(mMobile);
                    mLoginCaptchaPresenter.sendSMSCode(sendSMSBean);
                } else {
                    showMessage(R.string.tips_verify_phone_error);
                }
                break;
        }
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
    public void findSuccess() {
        showMessage("密码修改成功");
        startActivityNoValue(this, LoginActivity.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mForgetPresenter.detachView();
        mLoginCaptchaPresenter.cancelUtils();
        mLoginCaptchaPresenter.detachView();
    }
}
