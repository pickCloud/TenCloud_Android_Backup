package com.ten.tencloud.bean;

import java.util.List;
import java.util.Map;

/**
 * Created by lxq on 2018/4/25.
 */

public class K8sNodeBean {

    private List<ItemsBean> items;

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {

        private MetadataBean metadata;
        private StatusBean status;

        public MetadataBean getMetadata() {
            return metadata;
        }

        public void setMetadata(MetadataBean metadata) {
            this.metadata = metadata;
        }

        public StatusBean getStatus() {
            return status;
        }

        public void setStatus(StatusBean status) {
            this.status = status;
        }

        public static class MetadataBean {
            private String creationTimestamp;
            private Map<String, String> labels;
            private String name;
            private String namespace;
            private String resourceVersion;
            private String selfLink;
            private String uid;

            public String getCreationTimestamp() {
                return creationTimestamp;
            }

            public void setCreationTimestamp(String creationTimestamp) {
                this.creationTimestamp = creationTimestamp;
            }

            public Map<String, String> getLabels() {
                return labels;
            }

            public void setLabels(Map<String, String> labels) {
                this.labels = labels;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNamespace() {
                return namespace;
            }

            public void setNamespace(String namespace) {
                this.namespace = namespace;
            }

            public String getResourceVersion() {
                return resourceVersion;
            }

            public void setResourceVersion(String resourceVersion) {
                this.resourceVersion = resourceVersion;
            }

            public String getSelfLink() {
                return selfLink;
            }

            public void setSelfLink(String selfLink) {
                this.selfLink = selfLink;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }
        }

        public static class StatusBean {

            private CapacityBean allocatable;
            private CapacityBean capacity;
            private NodeInfoBean nodeInfo;
            private List<AddressesBean> addresses;
            private List<ConditionsBean> conditions;

            public CapacityBean getAllocatable() {
                return allocatable;
            }

            public void setAllocatable(CapacityBean allocatable) {
                this.allocatable = allocatable;
            }

            public CapacityBean getCapacity() {
                return capacity;
            }

            public void setCapacity(CapacityBean capacity) {
                this.capacity = capacity;
            }

            public NodeInfoBean getNodeInfo() {
                return nodeInfo;
            }

            public void setNodeInfo(NodeInfoBean nodeInfo) {
                this.nodeInfo = nodeInfo;
            }

            public List<AddressesBean> getAddresses() {
                return addresses;
            }

            public void setAddresses(List<AddressesBean> addresses) {
                this.addresses = addresses;
            }

            public List<ConditionsBean> getConditions() {
                return conditions;
            }

            public void setConditions(List<ConditionsBean> conditions) {
                this.conditions = conditions;
            }

            public static class CapacityBean {
                /**
                 * cpu : 4
                 * ephemeral-storage : 41151808Ki
                 * hugepages-1Gi : 0
                 * hugepages-2Mi : 0
                 * memory : 8010196Ki
                 * pods : 110
                 */

                private String cpu;
                private String memory;
                private String pods;

                public String getCpu() {
                    return cpu;
                }

                public void setCpu(String cpu) {
                    this.cpu = cpu;
                }

                public String getMemory() {
                    return memory;
                }

                public void setMemory(String memory) {
                    this.memory = memory;
                }

                public String getPods() {
                    return pods;
                }

                public void setPods(String pods) {
                    this.pods = pods;
                }
            }

            public static class NodeInfoBean {
                /**
                 * architecture : amd64
                 * bootID : 6f61064f-36de-434c-84a3-d2f1dbbd26c2
                 * containerRuntimeVersion : docker://1.13.1
                 * kernelVersion : 3.10.0-693.2.2.el7.x86_64
                 * kubeProxyVersion : v1.10.1
                 * kubeletVersion : v1.10.1
                 * machineID : f0f31005fb5a436d88e3c6cbf54e25aa
                 * operatingSystem : linux
                 * osImage : CentOS Linux 7 (Core)
                 * systemUUID : BACDD847-600F-489A-BC18-E2CEFDD735E4
                 */

                private String architecture;
                private String bootID;
                private String containerRuntimeVersion;
                private String kernelVersion;
                private String kubeProxyVersion;
                private String kubeletVersion;
                private String machineID;
                private String operatingSystem;
                private String osImage;
                private String systemUUID;

                public String getArchitecture() {
                    return architecture;
                }

                public void setArchitecture(String architecture) {
                    this.architecture = architecture;
                }

                public String getBootID() {
                    return bootID;
                }

                public void setBootID(String bootID) {
                    this.bootID = bootID;
                }

                public String getContainerRuntimeVersion() {
                    return containerRuntimeVersion;
                }

                public void setContainerRuntimeVersion(String containerRuntimeVersion) {
                    this.containerRuntimeVersion = containerRuntimeVersion;
                }

                public String getKernelVersion() {
                    return kernelVersion;
                }

                public void setKernelVersion(String kernelVersion) {
                    this.kernelVersion = kernelVersion;
                }

                public String getKubeProxyVersion() {
                    return kubeProxyVersion;
                }

                public void setKubeProxyVersion(String kubeProxyVersion) {
                    this.kubeProxyVersion = kubeProxyVersion;
                }

                public String getKubeletVersion() {
                    return kubeletVersion;
                }

                public void setKubeletVersion(String kubeletVersion) {
                    this.kubeletVersion = kubeletVersion;
                }

                public String getMachineID() {
                    return machineID;
                }

                public void setMachineID(String machineID) {
                    this.machineID = machineID;
                }

                public String getOperatingSystem() {
                    return operatingSystem;
                }

                public void setOperatingSystem(String operatingSystem) {
                    this.operatingSystem = operatingSystem;
                }

                public String getOsImage() {
                    return osImage;
                }

                public void setOsImage(String osImage) {
                    this.osImage = osImage;
                }

                public String getSystemUUID() {
                    return systemUUID;
                }

                public void setSystemUUID(String systemUUID) {
                    this.systemUUID = systemUUID;
                }
            }

            public static class AddressesBean {
                /**
                 * address : 172.31.59.194
                 * type : InternalIP
                 */

                private String address;
                private String type;
                private String ip;

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public String getType() {
                    return type;
                }

                public String getIp() {
                    return ip;
                }

                public void setIp(String ip) {
                    this.ip = ip;
                }

                public void setType(String type) {
                    this.type = type;
                }
            }

            public static class ConditionsBean {
                /**
                 * lastHeartbeatTime : Apr 26, 2018 10:27:18
                 * lastTransitionTime : Apr 19, 2018 17:24:49
                 * message : kubelet has sufficient disk space available
                 * reason : KubeletHasSufficientDisk
                 * status : False
                 * type : OutOfDisk
                 */

                private String lastHeartbeatTime;
                private String lastTransitionTime;
                private String message;
                private String reason;
                private String status;
                private String type;

                public String getLastHeartbeatTime() {
                    return lastHeartbeatTime;
                }

                public void setLastHeartbeatTime(String lastHeartbeatTime) {
                    this.lastHeartbeatTime = lastHeartbeatTime;
                }

                public String getLastTransitionTime() {
                    return lastTransitionTime;
                }

                public void setLastTransitionTime(String lastTransitionTime) {
                    this.lastTransitionTime = lastTransitionTime;
                }

                public String getMessage() {
                    return message;
                }

                public void setMessage(String message) {
                    this.message = message;
                }

                public String getReason() {
                    return reason;
                }

                public void setReason(String reason) {
                    this.reason = reason;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }
            }
        }
    }
}
