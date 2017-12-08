package com.ten.tencloud.bean;

/**
 * 集群
 * Created by lxq on 2017/11/23.
 */

public class ClusterBean {

    /**
     * id : int
     * name : str
     * description : str
     */

    private String id;
    private String name;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
