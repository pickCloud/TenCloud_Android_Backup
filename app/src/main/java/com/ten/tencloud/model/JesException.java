package com.ten.tencloud.model;

/**
 * Created by lxq on 2017/11/20.
 */

public class JesException extends RuntimeException {
    private int code;
    private String json;

    public JesException(String message, int code) {
        this(message, code, null);
    }

    public JesException(String message, int code, String json) {
        super(message);
        this.code = code;
        this.json = json;
    }

    public int getCode() {
        return code;
    }

    public String getJson() {
        return json;
    }

    @Override
    public String toString() {
        return "JesException{" +
                "code=" + code +
                ", json='" + json + '\'' +
                '}';
    }
}
