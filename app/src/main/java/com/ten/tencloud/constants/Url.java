package com.ten.tencloud.constants;


import com.ten.tencloud.BuildConfig;

/**
 * 统一接口目录地址
 * Created by lxq on 2017/11/20.
 */

public class Url {
    public static final String BASE_URL_DEBUG = "http://cd.10.com";
    public static final String BASE_URL_RELEASE = "https://c.10.com";

    public final static String WEBSOCKET_URL_DEBUG = "ws://cd.10.com";
    public final static String WEBSOCKET_URL_RELEASE = "ws://c.10.com";


    public static String BASE_URL;
    public static String BASE_WEBSOCTET_URL;

    static {
        if (!BuildConfig.DEBUG) {
            BASE_URL = BASE_URL_DEBUG;
            BASE_WEBSOCTET_URL = WEBSOCKET_URL_DEBUG;
        } else {
            BASE_URL = BASE_URL_RELEASE;
            BASE_WEBSOCTET_URL = WEBSOCKET_URL_RELEASE;
        }
    }

}
