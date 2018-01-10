package com.ten.tencloud.bean;

/**
 * Created by lxq on 2018/1/10.
 */

public class MessageBean {

    /**
     * id : 85
     * owner : 54
     * content : 你的企业【泉州bb技术有限公司】被管理员 【阙女士】修改了资料
     * mode : 2
     * sub_mode : 3
     * tip : 21:
     * status : 1
     * create_time : 2017-12-19 15:11:34
     * update_time : 2017-12-20 17:00:36
     */

    private int id;
    private int owner;
    private String content;
    private int mode;
    private int sub_mode;
    private String tip;
    private int status;
    private String create_time;
    private String update_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getSub_mode() {
        return sub_mode;
    }

    public void setSub_mode(int sub_mode) {
        this.sub_mode = sub_mode;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}
