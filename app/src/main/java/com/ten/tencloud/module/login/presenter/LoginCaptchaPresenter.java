package com.ten.tencloud.module.login.presenter;

import android.content.Context;

import com.geetest.gt3unbindsdk.Bind.GT3GeetestBindListener;
import com.geetest.gt3unbindsdk.Bind.GT3GeetestUtilsBind;
import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.SendSMSBean;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.model.JesException;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.login.contract.LoginCaptchaContract;
import com.ten.tencloud.module.login.model.LoginModel;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;

/**
 * Created by lxq on 2017/11/21.
 */

public class LoginCaptchaPresenter extends BasePresenter<LoginCaptchaContract.View> implements LoginCaptchaContract.Presenter<LoginCaptchaContract.View> {

    private static final int CODE_TIME = 60;

    private LoginModel mModel;
    private GT3GeetestUtilsBind mGt3GeetestUtils;

    private static final String captchaURL = "https://c.10.com/api/user/captcha";
    private static final String validateURL = "https://c.10.com/api/user/sms";
    private GT3GeetestBindListener mGt3GeetestBindListener;

    public LoginCaptchaPresenter() {
        mModel = LoginModel.getInstance();
    }

    @Override
    public void sendSMSCode(SendSMSBean sendSMSBean) {
        mSubscriptions.add(mModel.sendSMS(sendSMSBean).subscribe(
                new JesSubscribe<Object>(mView) {
                    @Override
                    public void _onSuccess(Object o) {
                        mView.showSmsCodeSuccess();
                    }

                    @Override
                    public void _onError(JesException e) {
                        if (e.getCode() == Constants.SMS_TIME_OUT) {
                            mView.showSmsCodeTimeOut();
                        } else {
                            mView.showError(e);
                        }
                    }
                }));
    }

    @Override
    public void sendSMSCodeByGee(SendSMSBean sendSMSBean) {
        mSubscriptions.add(mModel.sendSMS(sendSMSBean).subscribe(
                new JesSubscribe<Object>(mView) {
                    @Override
                    public void _onSuccess(Object o) {
                        mView.geeCaptchaSuccess();
                    }

                    @Override
                    public void _onError(JesException e) {
                        mView.geeCaptchaFailed();
                    }
                }));
    }

    @Override
    public void countdown() {
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(CODE_TIME + 1)
                .map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long aLong) {
                        return CODE_TIME - aLong;
                    }
                })
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.countdownStart();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        mView.countdownStop();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Long aLong) {
                        mView.countdowning(aLong + "S");
                    }
                });
    }

    /**
     * 初始化极验证
     *
     * @param context
     */
    @Override
    public void geeInit(Context context) {
        if (mGt3GeetestUtils != null) {
            return;
        }
        mGt3GeetestUtils = new GT3GeetestUtilsBind(context);
        mGt3GeetestBindListener = new GT3GeetestBindListener() {
            @Override
            public boolean gt3SetIsCustom() {
                return true;
            }

            @Override
            public void gt3GetDialogResult(boolean b, String result) {
                if (b) {
                    mView.gt3GetDialogResult(result);
                }
            }

            @Override
            public void gt3DialogOnError(String error) {
                mGt3GeetestUtils.cancelAllTask();
            }
        };
    }

    /**
     * 触发极验证
     *
     * @param context
     */
    @Override
    public void geeStart(Context context) {
        mGt3GeetestUtils.getGeetest(context, captchaURL, validateURL, null, mGt3GeetestBindListener);
        //设置是否可以点击屏幕边缘关闭验证码
        mGt3GeetestUtils.setDialogTouch(true);
    }

    /**
     * 完成
     */
    @Override
    public void gt3TestFinish() {
        mGt3GeetestUtils.gt3TestFinish();
    }

    /**
     * 关闭
     */
    @Override
    public void gt3TestClose() {
        mGt3GeetestUtils.gt3TestClose();
    }

    /**
     * 清空
     */
    @Override
    public void cancelUtils() {
        mGt3GeetestUtils.cancelUtils();
        mGt3GeetestUtils = null;
    }


}
