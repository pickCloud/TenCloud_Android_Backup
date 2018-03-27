package com.ten.tencloud.bean;

/**
 * Created by chenxinhai on 2018/3/26.
 */
public class AppServiceHeaderBean {
    private int count;
    private String desc;

    public AppServiceHeaderBean(int count, String desc) {
        this.count = count;
        this.desc = desc;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
