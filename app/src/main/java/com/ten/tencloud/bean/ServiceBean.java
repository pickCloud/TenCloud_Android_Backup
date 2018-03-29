package com.ten.tencloud.bean;

/**
 * Created by chenxh@10.com on 2018/3/27.
 */
public class ServiceBean {
    private String name;
    private String type;
    private String ip;
    private String outIp;
    private String burden;
    private String port;
    private String createDate;
    private int status;

    public ServiceBean(String name, String type, String ip, String outIp, String burden, String port, String createDate, int status) {
        this.name = name;
        this.type = type;
        this.ip = ip;
        this.outIp = outIp;
        this.burden = burden;
        this.port = port;
        this.createDate = createDate;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOutIp() {
        return outIp;
    }

    public void setOutIp(String outIp) {
        this.outIp = outIp;
    }

    public String getBurden() {
        return burden;
    }

    public void setBurden(String burden) {
        this.burden = burden;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
