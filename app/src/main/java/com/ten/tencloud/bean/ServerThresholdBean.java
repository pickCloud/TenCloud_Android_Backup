package com.ten.tencloud.bean;

/**
 * Created by lxq on 2018/3/16.
 */

public class ServerThresholdBean {

    /**
     * cpu_threshold : 0.8
     * memory_threshold : 0.8
     * disk_threshold : 0.8
     * net_threshold : 0.8
     * block_threshold : 0.8
     */

    private float cpu_threshold;
    private float memory_threshold;
    private float disk_threshold;
    private float net_threshold;
    private float block_threshold;

    public float getCpu_threshold() {
        return cpu_threshold;
    }

    public void setCpu_threshold(float cpu_threshold) {
        this.cpu_threshold = cpu_threshold;
    }

    public float getMemory_threshold() {
        return memory_threshold;
    }

    public void setMemory_threshold(float memory_threshold) {
        this.memory_threshold = memory_threshold;
    }

    public float getDisk_threshold() {
        return disk_threshold;
    }

    public void setDisk_threshold(float disk_threshold) {
        this.disk_threshold = disk_threshold;
    }

    public float getNet_threshold() {
        return net_threshold;
    }

    public void setNet_threshold(float net_threshold) {
        this.net_threshold = net_threshold;
    }

    public float getBlock_threshold() {
        return block_threshold;
    }

    public void setBlock_threshold(float block_threshold) {
        this.block_threshold = block_threshold;
    }
}
