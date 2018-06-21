package com.ten.tencloud.bean;

import java.util.List;
import java.util.Map;

public class DeploymentInfoBean {

    public int id;
    public String name;
    public int deployment_id;
    public String readyStatus;
    public String podStatus;
    public int restartStatus;
    public String verbose;
    public String create_time;
    public String update_time;

    //部署的ReplicasSet信息
    public int replicas;
    public int availableReplicas;
    public int readyReplicas;

    public Map<String, String> labels;


}
