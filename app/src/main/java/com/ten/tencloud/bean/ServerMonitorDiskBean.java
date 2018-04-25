package com.ten.tencloud.bean;

/**
 * Created by lxq on 2018/4/25.
 */

public class ServerMonitorDiskBean {
    private String diskName;
    private String mountPoint;
    private String size;
    private String usedSize;
    private String freeSize;
    private String usedPercent;

    public ServerMonitorDiskBean() {
    }

    public ServerMonitorDiskBean(String diskName, String mountPoint, String size, String usedSize, String freeSize, String usedPercent) {
        this.diskName = diskName;
        this.mountPoint = mountPoint;
        this.size = size;
        this.usedSize = usedSize;
        this.freeSize = freeSize;
        this.usedPercent = usedPercent;
    }

    public String getDiskName() {
        return diskName;
    }

    public void setDiskName(String diskName) {
        this.diskName = diskName;
    }

    public String getMountPoint() {
        return mountPoint;
    }

    public void setMountPoint(String mountPoint) {
        this.mountPoint = mountPoint;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUsedSize() {
        return usedSize;
    }

    public void setUsedSize(String usedSize) {
        this.usedSize = usedSize;
    }

    public String getFreeSize() {
        return freeSize;
    }

    public void setFreeSize(String freeSize) {
        this.freeSize = freeSize;
    }

    public String getUsedPercent() {
        return usedPercent;
    }

    public void setUsedPercent(String usedPercent) {
        this.usedPercent = usedPercent;
    }
}
