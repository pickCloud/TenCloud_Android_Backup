package com.ten.tencloud.bean;

/**
 * 应用概述信息
 * Create by chenxh@10.com on 2018/4/2.
 */
public class AppBrief {
    private int app_num;
    private int deploy_num;
    private int pods_num;
    private int docker_num;
    private int service_num;

    public int getApp_num() {
        return app_num;
    }

    public void setApp_num(int app_num) {
        this.app_num = app_num;
    }

    public int getDeploy_num() {
        return deploy_num;
    }

    public void setDeploy_num(int deploy_num) {
        this.deploy_num = deploy_num;
    }

    public int getPods_num() {
        return pods_num;
    }

    public void setPods_num(int pods_num) {
        this.pods_num = pods_num;
    }

    public int getDocker_num() {
        return docker_num;
    }

    public void setDocker_num(int docker_num) {
        this.docker_num = docker_num;
    }

    public int getService_num() {
        return service_num;
    }

    public void setService_num(int service_num) {
        this.service_num = service_num;
    }

    @Override
    public String toString() {
        return "AppBrief{" +
                "app_num=" + app_num +
                ", deploy_num=" + deploy_num +
                ", pods_num=" + pods_num +
                ", docker_num=" + docker_num +
                ", service_num=" + service_num +
                '}';
    }
}
