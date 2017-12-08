package com.ten.tencloud.bean;

/**
 * Created by lxq on 2017/11/23.
 */

public class ContentInfoBean {

    /**
     * free : 873070592
     * total : 1927516160
     * percent : 15.86
     * available : 1621762048
     */

    private String free;
    private String total;
    private String percent;
    private String available;
    private String created_time;

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }
}
