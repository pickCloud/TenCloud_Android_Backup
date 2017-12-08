package com.ten.tencloud.bean;

import java.util.List;

/**
 * Created by lxq on 2017/12/5.
 */

public class ServerMonitorBean {
    private List<ContentInfoBean> cpu;
    private List<ContentInfoBean> memory;
    private List<ContentInfoBean> disk;
    private List<NetSpeedBean> net;

    public List<ContentInfoBean> getCpu() {
        return cpu;
    }

    public void setCpu(List<ContentInfoBean> cpu) {
        this.cpu = cpu;
    }

    public List<ContentInfoBean> getMemory() {
        return memory;
    }

    public void setMemory(List<ContentInfoBean> memory) {
        this.memory = memory;
    }

    public List<ContentInfoBean> getDisk() {
        return disk;
    }

    public void setDisk(List<ContentInfoBean> disk) {
        this.disk = disk;
    }

    public List<NetSpeedBean> getNet() {
        return net;
    }

    public void setNet(List<NetSpeedBean> net) {
        this.net = net;
    }
}
