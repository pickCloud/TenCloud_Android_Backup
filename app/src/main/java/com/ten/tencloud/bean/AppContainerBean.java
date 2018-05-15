package com.ten.tencloud.bean;

import android.os.Parcel;
import android.os.Parcelable;

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
    private String image_name;
    private List<Port> ports;

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

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
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

        public Port() {
        }

        protected Port(Parcel in) {
            this.name = in.readString();
            this.protocol = in.readString();
            this.containerPort = in.readInt();
        }

        public static final Parcelable.Creator<Port> CREATOR = new Parcelable.Creator<Port>() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.container_name);
        dest.writeString(this.image_name);
        dest.writeTypedList(this.ports);
    }

    public AppContainerBean() {
    }

    protected AppContainerBean(Parcel in) {
        this.container_name = in.readString();
        this.image_name = in.readString();
        this.ports = in.createTypedArrayList(Port.CREATOR);
    }

    public static final Parcelable.Creator<AppContainerBean> CREATOR = new Parcelable.Creator<AppContainerBean>() {
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
