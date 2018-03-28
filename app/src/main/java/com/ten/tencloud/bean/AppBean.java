package com.ten.tencloud.bean;

import java.util.ArrayList;

/**
 * Created by chenxh@10.com on 2018/3/28.
 */
public class AppBean {
    private String name;
    private String source;
    private String createDate;
    private String updateDate;
    private int status;
    private ArrayList<String> labels;

    public AppBean(String name, String source, String createDate, String updateDate, int status, ArrayList<String> labels) {
        this.name = name;
        this.source = source;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.status = status;
        this.labels = labels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<String> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<String> labels) {
        this.labels = labels;
    }
}
