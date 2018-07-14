package com.ten.tencloud.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by chenxh@10.com on 2018/3/27.
 */
public class ServiceBean implements Serializable{
    private String name;
    private String app_name;
    private int type;
    private String ip;
    private int id;
    private String outIp;
    private String burden;
    private String port;
    private String createDate;
    private int status;

    private int app_id;
//    private int type;
    private int state;
    private int source;
    private Map<String, Object> endpoint;
    private Map<String, Object> labels;
    private String yaml;
    private String log;
    private String verbose;
    private int form;
    private String clusterIP;
    private String externalIPs;
    private String create_time;
    private List<String> access;

    private List<RulesBean> rules;
    private Map<String, Object> backend;
    private Map<String, Object> controller;

    private List<SubsetBean> subsets;

    public ServiceBean(String name, int type, String ip, String outIp, String burden, String port, String createDate, int status) {
        this.name = name;
        this.type = type;
        this.ip = ip;
        this.outIp = outIp;
        this.burden = burden;
        this.port = port;
        this.createDate = createDate;
        this.status = status;
    }

    public ServiceBean(){

    }

    public List<SubsetBean> getSubsets() {
        return subsets;
    }

    public void setSubsets(List<SubsetBean> subsets) {
        this.subsets = subsets;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public List<RulesBean> getRules() {
        return rules;
    }

    public void setRules(List<RulesBean> rules) {
        this.rules = rules;
    }

    public Map<String, Object> getBackend() {
        return backend;
    }

    public void setBackend(Map<String, Object> backend) {
        this.backend = backend;
    }

    public Map<String, Object> getController() {
        return controller;
    }

    public void setController(Map<String, Object> controller) {
        this.controller = controller;
    }

    public List<String> getAccess() {
        return access;
    }

    public void setAccess(List<String> access) {
        this.access = access;
    }

    public Map<String, Object> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, Object> labels) {
        this.labels = labels;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getClusterIP() {
        return clusterIP;
    }

    public void setClusterIP(String clusterIP) {
        this.clusterIP = clusterIP;
    }

    public String getExternalIPs() {
        return externalIPs;
    }

    public void setExternalIPs(String externalIPs) {
        this.externalIPs = externalIPs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getApp_id() {
        return app_id;
    }

    public void setApp_id(int app_id) {
        this.app_id = app_id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public Map<String, Object> getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(Map<String, Object> endpoint) {
        this.endpoint = endpoint;
    }

    public String getYaml() {
        return yaml;
    }

    public void setYaml(String yaml) {
        this.yaml = yaml;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getVerbose() {
        return verbose;
    }

    public void setVerbose(String verbose) {
        this.verbose = verbose;
    }

    public int getForm() {
        return form;
    }

    public void setForm(int form) {
        this.form = form;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOutIp() {
        return outIp;
    }

    public void setOutIp(String outIp) {
        this.outIp = outIp;
    }

    public String getBurden() {
        return burden;
    }

    public void setBurden(String burden) {
        this.burden = burden;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public class SubsetBean{
        private List<K8sNodeBean.ItemsBean.StatusBean.AddressesBean> addresses;
        private List<AppContainerBean.Port> ports;

        public List<K8sNodeBean.ItemsBean.StatusBean.AddressesBean> getAddresses() {
            return addresses;
        }

        public void setAddresses(List<K8sNodeBean.ItemsBean.StatusBean.AddressesBean> addresses) {
            this.addresses = addresses;
        }

        public List<AppContainerBean.Port> getPorts() {
            return ports;
        }

        public void setPorts(List<AppContainerBean.Port> ports) {
            this.ports = ports;
        }
    }
}
