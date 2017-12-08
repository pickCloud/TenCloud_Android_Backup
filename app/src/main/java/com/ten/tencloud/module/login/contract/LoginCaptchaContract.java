package com.ten.tencloud.module.login.contract;


import android.content.Context;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.SendSMSBean;

/**
 * Created by lxq on 2017/11/21.
 */

public class LoginCaptchaContract {
    public interface View extends IBaseView {

        /**
         * 验证码超过次数
         */
        void showSmsCodeTimeOut();

        void showSmsCodeSuccess();

        void geeCaptchaSuccess();

        void geeCaptchaFailed();

        void countdowning(String text);

        void countdownStop();

        void countdownStart();

        /**
         * Gee二次验证返回时
         * @param result
         */
        void gt3GetDialogResult(String result);

    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {

        void sendSMSCode(SendSMSBean sendSMSBean);

        void sendSMSCodeByGee(SendSMSBean sendSMSBean);

        void countdown();

        void geeInit(Context context);

        void geeStart(Context context);

        void gt3TestFinish();

        void gt3TestClose();

        void cancelUtils();
    }
}
