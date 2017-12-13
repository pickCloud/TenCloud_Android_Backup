package com.ten.tencloud.model;


import android.text.TextUtils;

import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.bean.JesResponse;
import com.ten.tencloud.constants.Constants;

import java.util.Map;

import retrofit2.Response;
import rx.functions.Func1;

/**
 * Created by lxq on 2017/11/20.
 */

public class HttpResultFunc<T> implements Func1<Response<JesResponse<T>>, T> {
    @Override
    public T call(Response<JesResponse<T>> response) {
        if (response.code() == Constants.NET_CODE_RE_LOGIN) {
            throw new JesException("登录过期，请重新登录", Constants.NET_CODE_RE_LOGIN);
        }
        if (response.isSuccessful()) {
            JesResponse<T> jesResponse = response.body();
            if (jesResponse.getStatus() != Constants.NET_CODE_SUCCESS) {
                throw new JesException(jesResponse.getMessage(), jesResponse.getStatus());
            }
            return jesResponse.getData();
        } else if (response.code() == Constants.NET_CODE_NO_MOTHED) {
            throw new JesException("服务器内部错误", Constants.NET_CODE_NO_MOTHED);
        } else {
            JesResponse jesResponse = null;
            try {
                jesResponse = TenApp.getInstance().getGsonInstance()
                        .fromJson(response.errorBody().string(), JesResponse.class);
                if (jesResponse != null && jesResponse.getData() != null) {
                    Map<String, String> data1 = (Map) jesResponse.getData();
                    String token = data1.get("token");
                    if (!TextUtils.isEmpty(token)) {
                        AppBaseCache.getInstance().setToken(token);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            throw new JesException(jesResponse.getMessage(), jesResponse.getStatus());
        }
    }
}
