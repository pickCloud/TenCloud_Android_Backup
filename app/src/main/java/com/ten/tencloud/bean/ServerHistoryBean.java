package com.ten.tencloud.bean;

/**
 * Created by lxq on 2017/12/8.
 */

public class ServerHistoryBean {
    private long created_time;
    private ContentInfoBean cpu;
    private ContentInfoBean memory;
    private ContentInfoBean disk;
    private NetSpeedBean net;

    public long getCreated_time() {
        return created_time;
    }

    public void setCreated_time(long created_time) {
        this.created_time = created_time;
    }

    public ContentInfoBean getCpu() {
        return cpu;
    }

    public void setCpu(ContentInfoBean cpu) {
        this.cpu = cpu;
    }

    public ContentInfoBean getMemory() {
        return memory;
    }

    public void setMemory(ContentInfoBean memory) {
        this.memory = memory;
    }

    public ContentInfoBean getDisk() {
        return disk;
    }

    public void setDisk(ContentInfoBean disk) {
        this.disk = disk;
    }

    public NetSpeedBean getNet() {
        return net;
    }

    public void setNet(NetSpeedBean net) {
        this.net = net;
    }
}
