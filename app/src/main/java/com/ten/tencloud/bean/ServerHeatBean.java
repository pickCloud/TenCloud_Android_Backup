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
     * diskIO : 0.7854583758348964
     * networkUsage : 0/0
     */

    private int serverID;
    private String name;
    private int colorType;
    private float cpuUsageRate;
    private float memUsageRate;
    private float diskUsageRate;
    private String diskIO;
    private String networkUsage;

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

    public String getDiskIO() {
        return diskIO;
    }

    public void setDiskIO(String diskIO) {
        this.diskIO = diskIO;
    }

    public String getNetworkUsage() {
        return networkUsage;
    }

    public void setNetworkUsage(String networkUsage) {
        this.networkUsage = networkUsage;
    }
}
