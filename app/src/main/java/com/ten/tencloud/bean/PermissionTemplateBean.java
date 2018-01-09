package com.ten.tencloud.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lxq on 2017/12/25.
 */

public class PermissionTemplateBean implements Parcelable {

    /**
     * access_filehub :
     * access_projects :
     * create_time : 2017-12-21 16:07:03
     * permissions : 36,31,2,9,10,11,12,13,14,15
     * update_time : 2017-12-21 16:07:03
     * id : 30
     * name : 运维
     * cid : 4
     * access_servers :
     */

    private String access_filehub;
    private String access_projects;
    private String create_time;
    private String permissions;
    private String update_time;
    private int id;
    private String name;
    private int cid;
    private String access_servers;
    private int uid;

    private int type;

    public PermissionTemplateBean() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(access_filehub);
        dest.writeString(access_projects);
        dest.writeString(create_time);
        dest.writeString(permissions);
        dest.writeString(update_time);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(cid);
        dest.writeString(access_servers);
        dest.writeInt(type);
    }

    protected PermissionTemplateBean(Parcel in) {
        access_filehub = in.readString();
        access_projects = in.readString();
        create_time = in.readString();
        permissions = in.readString();
        update_time = in.readString();
        id = in.readInt();
        name = in.readString();
        cid = in.readInt();
        access_servers = in.readString();
        type = in.readInt();
    }

    public static final Creator<PermissionTemplateBean> CREATOR = new Creator<PermissionTemplateBean>() {
        @Override
        public PermissionTemplateBean createFromParcel(Parcel in) {
            return new PermissionTemplateBean(in);
        }

        @Override
        public PermissionTemplateBean[] newArray(int size) {
            return new PermissionTemplateBean[size];
        }
    };

    public String getAccess_filehub() {
        return access_filehub;
    }

    public void setAccess_filehub(String access_filehub) {
        this.access_filehub = access_filehub;
    }

    public String getAccess_projects() {
        return access_projects;
    }

    public void setAccess_projects(String access_projects) {
        this.access_projects = access_projects;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
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

    public String getAccess_servers() {
        return access_servers;
    }

    public void setAccess_servers(String access_servers) {
        this.access_servers = access_servers;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
