package com.ten.tencloud.bean;

import android.support.annotation.NonNull;

/**
 * Created by chenxh@10.com on 2018/3/28.
 */
public class AppBean implements Comparable<AppBean> {

    /**
     * id : 1
     * name : app1
     * description : 测试1
     * status : 0
     * repos_name :
     * repos_ssh_url :
     * repos_https_url :
     * logo_url :
     * image_id : 0
     * lord : 79
     * form : 1
     * create_time : 2018-04-02 10:57:53
     * update_time : 2018-04-02 10:57:53
     */

    private int id;
    private String name;
    private String description;
    private int status;
    private String repos_name;
    private String repos_ssh_url;
    private String repos_https_url;
    private String logo_url;
    private int image_id;
    private int lord;
    private int form;
    private String create_time;
    private String update_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRepos_name() {
        return repos_name;
    }

    public void setRepos_name(String repos_name) {
        this.repos_name = repos_name;
    }

    public String getRepos_ssh_url() {
        return repos_ssh_url;
    }

    public void setRepos_ssh_url(String repos_ssh_url) {
        this.repos_ssh_url = repos_ssh_url;
    }

    public String getRepos_https_url() {
        return repos_https_url;
    }

    public void setRepos_https_url(String repos_https_url) {
        this.repos_https_url = repos_https_url;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public int getLord() {
        return lord;
    }

    public void setLord(int lord) {
        this.lord = lord;
    }

    public int getForm() {
        return form;
    }

    public void setForm(int form) {
        this.form = form;
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

    @Override
    public String toString() {
        return "AppBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", repos_name='" + repos_name + '\'' +
                ", repos_ssh_url='" + repos_ssh_url + '\'' +
                ", repos_https_url='" + repos_https_url + '\'' +
                ", logo_url='" + logo_url + '\'' +
                ", image_id=" + image_id +
                ", lord=" + lord +
                ", form=" + form +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                '}';
    }

    @Override
    public int compareTo(@NonNull AppBean o) {
        return o.getUpdate_time().compareTo(this.getUpdate_time());
    }
}
