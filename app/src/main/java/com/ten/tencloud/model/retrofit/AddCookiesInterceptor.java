package com.ten.tencloud.model.retrofit;

import android.text.TextUtils;

import com.ten.tencloud.model.AppBaseCache;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 添加token
 * Created by lxq on 2017/11/21.
 */

public class AddCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        String token = AppBaseCache.getInstance().getToken();
        if (!TextUtils.isEmpty(token)) {
            builder.addHeader("Authorization", token);
        }
        return chain.proceed(builder.build());
    }

}
