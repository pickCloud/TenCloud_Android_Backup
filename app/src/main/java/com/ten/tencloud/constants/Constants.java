package com.ten.tencloud.constants;

import android.os.Environment;

import java.io.File;

/**
 * Created by lxq on 2017/11/20.
 */

public class Constants {

    public static final String PROJECT_NAME = "TenCloud";

    /**
     * 路径
     */
    public static final String BASE_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + PROJECT_NAME + File.separator;


    /**
     * 缓存根路径
     */
    public static final String CACHE_PATH = BASE_PATH + File.separator + "cache" + File.separator;
    /**
     * 网络缓存路径
     */
    public static final String NET_CATCH_DIR = CACHE_PATH + File.separator + "net" + File.separator + ".netcatch";

    /**
     * 数字常量把值包含在名字之中
     */
    public static final int NET_TIMEOUT_30 = 30;
    public static final int NET_TIMEOUT_60 = 60;
    public static final int NET_TIMEOUT_120 = 120;
    public static final int NET_TIMEOUT_600 = 600;

    /**
     * 网络缓存大小
     */
    public static final int NET_CATCH_SIZE_52428800 = 52428800;

    /**
     * 常量
     */
    public static final String TOKEN = "token";

    /**
     * 跳转到登录页
     */
    public static final String LOGON_ACTION = "TENCLOUD_LOGIN_ACTION";

    /**
     * 跳转到主页
     */
    public static final String MAIN_ACTION = "TENCLOUD_MAIN_ACTION";

    public static final String SPF_NAME_USER = "UserInfo";
    public static final String SPF_NAME_COMMON = PROJECT_NAME;

    /**
     * 网络状态码
     */
    public static final int NET_CODE_SUCCESS = 0;
    //重新登录
    public static final int NET_CODE_RE_LOGIN = 403;

    public static final int NET_CODE_NO_MOTHED = 405;
    //500
    public static final int NET_CODE_INTERNAL_ERROR = 10500;
    //短信验证码超过次数
    public static final int SMS_TIME_OUT = 10405;


}
