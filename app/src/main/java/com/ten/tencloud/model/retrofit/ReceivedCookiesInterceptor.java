package com.ten.tencloud.model.retrofit;


import com.ten.tencloud.TenApp;
import com.ten.tencloud.model.AppBaseCache;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 无用 已去除session
 * Created by lxq on 2017/11/20.
 */

public class ReceivedCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Response originalResponse = chain.proceed(chain.request());
        //这里获取请求返回的cookie
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            List<String> cookies = originalResponse.headers("Set-Cookie");
            String sessionId = cookies.get(0);
            if (!sessionId.equals(AppBaseCache.getInstance().getSessionId())) {
                AppBaseCache.getInstance().setSessionId(sessionId);
                TenApp.resetRetrofitClient();//Session发生变化，重建Retrofit
            }
        }

        return originalResponse;
    }
}
