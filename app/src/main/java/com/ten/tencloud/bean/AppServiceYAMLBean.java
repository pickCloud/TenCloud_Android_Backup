package com.ten.tencloud.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Map;

/**
 * 生成Service
 * Created by lxq on 2018/5/15.
 */

public class AppServiceYAMLBean {
    private String service_name;
    private String app_name;
    private int app_id;
    private int service_source;
    private Map<String, String> pod_label;
    private int service_type;
    private String clusterIP;
    private String loadBalancerIP;
    private String provider;
    private List<String> externalIPs;
    private List<Port> ports;

    public String getService_name() {
        return service_name;
    }

    public AppServiceYAMLBean setService_name(String service_name) {
        this.service_name = service_name;
        return this;
    }

    public String getApp_name() {
        return app_name;
    }

    public AppServiceYAMLBean setApp_name(String app_name) {
        this.app_name = app_name;
        return this;
    }

    public int getApp_id() {
        return app_id;
    }

    public AppServiceYAMLBean setApp_id(int app_id) {
        this.app_id = app_id;
        return this;
    }

    public int getService_source() {
        return service_source;
    }

    public AppServiceYAMLBean setService_source(int service_source) {
        this.service_source = service_source;
        return this;
    }

    public Map<String, String> getPod_label() {
        return pod_label;
    }

    public AppServiceYAMLBean setPod_label(Map<String, String> pod_label) {
        this.pod_label = pod_label;
        return this;
    }

    public String getLoadBalancerIP() {
        return loadBalancerIP;
    }

    public AppServiceYAMLBean setLoadBalancerIP(String loadBalancerIP) {
        this.loadBalancerIP = loadBalancerIP;
        return this;
    }

    public String getProvider() {
        return provider;
    }

    public AppServiceYAMLBean setProvider(String provider) {
        this.provider = provider;
        return this;
    }

    public int getService_type() {
        return service_type;
    }

    public AppServiceYAMLBean setService_type(int service_type) {
        this.service_type = service_type;
        return this;
    }

    public String getClusterIP() {
        return clusterIP;
    }

    public AppServiceYAMLBean setClusterIP(String clusterIP) {
        this.clusterIP = clusterIP;
        return this;
    }

    public List<String> getExternalIPs() {
        return externalIPs;
    }

    public AppServiceYAMLBean setExternalIPs(List<String> externalIPs) {
        this.externalIPs = externalIPs;
        return this;
    }

    public List<Port> getPorts() {
        return ports;
    }

    public AppServiceYAMLBean setPorts(List<Port> ports) {
        this.ports = ports;
        return this;
    }

    public static class Port implements Parcelable {
        private String name;
        private String protocol;
        private int port;
        private int targetPort;
        private int nodePort;

        public String getName() {
            return name;
        }

        public Port setName(String name) {
            this.name = name;
            return this;
        }

        public String getProtocol() {
            return protocol;
        }

        public Port setProtocol(String protocol) {
            this.protocol = protocol;
            return this;
        }

        public int getPort() {
            return port;
        }

        public Port setPort(int port) {
            this.port = port;
            return this;
        }

        public int getTargetPort() {
            return targetPort;
        }

        public Port setTargetPort(int targetPort) {
            this.targetPort = targetPort;
            return this;
        }

        public int getNodePort() {
            return nodePort;
        }

        public Port setNodePort(int nodePort) {
            this.nodePort = nodePort;
            return this;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeString(this.protocol);
            dest.writeInt(this.port);
            dest.writeInt(this.targetPort);
            dest.writeInt(this.nodePort);
        }

        public Port() {
        }

        protected Port(Parcel in) {
            this.name = in.readString();
            this.protocol = in.readString();
            this.port = in.readInt();
            this.targetPort = in.readInt();
            this.nodePort = in.readInt();
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

}
