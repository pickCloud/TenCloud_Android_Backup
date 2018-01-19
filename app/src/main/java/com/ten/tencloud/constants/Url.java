package com.ten.tencloud.constants;


import com.ten.tencloud.BuildConfig;

/**
 *
 * 统一接口目录地址
 * Created by lxq on 2017/11/20.
 */

public class Url {
    public static final String BASE_URL_DEBUG = "http://47.94.18.22/";
    public static final String BASE_URL_RELEASE = "https://c.10.com";

    public final static String WEBSOCKET_URL_DEBUG = "ws://47.94.18.22/";
    public final static String WEBSOCKET_URL_RELEASE = "ws://47.94.18.22/";


    public static String BASE_URL;
    public static String BASE_WEBSOCTET_URL;

    static {
        if (BuildConfig.DEBUG) {
            BASE_URL = BASE_URL_DEBUG;
            BASE_WEBSOCTET_URL = WEBSOCKET_URL_DEBUG;
        } else {
            BASE_URL = BASE_URL_RELEASE;
            BASE_WEBSOCTET_URL = WEBSOCKET_URL_RELEASE;
        }
    }

}
