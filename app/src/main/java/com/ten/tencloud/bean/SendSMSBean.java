package com.ten.tencloud.bean;

/**
 * Created by lxq on 2017/11/28.
 */

public class SendSMSBean {

    private String mobile;
    private String geetest_challenge;
    private String geetest_validate;
    private String geetest_seccode;
    private String auth_code;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGeetest_challenge() {
        return geetest_challenge;
    }

    public void setGeetest_challenge(String geetest_challenge) {
        this.geetest_challenge = geetest_challenge;
    }

    public String getGeetest_validate() {
        return geetest_validate;
    }

    public void setGeetest_validate(String geetest_validate) {
        this.geetest_validate = geetest_validate;
    }

    public String getGeetest_seccode() {
        return geetest_seccode;
    }

    public void setGeetest_seccode(String geetest_seccode) {
        this.geetest_seccode = geetest_seccode;
    }

    public String getAuth_code() {
        return auth_code;
    }

    public void setAuth_code(String auth_code) {
        this.auth_code = auth_code;
    }
}
