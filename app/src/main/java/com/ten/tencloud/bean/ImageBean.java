package com.ten.tencloud.bean;

/**
 * Created by chenxh@10.com on 2018/3/27.
 */
public class ImageBean {

    private String name;
    private String version;
    private String updateDate;

    public ImageBean(String name, String version, String updateDate) {
        this.name = name;
        this.version = version;
        this.updateDate = updateDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
