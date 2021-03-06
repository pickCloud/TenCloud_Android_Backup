package com.ten.tencloud.bean;

/**
 * Created by lxq on 2017/11/23.
 */
public class ServerBean {

    public ServerBean() {

    }

    public ServerBean(String name, String public_ip, String provider) {
        this.name = name;
        this.public_ip = public_ip;
        this.provider = provider;
    }

    /**
     * id : int
     * name : str
     * address : str
     * public_ip : str
     * machine_status : int
     * business_status : int
     * disk : str
     * memory : str
     * cpu : str
     * net : str
     */



    private String id;
    private String name;
    private String address;
    private String public_ip;
    private String machine_status;
    private String business_status;
    private ContentInfoBean disk;
    private ContentInfoBean memory;
    private ContentInfoBean cpu;
    private NetSpeedBean net;
    private String provider;

    private boolean isSelect;


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

    public ContentInfoBean getDisk() {
        return disk;
    }

    public void setDisk(ContentInfoBean disk) {
        this.disk = disk;
    }

    public ContentInfoBean getMemory() {
        return memory;
    }

    public void setMemory(ContentInfoBean memory) {
        this.memory = memory;
    }

    public ContentInfoBean getCpu() {
        return cpu;
    }

    public void setCpu(ContentInfoBean cpu) {
        this.cpu = cpu;
    }

    public NetSpeedBean getNet() {
        return net;
    }

    public void setNet(NetSpeedBean net) {
        this.net = net;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
