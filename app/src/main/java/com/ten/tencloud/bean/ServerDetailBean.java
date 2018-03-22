package com.ten.tencloud.bean;

import java.util.List;

/**
 * Created by lxq on 2017/11/29.
 */

public class ServerDetailBean {

    /**
     * basic_info : {"id":3,"name":"@TenHub-官网","cluster_id":1,"cluster_name":"阿里云","address":"华南 1 （深圳）","public_ip":"112.74.31.161","machine_status":"运行中","business_status":0,"region_id":"cn-shenzhen","instance_id":"i-wz93448jsb55l91fpbao","created_time":"2017-06-08 10:04:43"}
     * system_info : {"config":{"cpu":2,"memory":2048,"os_name":"CentOS  7.0 64位","os_type":"linux"}}
     * business_info : {"provider":"阿里云","contract":{"create_time":"2016-11-24T06:32Z","expired_time":"2018-01-10T16:00Z","charge_type":"PayByBandwidth"}}
     */

    private BasicInfoBean basic_info;
    private SystemInfoBean system_info;
    private BusinessInfoBean business_info;

    public BasicInfoBean getBasic_info() {
        return basic_info;
    }

    public void setBasic_info(BasicInfoBean basic_info) {
        this.basic_info = basic_info;
    }

    public SystemInfoBean getSystem_info() {
        return system_info;
    }

    public void setSystem_info(SystemInfoBean system_info) {
        this.system_info = system_info;
    }

    public BusinessInfoBean getBusiness_info() {
        return business_info;
    }

    public void setBusiness_info(BusinessInfoBean business_info) {
        this.business_info = business_info;
    }

    public static class BasicInfoBean {
        /**
         * id : 3
         * name : @TenHub-官网
         * cluster_id : 1
         * cluster_name : 阿里云
         * address : 华南 1 （深圳）
         * public_ip : 112.74.31.161
         * machine_status : 运行中
         * business_status : 0
         * region_id : cn-shenzhen
         * instance_id : i-wz93448jsb55l91fpbao
         * created_time : 2017-06-08 10:04:43
         */

        private int id;
        private String name;
        private int cluster_id;
        private String cluster_name;
        private String address;
        private String public_ip;
        private String machine_status;
        private int business_status;
        private String region_id;
        private String instance_id;
        private String created_time;

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

        public int getCluster_id() {
            return cluster_id;
        }

        public void setCluster_id(int cluster_id) {
            this.cluster_id = cluster_id;
        }

        public String getCluster_name() {
            return cluster_name;
        }

        public void setCluster_name(String cluster_name) {
            this.cluster_name = cluster_name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPublic_ip() {
            return public_ip;
        }

        public void setPublic_ip(String public_ip) {
            this.public_ip = public_ip;
        }

        public String getMachine_status() {
            return machine_status;
        }

        public void setMachine_status(String machine_status) {
            this.machine_status = machine_status;
        }

        public int getBusiness_status() {
            return business_status;
        }

        public void setBusiness_status(int business_status) {
            this.business_status = business_status;
        }

        public String getRegion_id() {
            return region_id;
        }

        public void setRegion_id(String region_id) {
            this.region_id = region_id;
        }

        public String getInstance_id() {
            return instance_id;
        }

        public void setInstance_id(String instance_id) {
            this.instance_id = instance_id;
        }

        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }
    }

    public static class SystemInfoBean {
        /**
         * config : {"cpu":2,"memory":2048,"os_name":"CentOS  7.0 64位","os_type":"linux"}
         */

        private ConfigBean config;

        public ConfigBean getConfig() {
            return config;
        }

        public void setConfig(ConfigBean config) {
            this.config = config;
        }

        public static class ConfigBean {
            /**
             * cpu : 2
             * memory : 2048
             * os_name : CentOS  7.0 64位
             * os_type : linux
             * <p>
             * "cpu": 1,
             * "memory": 1024,
             * "os_name": "CentOS  7.4 64位",
             * "os_type": "linux",
             * "security_group_ids": "sg-bp1c7rvm8oqqf6fblu0z",
             * "instance_network_type": "vpc",
             * "internet_max_bandwidth_in": "200",
             * "internet_max_bandwidth_out": "1",
             * "system_disk_id": "",
             * "system_disk_type": "alibase",
             * "system_disk_size": "20G",
             * "image_id": "centos_7_04_64_20G_alibase_201701015.vhd"
             */

            private int cpu;
            private int memory;
            private String os_name;
            private String os_type;
            private String security_group_ids;
            private String instance_network_type;
            private String internet_max_bandwidth_in;
            private String internet_max_bandwidth_out;

