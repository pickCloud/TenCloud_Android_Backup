package com.ten.tencloud.model;


import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;
import com.ten.tencloud.BuildConfig;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.constants.Url;
import com.ten.tencloud.model.netapi.TenLoginApi;
import com.ten.tencloud.model.netapi.TenServerApi;
import com.ten.tencloud.model.netapi.TenTestApi;
import com.ten.tencloud.model.netapi.TenUserApi;
import com.ten.tencloud.model.retrofit.AddCookiesInterceptor;
import com.ten.tencloud.model.retrofit.ResponseConverterFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * 初始化Retrofit
 * Created by lxq on 2017/11/20.
 */

public class InitRetrofit {

    private final Retrofit client;

    public InitRetrofit() {

        client = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ResponseConverterFactory.create())
                .client(getOkHttpClient())
                .baseUrl(Url.BASE_URL)
                .build();
    }

    public OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder newBuilder = new OkHttpClient().newBuilder();
        newBuilder.interceptors().add(new AddCookiesInterceptor());
        //日志打印
        newBuilder.addInterceptor(new LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .setLevel(Level.BASIC)
                .log(Platform.INFO)
                .request("Request")
                .response("Response")
                .build());
        //设置缓存路径跟大小
        newBuilder.cache(new Cache(new File(Constants.NET_CATCH_DIR), Constants.NET_CATCH_SIZE_52428800));
        newBuilder.connectTimeout(Constants.NET_TIMEOUT_60, TimeUnit.SECONDS);
        newBuilder.readTimeout(Constants.NET_TIMEOUT_60, TimeUnit.SECONDS);
        newBuilder.writeTimeout(Constants.NET_TIMEOUT_120, TimeUnit.SECONDS);
        return newBuilder.build();
    }

    public TenLoginApi getTenLoginApi() {
        return client.create(TenLoginApi.class);
    }

    public TenServerApi getTenServerApi() {
        return client.create(TenServerApi.class);
    }

    public TenUserApi getTenUserApi() {
        return client.create(TenUserApi.class);
    }

    public TenTestApi getTestApi(){
        return client.create(TenTestApi.class);
    }

}
