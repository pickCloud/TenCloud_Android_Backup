package com.ten.tencloud.module.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.SendSMSBean;
import com.ten.tencloud.model.SPFHelper;
import com.ten.tencloud.module.login.contract.LoginCaptchaContract;
import com.ten.tencloud.module.login.contract.LoginContract;
import com.ten.tencloud.module.login.presenter.LoginCaptchaPresenter;
import com.ten.tencloud.module.login.presenter.LoginPresenter;
import com.ten.tencloud.module.main.ui.MainActivity;
import com.ten.tencloud.utils.StatusBarUtils;
import com.ten.tencloud.utils.Utils;
import com.ten.tencloud.widget.dialog.LoginTipsDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginContract.View, LoginCaptchaContract.View {

    /**
     * 登陆方式
     */
    private static final int LOGIN_STATE_PASSWORD = 1;
    private static final int LOGIN_STATE_CODE = 2;


    /**
     * 记录当前登录方式
     */
    private int mLoginState;

    @BindView(R.id.tv_btn_password)
    TextView mTvBtnPassword;
    @BindView(R.id.tv_btn_Code)
    TextView mTvBtnCode;
    @BindView(R.id.ll_password)
    LinearLayout mLLPassword;
    @BindView(R.id.ll_code)
    LinearLayout mLLCode;
    @BindView(R.id.btn_send_code)
    Button mBtnSendCode;
    @BindView(R.id.et_phone_pw)
    EditText mEtPhonePw;
    @BindView(R.id.et_phone_code)
    EditText mEtPhoneCode;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.et_code)
    EditText mEtCode;

    private LoginPresenter mLoginPresenter;

    /**
     * 极验证相关
     */
    private String mMobile;
    private LoginCaptchaPresenter mLoginCaptchaPresenter;
    private LoginTipsDialog mLoginTipsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_login);
        hideToolBar();
        StatusBarUtils.setTransparent(this);
        mMobile = new SPFHelper(this, "").getString("loginName", "");
        mEtPhonePw.setText(mMobile);
        mLoginPresenter = new LoginPresenter();
        mLoginCaptchaPresenter = new LoginCaptchaPresenter();
        mLoginPresenter.attachView(this);
        mLoginCaptchaPresenter.attachView(this);
        initView();
    }

    private void initView() {
        changeLoginTab(true);
        mLoginCaptchaPresenter.geeInit(this);
        int type = getIntent().getIntExtra("type", 0);
        String msg = getIntent().getStringExtra("msg");
        if (type == 1) {//被踢
            showMsgDialog(msg);
        }
    }

    private void showMsgDialog(String msg) {
        if (mLoginTipsDialog == null) {
            mLoginTipsDialog = new LoginTipsDialog(this);
        }
        if (!mLoginTipsDialog.isShowing()) {
            mLoginTipsDialog.show();
        }
        mLoginTipsDialog.setMsg(msg);
    }

    /**
     * 切换登录模式
     *
     * @param isPassword
     */
    private void changeLoginTab(boolean isPassword) {
        if (isPassword) {
            if (mLoginState == LOGIN_STATE_PASSWORD) {
                return;
            }
            mTvBtnPassword.setSelected(true);
            mTvBtnCode.setSelected(false);
            mLLPassword.setVisibility(View.VISIBLE);
            mLLCode.setVisibility(View.INVISIBLE);
            mLoginState = LOGIN_STATE_PASSWORD;
            mEtPhonePw.setText(mEtPhoneCode.getText());
            mEtPhonePw.setSelection(mEtPhoneCode.getText().length());
        } else {
            if (mLoginState == LOGIN_STATE_CODE) {
                return;
            }
            mTvBtnPassword.setSelected(false);
            mTvBtnCode.setSelected(true);
            mLLPassword.setVisibility(View.INVISIBLE);
            mLLCode.setVisibility(View.VISIBLE);
            mLoginState = LOGIN_STATE_CODE;
            mEtPhoneCode.setText(mEtPhonePw.getText());
            mEtPhoneCode.setSelection(mEtPhonePw.getText().length());
        }
    }

    @OnClick({R.id.tv_btn_password, R.id.tv_btn_Code,
            R.id.btn_send_code, R.id.tv_btn_register, R.id.tv_btn_forget})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_btn_password:
                changeLoginTab(true);
                break;
            case R.id.tv_btn_Code:
                changeLoginTab(false);
                break;
            case R.id.btn_send_code:
                SendSMSBean sendSMSBean = new SendSMSBean();
                mMobile = mEtPhoneCode.getText().toString().trim();
                if (Utils.isMobile(mMobile)) {
                    sendSMSBean.setMobile(mMobile);
                    mLoginCaptchaPresenter.sendSMSCode(sendSMSBean);
                } else {
                    showMessage("请输入正确的手机号");
                }
                break;
            case R.id.tv_btn_register:
                startActivityNoValue(this, RegisterActivity.class);
                break;
            case R.id.tv_btn_forget:
                startActivityNoValue(this, ForgetStep01Activity.class);
                break;
        }
    }

    /**
     * 发送验证码
     */
    private void sendCode() {
        mLoginCaptchaPresenter.countdown();
    }

    @Override
    public void loginSuccess() {
        showToastMessage("登录成功");
        startActivityNoValue(this, MainActivity.class);
        finish();
    }

    @Override
    public void unregistered() {
        Intent intent = new Intent(this, PerfectUserActivity.class);
        intent.putExtra("phone", mMobile);
        startActivity(intent);
    }

    @Override
    public void showSmsCodeTimeOut() {
        mLoginCaptchaPresenter.geeStart(this);
    }

    @Override
    public void showSmsCodeSuccess() {
        sendCode();
    }

    @Override
    public void geeCaptchaSuccess() {
        mLoginCaptchaPresenter.gt3TestFinish();
        sendCode();
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

    public void login(View view) {
        hideKeyboard();
        if (mLoginState == LOGIN_STATE_PASSWORD) {
            String phone = mEtPhonePw.getText().toString();
            String password = mEtPassword.getText().toString();
            mLoginPresenter.loginByPassword(phone, password);
        }
        if (mLoginState == LOGIN_STATE_CODE) {
            String phone = mEtPhoneCode.getText().toString();
            String code = mEtCode.getText().toString();
            mLoginPresenter.loginByCode(phone, code);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginCaptchaPresenter.cancelUtils();
        mLoginPresenter.detachView();
        mLoginCaptchaPresenter.detachView();
    }
}
