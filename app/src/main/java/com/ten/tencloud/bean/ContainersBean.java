package com.ten.tencloud.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ContainersBean implements Parcelable {

    public String name;
    public String image;

    public ArrayList<AppContainerBean.Port> ports;

    public ContainersBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.image);
        dest.writeTypedList(this.ports);
    }

    protected ContainersBean(Parcel in) {
        this.name = in.readString();
        this.image = in.readString();
        this.ports = in.createTypedArrayList(AppContainerBean.Port.CREATOR);
    }

    public static final Creator<ContainersBean> CREATOR = new Creator<ContainersBean>() {
        @Override
        public ContainersBean createFromParcel(Parcel source) {
            return new ContainersBean(source);
        }

        @Override
        public ContainersBean[] newArray(int size) {
            return new ContainersBean[size];
        }
    };
}
