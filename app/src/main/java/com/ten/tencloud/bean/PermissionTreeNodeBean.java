package com.ten.tencloud.bean;

import java.util.List;

/**
 * Created by lxq on 2017/12/26.
 */

public class PermissionTreeNodeBean {

    private String name;
    private String filename;
    private int id;
    private int group;
    private List<PermissionTreeNodeBean> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public List<PermissionTreeNodeBean> getData() {
        return data;
    }

    public void setData(List<PermissionTreeNodeBean> data) {
        this.data = data;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
