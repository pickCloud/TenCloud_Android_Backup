package com.ten.tencloud.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lxq on 2017/12/28.
 */

public class EmployeeBean implements Parcelable {

    /**
     * update_time : 2017-12-04 10:57:21
     * name : 米建立
     * mobile : 13720893640
     * create_time : 2017-11-17 11:26:00
     * id : 14
     * status : 1
     * uid : 8
     * is_admin : 1
     */

    private String update_time;
    private String name;
    private String mobile;
    private String create_time;
    private int id;
    private int status;
    private int uid;
    private int is_admin; //1 是 0 否
    private String image_url;
    private String id_card;//身份证

    public EmployeeBean() {
    }

    protected EmployeeBean(Parcel in) {
        update_time = in.readString();
        name = in.readString();
        mobile = in.readString();
        create_time = in.readString();
        id = in.readInt();
        status = in.readInt();
        uid = in.readInt();
        is_admin = in.readInt();
        image_url = in.readString();
        id_card = in.readString();
    }

    public static final Creator<EmployeeBean> CREATOR = new Creator<EmployeeBean>() {
        @Override
        public EmployeeBean createFromParcel(Parcel in) {
            return new EmployeeBean(in);
        }

        @Override
        public EmployeeBean[] newArray(int size) {
            return new EmployeeBean[size];
        }
    };

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(int is_admin) {
        this.is_admin = is_admin;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(update_time);
        dest.writeString(name);
        dest.writeString(mobile);
        dest.writeString(create_time);
        dest.writeInt(id);
        dest.writeInt(status);
        dest.writeInt(uid);
        dest.writeInt(is_admin);
        dest.writeString(image_url);
        dest.writeString(id_card);
    }
}
