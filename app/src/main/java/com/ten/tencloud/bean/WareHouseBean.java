package com.ten.tencloud.bean;

/**
 * Created by chenxinhai on 2018/3/27.
 */
public class WareHouseBean {

    private String name;
    private String url;

    public WareHouseBean(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}


