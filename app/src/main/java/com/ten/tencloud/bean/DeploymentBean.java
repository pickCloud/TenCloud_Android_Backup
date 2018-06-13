package com.ten.tencloud.bean;

import java.util.List;

/**
 * Created by chenxh@10.com on 2018/3/27.
 */
public class DeploymentBean {
    private int id;

    private String name;
    private int status;
    private List<Pod> mPodList;
    private String createDate;
    private String linkApp;

    private String description;
    private String repos_name;
    private String epos_ssh_url;
    private String repos_https_url;
    private String labels;
    private String logo_url;
    private int image_id;
    private int form;
    private int lord;
    private long create_time;
    private long update_time;

    public List<Pod> getmPodList() {
        return mPodList;
    }

    public void setmPodList(List<Pod> mPodList) {
        this.mPodList = mPodList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRepos_name() {
        return repos_name;
    }

    public void setRepos_name(String repos_name) {
        this.repos_name = repos_name;
    }

    public String getEpos_ssh_url() {
        return epos_ssh_url;
    }

    public void setEpos_ssh_url(String epos_ssh_url) {
        this.epos_ssh_url = epos_ssh_url;
    }

    public String getRepos_https_url() {
        return repos_https_url;
    }

    public void setRepos_https_url(String repos_https_url) {
        this.repos_https_url = repos_https_url;
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

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public int getForm() {
        return form;
    }

    public void setForm(int form) {
        this.form = form;
    }

    public int getLord() {
        return lord;
    }

    public void setLord(int lord) {
        this.lord = lord;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public static class Pod{
        private String name;
        private int count;

        public Pod(String name, int count) {
            this.name = name;
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public DeploymentBean(String name, int status, List<Pod> podList, String createDate, String linkApp) {
        this.name = name;
        this.status = status;
        mPodList = podList;
        this.createDate = createDate;
        this.linkApp = linkApp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Pod> getPodList() {
        return mPodList;
    }

    public void setPodList(List<Pod> podList) {
        mPodList = podList;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLinkApp() {
        return linkApp;
    }

    public void setLinkApp(String linkApp) {
        this.linkApp = linkApp;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
