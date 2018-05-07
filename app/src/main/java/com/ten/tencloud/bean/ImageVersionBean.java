package com.ten.tencloud.bean;

/**
 * Created by lxq on 2018/5/7.
 */

public class ImageVersionBean {
    private String version;
    private String updateTime;

    public ImageVersionBean() {
    }

    public ImageVersionBean(String version, String updateTime) {
        this.version = version;
        this.updateTime = updateTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
