package com.ten.tencloud.bean;

import java.util.List;

/**
 * Created by lxq on 2017/11/23.
 */

public class ClusterInfoBean {

    /**
     * basic_info : {"id":"int","name":"str","description":"str","update_time":"str"}
     * server_list : [{"id":"int","name":"str","address":"str","public_ip":"str","machine_status":"int","business_status":"int","disk_content":"str","memory_content":"str","cpu_content":"str","net_content":"str"}]
     */

    private List<BasicInfoBean> basic_info;
    private List<ServerBean> server_list;

    public List<BasicInfoBean> getBasic_info() {
        return basic_info;
    }

    public void setBasic_info(List<BasicInfoBean> basic_info) {
        this.basic_info = basic_info;
    }

    public List<ServerBean> getServer_list() {
        return server_list;
    }

    public void setServer_list(List<ServerBean> server_list) {
        this.server_list = server_list;
    }

    public static class BasicInfoBean {
        /**
         * id : int
         * name : str
         * description : str
         * update_time : str
         */

        private String id;
        private String name;
        private String description;
        private String update_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }
    }

}
