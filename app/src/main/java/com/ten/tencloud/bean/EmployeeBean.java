package com.ten.tencloud.bean;

/**
 * Created by lxq on 2017/12/28.
 */

public class EmployeeBean {

    /**
     * update_time : 2017-12-04 10:57:21
     * name : 米建立
     * mobile : 13720893640
     * create_time : 2017-11-17 11:26:00
     * id : 14
     * status : 1
     * uid : 8
     * is_admin : 1
     */

    private String update_time;
    private String name;
    private String mobile;
    private String create_time;
    private int id;
    private int status;
    private int uid;
    private int is_admin; //1 是 0 否

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(int is_admin) {
        this.is_admin = is_admin;
    }
}
