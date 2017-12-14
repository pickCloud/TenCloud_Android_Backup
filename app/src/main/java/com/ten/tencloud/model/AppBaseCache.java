package com.ten.tencloud.model;

import android.content.Context;
import android.text.TextUtils;

import com.ten.tencloud.TenApp;
import com.ten.tencloud.bean.User;
import com.ten.tencloud.constants.Constants;

/**
 * 基本参数缓存
 * Created by lxq on 2017/11/20.
 */

public class AppBaseCache {

    private final String USER_NAME = "USER_NAME";
    private final String MOBILE = "MOBILE";
    private final String EMAIL = "EMAIL";
    private final String SESSION_ID = "SESSION_ID";
    private final String TOKEN = "TOKEN";
    private final String CID = "CID";//公司ID

    private static AppBaseCache sAppBaseCache;
    private static SPFHelper spfHelper;

    private String userName;
    private String mobile;
    private String eMail;
    private String sessionId;

    private String token;
    private String cid;

    private AppBaseCache(Context context) {
        spfHelper = new SPFHelper(context, Constants.SPF_NAME_USER);
    }

    public static synchronized AppBaseCache getInstance() {
        if (sAppBaseCache == null) {
            sAppBaseCache = new AppBaseCache(TenApp.getInstance());
        }
        return sAppBaseCache;
    }

    /**
     * 清空AppBaseCache
     */
    public AppBaseCache resetAppBaseCache() {
        spfHelper.clearPreference();
        sAppBaseCache = new AppBaseCache(TenApp.getInstance());
        return sAppBaseCache;
    }

    public AppBaseCache setUser(User user) {
        setUserName(user.getName())
                .setEmail(user.getEmail())
                .setCid(user.getCid())
                .setMobile(user.getMobile());
        return this;
    }

    /**
     * 更改preference的操作对象，替换成指定名字的
     *
     * @param context
     * @param preferenceName
     */
    public void choicePreference(Context context, String preferenceName) {
        synchronized (this) {
            spfHelper = new SPFHelper(context, preferenceName);
        }
    }

    public String getUserName() {
        if (TextUtils.isEmpty(userName)) {
            return spfHelper.getString(USER_NAME, null);
        }
        return userName;
    }

    public AppBaseCache setUserName(String userName) {
        if (spfHelper.putString(USER_NAME, userName)) {
            this.userName = userName;
        }
        return this;
    }

    public String getSessionId() {
        if (TextUtils.isEmpty(sessionId)) {
            return spfHelper.getString(SESSION_ID, "");
        }
        return sessionId;
    }

    public AppBaseCache setSessionId(String sessionId) {
        if (spfHelper.putString(SESSION_ID, sessionId)) {
            this.sessionId = sessionId;
        }
        return this;
    }

    public String getToken() {
        if (TextUtils.isEmpty(token)) {
            return spfHelper.getString(TOKEN, "");
        }
        return token;
    }

    public AppBaseCache setToken(String token) {
        if (spfHelper.putString(TOKEN, token)) {
            this.token = token;
        }
        return this;
    }

    public String getCid() {
        if (TextUtils.isEmpty(cid)) {
            return spfHelper.getString(CID, "");
        }
        return cid;
    }

    public AppBaseCache setCid(String cid) {
        if (spfHelper.putString(CID, cid)) {
            this.cid = cid;
        }
        return this;
    }

    public String getMobile() {
        if (TextUtils.isEmpty(mobile)) {
            return spfHelper.getString(MOBILE, null);
        }
        return mobile;
    }

    public AppBaseCache setMobile(String mobile) {
        if (spfHelper.putString(MOBILE, mobile)) {
            this.mobile = mobile;
        }
        return this;
    }

    public String geteMail() {
        if (TextUtils.isEmpty(eMail)) {
            return spfHelper.getString(EMAIL, null);
        }
        return eMail;
    }

    public AppBaseCache setEmail(String eMail) {
        if (spfHelper.putString(EMAIL, eMail)) {
            this.eMail = eMail;
        }
        return this;
    }
}
