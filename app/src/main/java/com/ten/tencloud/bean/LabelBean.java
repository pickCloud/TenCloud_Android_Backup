package com.ten.tencloud.bean;

import android.support.annotation.NonNull;

/**
 * Create by chenxh@10.com on 2018/4/3.
 */
public class LabelBean implements Comparable<LabelBean>{

    private String name;
    private boolean isCheck;//历史标签选中控制
    private boolean isSelect;//编辑标签的实际单选状态控制
    private boolean isDelete;//编辑标签，再次点击删除

    public LabelBean(String name) {
        this.name = name;
    }

    public LabelBean(String name, boolean isCheck) {
        this.name = name;
        this.isCheck = isCheck;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Override
    public int compareTo(@NonNull LabelBean o) {
        return this.getName().compareTo(o.getName());
    }
}
