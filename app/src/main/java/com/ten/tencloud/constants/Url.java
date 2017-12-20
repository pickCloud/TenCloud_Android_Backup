package com.ten.tencloud.constants;


import com.ten.tencloud.BuildConfig;

/**
 *
 * 统一接口目录地址
 * Created by lxq on 2017/11/20.
 */

public class Url {
//    public static final String BASE_URL_DEBUG = "http://47.94.18.22:18010";
    public static final String BASE_URL_DEBUG = "https://c.10.com";

    public final static String WEBSOCKET_UEL_DEBUG = "ws://c.10.com";

    public static final String BASE_URL_PUBLIC = "https://c.10.com";
//    public static final String BASE_URL_PUBLIC = "https://c.10.com";

    public static String BASE_URL;

    static {
        if (BuildConfig.DEBUG) {
            BASE_URL = BASE_URL_DEBUG;
        } else {
            BASE_URL = BASE_URL_PUBLIC;
        }
    }

}
