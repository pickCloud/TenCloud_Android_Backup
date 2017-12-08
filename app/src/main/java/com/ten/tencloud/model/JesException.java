package com.ten.tencloud.model;

/**
 * Created by lxq on 2017/11/20.
 */

public class JesException extends RuntimeException {
    private int code;

    public JesException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
