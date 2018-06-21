package com.ten.tencloud.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenxh@10.com on 2018/3/27.
 */
public class DeploymentBean implements Parcelable {
    private int id;

    public int getApp_id() {
        return app_id;
    }

    public void setApp_id(int app_id) {
        this.app_id = app_id;
    }

    private int app_id;

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
    private String create_time;
    private String update_time;

    private int readyReplicas;
    private int updatedReplicas;
    private int availableReplicas;
    private int type;
    private int server_id;

    private String app_name;
    private String yaml;

    public String getYaml() {
        return yaml;
    }

    public void setYaml(String yaml) {
        this.yaml = yaml;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public int getReadyReplicas() {
        return readyReplicas;
    }

    public void setReadyReplicas(int readyReplicas) {
        this.readyReplicas = readyReplicas;
    }

    public int getUpdatedReplicas() {
        return updatedReplicas;
    }

    public void setUpdatedReplicas(int updatedReplicas) {
        this.updatedReplicas = updatedReplicas;
    }

    public int getAvailableReplicas() {
        return availableReplicas;
    }

    public void setAvailableReplicas(int availableReplicas) {
        this.availableReplicas = availableReplicas;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getServer_id() {
        return server_id;
    }

    public void setServer_id(int server_id) {
        this.server_id = server_id;
    }

    public int getReplicas() {
        return replicas;
    }

    public void setReplicas(int replicas) {
        this.replicas = replicas;
    }

    private int replicas;

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

    public static class Pod implements Parcelable {
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeInt(this.count);
        }

        protected Pod(Parcel in) {
            this.name = in.readString();
            this.count = in.readInt();
        }

        public static final Creator<Pod> CREATOR = new Creator<Pod>() {
            @Override
            public Pod createFromParcel(Parcel source) {
                return new Pod(source);
            }

            @Override
            public Pod[] newArray(int size) {
                return new Pod[size];
            }
        };
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.app_id);
        dest.writeString(this.name);
        dest.writeInt(this.status);
        dest.writeTypedList(this.mPodList);
        dest.writeString(this.createDate);
        dest.writeString(this.linkApp);
        dest.writeString(this.description);
        dest.writeString(this.repos_name);
        dest.writeString(this.epos_ssh_url);
        dest.writeString(this.repos_https_url);
        dest.writeString(this.labels);
        dest.writeString(this.logo_url);
        dest.writeInt(this.image_id);
        dest.writeInt(this.form);
        dest.writeInt(this.lord);
        dest.writeString(this.create_time);
        dest.writeString(this.update_time);
        dest.writeInt(this.readyReplicas);
        dest.writeInt(this.updatedReplicas);
        dest.writeInt(this.availableReplicas);
        dest.writeInt(this.type);
        dest.writeInt(this.server_id);
        dest.writeString(this.app_name);
        dest.writeString(this.yaml);
        dest.writeInt(this.replicas);
    }

    protected DeploymentBean(Parcel in) {
        this.id = in.readInt();
        this.app_id = in.readInt();
        this.name = in.readString();
        this.status = in.readInt();
        this.mPodList = in.createTypedArrayList(Pod.CREATOR);
        this.createDate = in.readString();
        this.linkApp = in.readString();
        this.description = in.readString();
        this.repos_name = in.readString();
        this.epos_ssh_url = in.readString();
        this.repos_https_url = in.readString();
        this.labels = in.readString();
        this.logo_url = in.readString();
        this.image_id = in.readInt();
        this.form = in.readInt();
        this.lord = in.readInt();
        this.create_time = in.readString();
        this.update_time = in.readString();
        this.readyReplicas = in.readInt();
        this.updatedReplicas = in.readInt();
        this.availableReplicas = in.readInt();
        this.type = in.readInt();
        this.server_id = in.readInt();
        this.app_name = in.readString();
        this.yaml = in.readString();
        this.replicas = in.readInt();
    }

    public static final Creator<DeploymentBean> CREATOR = new Creator<DeploymentBean>() {
        @Override
        public DeploymentBean createFromParcel(Parcel source) {
            return new DeploymentBean(source);
        }

        @Override
        public DeploymentBean[] newArray(int size) {
            return new DeploymentBean[size];
        }
    };
}
