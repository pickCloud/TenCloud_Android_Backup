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
    private int type;
    private String k8s_node;
    private K8sNodeBean k8sNodeBean;
    private int master_server_id;
    private String public_ip;


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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getK8s_node() {
        return k8s_node;
    }

    public void setK8s_node(String k8s_node) {
        this.k8s_node = k8s_node;
    }

    public K8sNodeBean getK8sNodeBean() {
        return k8sNodeBean;
    }

    public void setK8sNodeBean(K8sNodeBean k8sNodeBean) {
        this.k8sNodeBean = k8sNodeBean;
    }

    public int getMaster_server_id() {
        return master_server_id;
    }

    public void setMaster_server_id(int master_server_id) {
        this.master_server_id = master_server_id;
    }

    public String getPublic_ip() {
        return public_ip;
    }

    public void setPublic_ip(String public_ip) {
        this.public_ip = public_ip;
    }
}
