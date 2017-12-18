package com.ten.tencloud.model;

import android.content.Context;
import android.text.TextUtils;

import com.ten.tencloud.TenApp;
import com.ten.tencloud.bean.LoginInfoBean;
import com.ten.tencloud.bean.User;
import com.ten.tencloud.constants.Constants;

import io.objectbox.Box;

/**
 * 基本参数缓存
 * Created by lxq on 2017/11/20.
 */

public class AppBaseCache {

    private final String TOKEN = "TOKEN";
    private final String CID = "CID";//公司ID

    private static AppBaseCache sAppBaseCache;
    private static SPFHelper spfHelper;

    private String token;
    private int cid;

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
        Box<User> userBox = TenApp.getInstance().getBoxStore().boxFor(User.class);
        User first = userBox.query().build().findFirst();
        if (first != null) {
            userBox.remove(first);
        }
        return sAppBaseCache;
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

    public User getUserInfo() {
        Box<User> userBox = TenApp.getInstance().getBoxStore().boxFor(User.class);
        return userBox.query().build().findFirst();
    }

    public void setUserInfo(User user) {
        Box<User> userBox = TenApp.getInstance().getBoxStore().boxFor(User.class);
        User first = userBox.query().build().findFirst();
        if (first != null) {
            userBox.remove(first);
        }
        user.setId(0);
        userBox.put(user);
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

    public int getCid() {
        return spfHelper.getInt(CID, 0);
    }

    public AppBaseCache setCid(int cid) {
        if (spfHelper.putInt(CID, cid)) {
            this.cid = cid;
        }
        return this;
    }

    public void saveUserInfoWithLogin(LoginInfoBean result) {
        setUserInfo(result.getUser());
        setCid(result.getCid());
        setToken(result.getToken());
    }

}
