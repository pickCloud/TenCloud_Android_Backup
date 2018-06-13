package com.ten.tencloud.bean;

public class DeploymentInfoBean {

    public String id;
    public String name;
    public String deployment_id;
    public String readyStatus;
    public int podStatus;
    public int restartStatus;
    public String verbose;
    public String create_time;
    public String update_time;

    //部署的ReplicasSet信息
    public int replicas;
    public int availableReplicas;
    public int readyReplicas;

}
