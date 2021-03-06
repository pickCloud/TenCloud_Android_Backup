package com.ten.tencloud.model;

import android.content.Context;
import android.text.TextUtils;

import com.ten.tencloud.TenApp;
import com.ten.tencloud.bean.CompanyBean;
import com.ten.tencloud.bean.KeyValue;
import com.ten.tencloud.bean.KeyValue_;
import com.ten.tencloud.bean.LoginInfoBean;
import com.ten.tencloud.bean.ServerThresholdBean;
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
    private final String LOGIN_COMPANY = "LOGIN_COMPANY";//记录登录的公司
    private final String USER_PERMISSION = "USER_PERMISSION";//用户权限

    private final String SERVER_THRESHOLD = "SERVER_THRESHOLD";//服务器临界值

    private static AppBaseCache sAppBaseCache;
    private static SPFHelper spfHelper;

    private String token;
    private int cid;
    private String loginCompany;
    private String userPermission;

    private AppBaseCache(Context context) {
        spfHelper = new SPFHelper(context, Constants.SPF_NAME_USER);
    }

    public static synchronized AppBaseCache getInstance() {
        if (sAppBaseCache == null) {
            synchronized (AppBaseCache.class) {
                sAppBaseCache = new AppBaseCache(TenApp.getInstance());
            }
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
        User user = userBox.query().build().findFirst();
        if (user == null) {
            try {
                TenApp.getInstance().jumpLoginActivity();
                throw new Exception("用户信息丢失，跳转至登录页");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    /**
     * 不包含异常处理
     *
     * @return
     */
    public User getUserInfoNoException() {
        Box<User> userBox = TenApp.getInstance().getBoxStore().boxFor(User.class);
        User user = userBox.query().build().findFirst();
        return user;
    }

    public void setUserInfo(User user) {
        Box<User> userBox = TenApp.getInstance().getBoxStore().boxFor(User.class);
        User first = userBox.query().build().findFirst();
        if (first != null) {
            userBox.remove(first);
        }
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

    public String getUserPermission() {
        if (!TextUtils.isEmpty(userPermission)) {
            return userPermission;
        }
        Box<KeyValue> box = TenApp.getInstance().getBoxStore().boxFor(KeyValue.class);
        KeyValue value = box.query().equal(KeyValue_.key, USER_PERMISSION).build().findFirst();
        if (value == null) {
            return "";
        }
        return value.getValue();
    }

    public void setUserPermission(String userPermission) {
        Box<KeyValue> box = TenApp.getInstance().getBoxStore().boxFor(KeyValue.class);
        KeyValue first = box.query().equal(KeyValue_.key, USER_PERMISSION).build().findFirst();
        if (first != null) {
            box.remove(first);
        }
        box.put(new KeyValue(USER_PERMISSION, userPermission));
        this.userPermission = userPermission;
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

    public void saveSelectCompanyWithLogin(CompanyBean companyBean) {
        String json = TenApp.getInstance().getGsonInstance().toJson(companyBean);
        spfHelper.putString(LOGIN_COMPANY, json);
    }


    public CompanyBean getSelectCompanyWithLogin() {
        String json = spfHelper.getString(LOGIN_COMPANY, "");
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        return TenApp.getInstance().getGsonInstance().fromJson(json, CompanyBean.class);
    }

    public void setIsAdmin(boolean isAdmin) {
        CompanyBean selectCompanyWithLogin = getSelectCompanyWithLogin();
        selectCompanyWithLogin.setIs_admin(isAdmin ? 1 : 0);
        saveSelectCompanyWithLogin(selectCompanyWithLogin);
    }

    public boolean isAdmin() {
        CompanyBean selectCompanyWithLogin = getSelectCompanyWithLogin();
        if (selectCompanyWithLogin != null && selectCompanyWithLogin.getIs_admin() != 0) {
            return true;
        }
        return false;
    }

    //=======服务器临界值
    public void saveServerThreshold(ServerThresholdBean bean) {
        String json = TenApp.getInstance().getGsonInstance().toJson(bean);
        new SPFHelper(TenApp.getInstance(), "")
                .putString(SERVER_THRESHOLD, json);
    }

    public ServerThresholdBean getServerThreshold() {
        String json = new SPFHelper(TenApp.getInstance(), "")
                .getString(SERVER_THRESHOLD, "");
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        return TenApp.getInstance().getGsonInstance().fromJson(json, ServerThresholdBean.class);
    }
}