            private List<BusinessInfoBean.DiskInfo> disk_info;

            private List<BusinessInfoBean.ImageInfo> image_info;

            public int getCpu() {
                return cpu;
            }

            public void setCpu(int cpu) {
                this.cpu = cpu;
            }

            public int getMemory() {
                return memory;
            }

            public void setMemory(int memory) {
                this.memory = memory;
            }

            public String getOs_name() {
                return os_name;
            }

            public void setOs_name(String os_name) {
                this.os_name = os_name;
            }

            public String getOs_type() {
                return os_type;
            }

            public void setOs_type(String os_type) {
                this.os_type = os_type;
            }

            public String getSecurity_group_ids() {
                return security_group_ids;
            }

            public void setSecurity_group_ids(String security_group_ids) {
                this.security_group_ids = security_group_ids;
            }

            public String getInstance_network_type() {
                return instance_network_type;
            }

            public void setInstance_network_type(String instance_network_type) {
                this.instance_network_type = instance_network_type;
            }

            public String getInternet_max_bandwidth_in() {
                return internet_max_bandwidth_in;
            }

            public void setInternet_max_bandwidth_in(String internet_max_bandwidth_in) {
                this.internet_max_bandwidth_in = internet_max_bandwidth_in;
            }

            public String getInternet_max_bandwidth_out() {
                return internet_max_bandwidth_out;
            }

            public void setInternet_max_bandwidth_out(String internet_max_bandwidth_out) {
                this.internet_max_bandwidth_out = internet_max_bandwidth_out;
            }

            public List<BusinessInfoBean.DiskInfo> getDisk_info() {
                return disk_info;
            }

            public void setDisk_info(List<BusinessInfoBean.DiskInfo> disk_info) {
                this.disk_info = disk_info;
            }

            public List<BusinessInfoBean.ImageInfo> getImage_info() {
                return image_info;
            }

            public void setImage_info(List<BusinessInfoBean.ImageInfo> image_info) {
                this.image_info = image_info;
            }
        }
    }

    public static class BusinessInfoBean {
        /**
         * provider : 阿里云
         * contract : {"create_time":"2016-11-24T06:32Z","expired_time":"2018-01-10T16:00Z","charge_type":"PayByBandwidth"}
         */

        private String provider;
        private ContractBean contract;

        public String getProvider() {
            return provider;
        }

        public void setProvider(String provider) {
            this.provider = provider;
        }

        public ContractBean getContract() {
            return contract;
        }

        public void setContract(ContractBean contract) {
            this.contract = contract;
        }

        public static class ContractBean {
            /**
             * create_time : 2016-11-24T06:32Z
             * expired_time : 2018-01-10T16:00Z
             * charge_type : PayByBandwidth
             */

            private String create_time;
            private String expired_time;
            private String charge_type;

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getExpired_time() {
                return expired_time;
            }

            public void setExpired_time(String expired_time) {
                this.expired_time = expired_time;
            }

            public String getCharge_type() {
                return charge_type;
            }

            public void setCharge_type(String charge_type) {
                this.charge_type = charge_type;
            }
        }

        public static class DiskInfo {

            /**
             * system_disk_id : d-bp1b2jpvwlu2ilh63r9h
             * system_disk_type : cloud_efficiency
             * system_disk_size : 40
             */

            private String system_disk_id;
            private String system_disk_type;
            private String system_disk_size;

            public String getSystem_disk_id() {
                return system_disk_id;
            }

            public void setSystem_disk_id(String system_disk_id) {
                this.system_disk_id = system_disk_id;
            }

            public String getSystem_disk_type() {
                return system_disk_type;
            }

            public void setSystem_disk_type(String system_disk_type) {
                this.system_disk_type = system_disk_type;
            }

            public String getSystem_disk_size() {
                return system_disk_size;
            }

            public void setSystem_disk_size(String system_disk_size) {
                this.system_disk_size = system_disk_size;
            }
        }

        public static class ImageInfo {

            /**
             * image_id : centos_7_04_64_20G_alibase_201701015.vhd
             * image_name : centos_7_04_64_20G_alibase_201701015.vhd
             * image_version :
             */

            private String image_id;
            private String image_name;
            private String image_version;

            public String getImage_id() {
                return image_id;
            }

            public void setImage_id(String image_id) {
                this.image_id = image_id;
            }

            public String getImage_name() {
                return image_name;
            }

            public void setImage_name(String image_name) {
                this.image_name = image_name;
            }

            public String getImage_version() {
                return image_version;
            }

            public void setImage_version(String image_version) {
                this.image_version = image_version;
            }
        }
    }
}
