package com.ten.tencloud.model.retrofit;


import com.google.gson.Gson;
import com.ten.tencloud.base.bean.BaseResponse;
import com.ten.tencloud.base.bean.JesResponse;
import com.ten.tencloud.constants.Constants;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Gson解析器 根据后台返回结果是否为成功
 * Created by lxq on 2017/11/20.
 */

public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;


    public GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {

        String response = value.string();
        //处理不正常返回结果的情况
        BaseResponse httpResult = gson.fromJson(response, BaseResponse.class);
        if (httpResult.getStatus() == Constants.NET_CODE_SUCCESS) {
            return gson.fromJson(response, type);
        } else {
            //抛一个自定义ResultException 传入失败时候的状态码，和信息
            JesResponse jesResponse = new JesResponse();
            jesResponse.setMessage(httpResult.getMessage());
            jesResponse.setStatus(httpResult.getStatus());
            return (T) jesResponse;
        }
    }
}
