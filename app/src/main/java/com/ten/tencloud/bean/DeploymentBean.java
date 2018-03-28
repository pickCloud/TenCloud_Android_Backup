package com.ten.tencloud.bean;

import java.util.List;

/**
 * Created by chenxh@10.com on 2018/3/27.
 */
public class DeploymentBean {
    private String name;
    private int status;
    private List<Pod> mPodList;
    private String createDate;
    private String linkApp;

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
}
