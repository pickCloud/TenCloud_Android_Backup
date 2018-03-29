package com.ten.tencloud.bean;

/**
 * Created by lxq on 2018/3/15.
 */

public class ServerHeatBean {

    /**
     * serverID : 184
     * name : 测试
     * colorType : 4
     * cpuUsageRate : 0
     * memUsageRate : 0.19940000000000002
     * diskUsageRate : 0.0394
     * diskUtilize : 0.7854583758348964
     * netUsageRate : 0/0
     */

    private int serverID;
    private String name;
    private int colorType;
    private float cpuUsageRate;
    private float memUsageRate;
    private float diskUsageRate;
    private float diskUtilize;
    private String netUsageRate;
    private String netDownload;
    private String netUpload;


    public int getServerID() {
        return serverID;
    }

    public void setServerID(int serverID) {
        this.serverID = serverID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColorType() {
        return colorType;
    }

    public void setColorType(int colorType) {
        this.colorType = colorType;
    }

    public float getCpuUsageRate() {
        return cpuUsageRate;
    }

    public void setCpuUsageRate(float cpuUsageRate) {
        this.cpuUsageRate = cpuUsageRate;
    }

    public float getMemUsageRate() {
        return memUsageRate;
    }

    public void setMemUsageRate(float memUsageRate) {
        this.memUsageRate = memUsageRate;
    }

    public float getDiskUsageRate() {
        return diskUsageRate;
    }

    public void setDiskUsageRate(float diskUsageRate) {
        this.diskUsageRate = diskUsageRate;
    }

    public float getDiskUtilize() {
        return diskUtilize;
    }

    public void setDiskUtilize(float diskUtilize) {
        this.diskUtilize = diskUtilize;
    }

    public String getNetUsageRate() {
        return netUsageRate;
    }

    public void setNetUsageRate(String netUsageRate) {
        this.netUsageRate = netUsageRate;
    }

    public String getNetDownload() {
        return netDownload;
    }

    public void setNetDownload(String netDownload) {
        this.netDownload = netDownload;
    }

    public String getNetUpload() {
        return netUpload;
    }

    public void setNetUpload(String netUpload) {
        this.netUpload = netUpload;
    }
}
