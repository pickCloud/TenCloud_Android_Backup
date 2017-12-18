package com.ten.tencloud.bean;

/**
 * Created by lxq on 2017/12/18.
 */

public class LoginInfoBean {
    private int cid;
    private User user;
    private String token;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
