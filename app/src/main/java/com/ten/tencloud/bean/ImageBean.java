package com.ten.tencloud.bean;

/**
 * Created by chenxh@10.com on 2018/3/27.
 */
public class ImageBean {

    private int id;
    private String name;
    private String version;
    private int type;
    private String url;
    private String description;
    private String labels;
    private String logo_url;
    private String commit;
    private int state;
    private String dockerfile;
    private String repos_name;
    private String repos_url;
    private int app_id;
    private String log;
    private int lord;
    private int form;
    private String create_time;
    private String update_time;
    private String label_name;

    public ImageBean() {
    }

    public ImageBean(String name, String version, String update_time) {
        this.name = name;
        this.version = version;
        this.update_time = update_time;
    }

    public ImageBean(String name, String version, int type, String update_time, String label_name) {
        this.name = name;
        this.version = version;
        this.type = type;
        this.update_time = update_time;
        this.label_name = label_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDockerfile() {
        return dockerfile;
    }

    public void setDockerfile(String dockerfile) {
        this.dockerfile = dockerfile;
    }

    public String getRepos_name() {
        return repos_name;
    }

    public void setRepos_name(String repos_name) {
        this.repos_name = repos_name;
    }

    public String getRepos_url() {
        return repos_url;
    }

    public void setRepos_url(String repos_url) {
        this.repos_url = repos_url;
    }

    public int getApp_id() {
        return app_id;
    }

    public void setApp_id(int app_id) {
        this.app_id = app_id;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
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

    public String getLabel_name() {
        return label_name;
    }

    public void setLabel_name(String label_name) {
        this.label_name = label_name;
    }
}
