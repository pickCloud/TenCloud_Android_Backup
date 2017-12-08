package com.ten.tencloud.base.bean;

/**
 * Created by lxq on 2017/11/20.
 */

public class JesResponse<T> extends BaseResponse {
    private T data;

    public T getData() {
        return data;
    }

    public JesResponse setData(T data) {
        this.data = data;
        return this;
    }
}
