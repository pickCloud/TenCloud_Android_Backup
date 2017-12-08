package com.ten.tencloud.bean;

/**
 * Created by lxq on 2017/11/23.
 */
public class ServerBean {
    /**
     * id : int
     * name : str
     * address : str
     * public_ip : str
     * machine_status : int
     * business_status : int
     * disk_content : str
     * memory_content : str
     * cpu_content : str
     * net_content : str
     */

    private String id;
    private String name;
    private String address;
    private String public_ip;
    private String machine_status;
    private String business_status;
    private ContentInfoBean disk_content;
    private ContentInfoBean memory_content;
    private ContentInfoBean cpu_content;
    private NetSpeedBean net_content;
    private String provider;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPublic_ip() {
        return public_ip;
    }

    public void setPublic_ip(String public_ip) {
        this.public_ip = public_ip;
    }

    public String getMachine_status() {
        return machine_status;
    }

    public void setMachine_status(String machine_status) {
        this.machine_status = machine_status;
    }

    public String getBusiness_status() {
        return business_status;
    }

    public void setBusiness_status(String business_status) {
        this.business_status = business_status;
    }

    public ContentInfoBean getDisk_content() {
        return disk_content;
    }

    public void setDisk_content(ContentInfoBean disk_content) {
        this.disk_content = disk_content;
    }

    public ContentInfoBean getMemory_content() {
        return memory_content;
    }

    public void setMemory_content(ContentInfoBean memory_content) {
        this.memory_content = memory_content;
    }

    public ContentInfoBean getCpu_content() {
        return cpu_content;
    }

    public void setCpu_content(ContentInfoBean cpu_content) {
        this.cpu_content = cpu_content;
    }

    public NetSpeedBean getNet_content() {
        return net_content;
    }

    public void setNet_content(NetSpeedBean net_content) {
        this.net_content = net_content;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

}
