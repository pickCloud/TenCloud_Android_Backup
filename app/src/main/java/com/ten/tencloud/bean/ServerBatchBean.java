package com.ten.tencloud.bean;

/**
 * Created by lxq on 2018/4/3.
 */

public class ServerBatchBean {

    /**
     * is_add : true
     * instance_id : i-uf6an9vwo40c5pgx0nie
     * public_ip : 139.196.88.54
     * inner_ip :
     * net_type : vpc
     * region_id : cn-shanghai
     */

    private boolean is_add;
    private String instance_id;
    private String public_ip;
    private String inner_ip;
    private String net_type;
    private String region_id;
    private String provider;
    private int cloud_type;
    private boolean isSelect;

    public boolean isIs_add() {
        return is_add;
    }

    public void setIs_add(boolean is_add) {
        this.is_add = is_add;
    }

    public String getInstance_id() {
        return instance_id;
    }

    public void setInstance_id(String instance_id) {
        this.instance_id = instance_id;
    }

    public String getPublic_ip() {
        return public_ip;
    }

    public void setPublic_ip(String public_ip) {
        this.public_ip = public_ip;
    }

    public String getInner_ip() {
        return inner_ip;
    }

    public void setInner_ip(String inner_ip) {
        this.inner_ip = inner_ip;
    }

    public String getNet_type() {
        return net_type;
    }

    public void setNet_type(String net_type) {
        this.net_type = net_type;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public int getCloud_type() {
        return cloud_type;
    }

    public void setCloud_type(int cloud_type) {
        this.cloud_type = cloud_type;
    }
}
