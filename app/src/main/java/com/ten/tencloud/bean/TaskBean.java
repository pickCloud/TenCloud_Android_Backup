package com.ten.tencloud.bean;

/**
 * Created by chenxh@10.com on 2018/3/27.
 */
public class TaskBean {

    private String name;
    private int progress;
    private String createDate;
    private String endDate;
    private int status;

    public TaskBean(String name, int progress, String createDate, String endDate, int status) {
        this.name = name;
        this.progress = progress;
        this.createDate = createDate;
        this.endDate = endDate;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
