package com.ten.tencloud.bean;

/**
 * Created by lxq on 2018/1/12.
 */

public class JoinComBean {

    /**
     * cid : 1
     * company_name : 十全
     * contact : 13900000000
     * setting : mobile,name
     */

    private int cid;
    private String company_name;
    private String contact;
    private String setting;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }
}
