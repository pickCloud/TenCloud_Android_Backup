package com.ten.tencloud.constants;

import android.os.Environment;

import java.io.File;

/**
 * Created by lxq on 2017/11/20.
 */

public class Constants {

    public static final String PROJECT_NAME = "TenCloud";

    public static final String FIRST_OPEN = "FirstOpen";

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

    //被踢出
    public static final int NET_CODE_RE_LOGIN_KICK = 10415;

    public static final int NET_CODE_NO_MOTHED = 405;

    public static final int NET_CODE_NOT_FOUND = 404;
    //500
    public static final int NET_CODE_INTERNAL_ERROR = 10500;

    public static final int NET_CODE_TIME_OUT = 100020;

    public static final int NET_CODE_NO_NETWORK = 100021;
    //短信验证码超过次数
    public static final int SMS_TIME_OUT = 10405;
    //非公司员工
    public static final int NET_CODE_NOT_EMPLOYEE = 10003;


    /**
     * 页面RequestCode
     */
    public static final int ACTIVITY_REQUEST_CODE_COMMON1 = 20084;
    public static final int ACTIVITY_REQUEST_CODE_COMMON2 = 20085;
    /**
     * 页面ResultCode
     */
    public static final int ACTIVITY_RESULT_CODE_REFRESH = 20086;

    public static final int ACTIVITY_RESULT_CODE_FINISH = 20087;


    /**
     * 员工状态码
     */
    public static final int EMPLOYEE_STATUS_CODE_NO_PASS = 1;
    public static final int EMPLOYEE_STATUS_CODE_CHECKING = 2;
    public static final int EMPLOYEE_STATUS_CODE_PASS = 3;
    public static final int EMPLOYEE_STATUS_CODE_CREATE = 4;
    public static final int EMPLOYEE_STATUS_CODE_WAITING = 5;

}
