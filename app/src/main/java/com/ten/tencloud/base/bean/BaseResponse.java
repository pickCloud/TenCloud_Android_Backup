package com.ten.tencloud.base.bean;

/**
 * Created by lxq on 2017/11/20.
 */

public class BaseResponse {
    protected int status;
    protected String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
