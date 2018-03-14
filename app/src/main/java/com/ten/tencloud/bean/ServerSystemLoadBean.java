package com.ten.tencloud.bean;

/**
 * Created by lxq on 2018/3/8.
 */

public class ServerSystemLoadBean {

    /**
     * date : 2018-03-08 14:23:30
     * fifth_minute_load : 0.24
     * five_minute_load : 0.34
     * login_users : 1
     * one_minute_load : 0.14
     * run_time : 6天6小时30分钟
     */

    private String date;
    private float fifth_minute_load;
    private float five_minute_load;
    private int login_users;
    private float one_minute_load;
    private String run_time;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getFifth_minute_load() {
        return fifth_minute_load;
    }

    public void setFifth_minute_load(float fifth_minute_load) {
        this.fifth_minute_load = fifth_minute_load;
    }

    public float getFive_minute_load() {
        return five_minute_load;
    }

    public void setFive_minute_load(float five_minute_load) {
        this.five_minute_load = five_minute_load;
    }

    public int getLogin_users() {
        return login_users;
    }

    public void setLogin_users(int login_users) {
        this.login_users = login_users;
    }

    public float getOne_minute_load() {
        return one_minute_load;
    }

    public void setOne_minute_load(float one_minute_load) {
        this.one_minute_load = one_minute_load;
    }

    public String getRun_time() {
        return run_time;
    }

    public void setRun_time(String run_time) {
        this.run_time = run_time;
    }
}
