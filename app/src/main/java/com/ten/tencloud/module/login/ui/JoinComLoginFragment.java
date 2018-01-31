package com.ten.tencloud.module.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseFragment;
import com.ten.tencloud.bean.JoinComBean;
import com.ten.tencloud.bean.SendSMSBean;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.module.login.contract.JoinCom1Contract;
import com.ten.tencloud.module.login.contract.LoginCaptchaContract;
import com.ten.tencloud.module.login.contract.LoginContract;
import com.ten.tencloud.module.login.presenter.JoinCom1Presenter;
import com.ten.tencloud.module.login.presenter.LoginCaptchaPresenter;
import com.ten.tencloud.module.login.presenter.LoginPresenter;
import com.ten.tencloud.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lxq on 2018/1/31.
 */

public class JoinComLoginFragment extends BaseFragment
        implements JoinCom1Contract.View, LoginCaptchaContract.View, LoginContract.View {

    @BindView(R.id.tv_company_name)
    TextView mTvCompanyName;
    @BindView(R.id.ll_info)
    View mLlInfo;
    @BindView(R.id.tv_tip)
    TextView mTvTip;

    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.btn_send_code)
    Button mBtnSendCode;
    @BindView(R.id.btn_ok)
    Button mBtnOk;

    private boolean mIsLogin;
    private LoginCaptchaPresenter mLoginCaptchaPresenter;
    private String mMobile;
    private JoinCom1Presenter mJoinCom1Presenter;
    private LoginPresenter mLoginPresenter;
    private String mSetting;
    private String mCode;
    private boolean mIsNeedInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return createView(inflater, container, R.layout.fragment_join_com_login);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mCode = getArguments().getString("code");
        //验证码
        mLoginCaptchaPresenter = new LoginCaptchaPresenter();
        mLoginCaptchaPresenter.attachView(this);
        mLoginCaptchaPresenter.geeInit(mActivity);//初始化
        //邀请信息
        mJoinCom1Presenter = new JoinCom1Presenter();
        mJoinCom1Presenter.attachView(this);
        //登录
        mLoginPresenter = new LoginPresenter();
        mLoginPresenter.attachView(this);

        initView();
    }

    private void initView() {
        //判断是否登入
        mIsLogin = AppBaseCache.getInstance().getToken() != null
                && AppBaseCache.getInstance().getUserInfoNoException() != null;
        mLlInfo.setVisibility(mIsLogin ? View.GONE : View.VISIBLE);
        //获取邀请信息
        mJoinCom1Presenter.getJoinInitialize(mCode);
    }

    public void btnOk(View view) {
        if (mIsLogin) {
            mJoinCom1Presenter.temp();
        } else {
            String phone = mEtPhone.getText().toString();
            String authCode = mEtCode.getText().toString();
            mLoginPresenter.loginByCode(phone, authCode);
        }
    }

    @Override
    public void showInitialize(JoinComBean bean) {
        if (bean == null) {
            mTvTip.setText("邀请链接已过期,请联系管理员重新邀请");
            mLlInfo.setVisibility(View.GONE);
            mBtnOk.setEnabled(false);
            return;
        }
        mTvCompanyName.setText(bean.getCompany_name());
        mSetting = bean.getSetting();
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
        mLoginCaptchaPresenter.geeStart(mActivity);
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
    public void loginSuccess() {
        //不需要填写密码完善信息
        mIsNeedInfo = false;
        //触发待加入状态
        mJoinCom1Presenter.joinWaiting(mCode);
    }

    @Override
    public void joinWaiting() {
        Intent intent = new Intent(mActivity, JoinComStep2Activity.class);
        intent.putExtra("code", mCode);
        intent.putExtra("setting", mSetting);
        intent.putExtra("isNeedInfo", mIsNeedInfo);
        startActivity(intent);
    }

    @Override
    public void showEmployeeStatus(int status) {

    }

    @Override
    public void unregistered() {
        //需要填写密码
        mIsNeedInfo = true;
        mJoinCom1Presenter.joinWaiting(mCode);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLoginPresenter.detachView();
        mJoinCom1Presenter.detachView();
        mLoginCaptchaPresenter.cancelUtils();
        mLoginCaptchaPresenter.detachView();
    }
}
