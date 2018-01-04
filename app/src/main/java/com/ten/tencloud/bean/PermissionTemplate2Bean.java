package com.ten.tencloud.bean;

import java.util.List;

/**
 * Created by lxq on 2017/12/25.
 */

public class PermissionTemplate2Bean {

    private List<ID> access_filehub;
    private List<ID> access_projects;
    private String create_time;
    private List<ID> permissions;
    private String update_time;
    private int id;
    private String name;
    private int cid;
    private List<ID> access_servers;

    public List<ID> getAccess_filehub() {
        return access_filehub;
    }

    public void setAccess_filehub(List<ID> access_filehub) {
        this.access_filehub = access_filehub;
    }

    public List<ID> getAccess_projects() {
        return access_projects;
    }

    public void setAccess_projects(List<ID> access_projects) {
        this.access_projects = access_projects;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public List<ID> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<ID> permissions) {
        this.permissions = permissions;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

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

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public List<ID> getAccess_servers() {
        return access_servers;
    }

    public void setAccess_servers(List<ID> access_servers) {
        this.access_servers = access_servers;
    }

    public static class ID {
        private int id;
        private int sid; //access_servers
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSid() {
            return sid;
        }

        public void setSid(int sid) {
            this.sid = sid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
