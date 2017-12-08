package com.ten.tencloud.bean;

/**
 * Created by lxq on 2017/11/23.
 */

public class NetSpeedBean {

    /**
     * recv_speed : 0
     * send_speed : 0
     */

    private String recv_speed;
    private String send_speed;
    private String output;
    private String input;
    private String created_time;

    public String getRecv_speed() {
        return recv_speed;
    }

    public void setRecv_speed(String recv_speed) {
        this.recv_speed = recv_speed;
    }

    public String getSend_speed() {
        return send_speed;
    }

    public void setSend_speed(String send_speed) {
        this.send_speed = send_speed;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }
}
