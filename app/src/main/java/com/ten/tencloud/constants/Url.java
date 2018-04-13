package com.ten.tencloud.constants;


import com.ten.tencloud.BuildConfig;

/**
 * 统一接口目录地址
 * Created by lxq on 2017/11/20.
 */

public class Url {

    private static boolean isTest = false;

    public static final String BASE_URL_DEBUG = "http://47.75.159.100";
//    public static final String BASE_URL_DEBUG = "http://cd.10.com";
//    public static final String BASE_URL_RELEASE = "https://c.10.com";
    public static final String BASE_URL_RELEASE = "http://47.75.159.100";
    public static final String BASE_URL_TEST = "http://ct.10.com";

    public final static String WEBSOCKET_URL_DEBUG = "ws://47.75.159.100";
//    public final static String WEBSOCKET_URL_DEBUG = "ws://cd.10.com";
//    public final static String WEBSOCKET_URL_RELEASE = "ws://c.10.com";
    public final static String WEBSOCKET_URL_RELEASE = "ws://47.75.159.100";
    public final static String WEBSOCKET_URL_TEST = "ws://ct.10.com";

    public static String BASE_URL;
    public static String BASE_WEBSOCTET_URL;

    public static String GITHUB_OAUTH_URL="https://github.com/login/oauth/authorize?client_id=aeeadb59210d1576525f";

    static {
        if (BuildConfig.DEBUG) {
            BASE_URL = isTest ? BASE_URL_TEST : BASE_URL_DEBUG;
            BASE_WEBSOCTET_URL = isTest ? WEBSOCKET_URL_TEST : WEBSOCKET_URL_DEBUG;
        } else {
            BASE_URL = BASE_URL_RELEASE;
            BASE_WEBSOCTET_URL = WEBSOCKET_URL_RELEASE;
        }
    }

}
