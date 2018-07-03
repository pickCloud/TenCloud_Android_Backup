package com.ten.tencloud.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lxq on 2018/4/24.
 */

public class AppContainerBean implements Parcelable {
    private int app_id;
    private String app_name;
    private String deployment_name;
    private int replica_num;
    private Map<String,String> pod_label;
    private String container_name;
    private String name;
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private List<Port> ports;


    public List<ContainersBean> containers;

    public int getApp_id() {
        return app_id;
    }

    public AppContainerBean setApp_id(int app_id) {
        this.app_id = app_id;
        return this;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getDeployment_name() {
        return deployment_name;
    }

    public void setDeployment_name(String deployment_name) {
        this.deployment_name = deployment_name;
    }

    public int getReplica_num() {
        return replica_num;
    }

    public void setReplica_num(int replica_num) {
        this.replica_num = replica_num;
    }

    public Map<String, String> getPod_label() {
        return pod_label;
    }

    public void setPod_label(Map<String, String> pod_label) {
        this.pod_label = pod_label;
    }

    public String getContainer_name() {
        return container_name;
    }

    public void setContainer_name(String container_name) {
        this.container_name = container_name;
    }

    public List<Port> getPorts() {
        return ports;
    }

    public void setPorts(List<Port> ports) {
        this.ports = ports;
    }

    public static class Port implements Parcelable {
        private String name;
        private String protocol;
        private int containerPort;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProtocol() {
            return protocol;
        }

        public void setProtocol(String protocol) {
            this.protocol = protocol;
        }

        public int getContainerPort() {
            return containerPort;
        }

        public void setContainerPort(int containerPort) {
            this.containerPort = containerPort;
        }

        public Port() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeString(this.protocol);
            dest.writeInt(this.containerPort);
        }

        protected Port(Parcel in) {
            this.name = in.readString();
            this.protocol = in.readString();
            this.containerPort = in.readInt();
        }

        public static final Creator<Port> CREATOR = new Creator<Port>() {
            @Override
            public Port createFromParcel(Parcel source) {
                return new Port(source);
            }

            @Override
            public Port[] newArray(int size) {
                return new Port[size];
            }
        };
    }

    public AppContainerBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.app_id);
        dest.writeString(this.app_name);
        dest.writeString(this.deployment_name);
        dest.writeInt(this.replica_num);
        dest.writeInt(this.pod_label.size());
        for (Map.Entry<String, String> entry : this.pod_label.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
        dest.writeString(this.container_name);
        dest.writeString(this.name);
        dest.writeString(this.image);
        dest.writeTypedList(this.ports);
        dest.writeTypedList(this.containers);
    }

    protected AppContainerBean(Parcel in) {
        this.app_id = in.readInt();
        this.app_name = in.readString();
        this.deployment_name = in.readString();
        this.replica_num = in.readInt();
        int pod_labelSize = in.readInt();
        this.pod_label = new HashMap<String, String>(pod_labelSize);
        for (int i = 0; i < pod_labelSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.pod_label.put(key, value);
        }
        this.container_name = in.readString();
        this.name = in.readString();
        this.image = in.readString();
        this.ports = in.createTypedArrayList(Port.CREATOR);
        this.containers = in.createTypedArrayList(ContainersBean.CREATOR);
    }

    public static final Creator<AppContainerBean> CREATOR = new Creator<AppContainerBean>() {
        @Override
        public AppContainerBean createFromParcel(Parcel source) {
            return new AppContainerBean(source);
        }

        @Override
        public AppContainerBean[] newArray(int size) {
            return new AppContainerBean[size];
        }
    };
}
