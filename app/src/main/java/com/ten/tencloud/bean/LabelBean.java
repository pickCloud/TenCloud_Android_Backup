package com.ten.tencloud.bean;

import android.support.annotation.NonNull;

import com.ten.tencloud.widget.PinyinTool;

/**
 * Create by chenxh@10.com on 2018/4/3.
 */
public class LabelBean implements Comparable {

    private int id;
    private String name;
    private boolean isCheck;//历史标签选中控制
    private boolean isSelect;//编辑标签的实际单选状态控制
    private boolean isDelete;//编辑标签，再次点击删除

    public LabelBean(String name) {
        this.name = name;
    }

    public LabelBean(int id, String name) {
        this.id = id;
        this.name = name;
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
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        return getName().equals(((LabelBean) obj).getName());
    }

    @Override
    public String toString() {
        return "LabelBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isCheck=" + isCheck +
                ", isSelect=" + isSelect +
                ", isDelete=" + isDelete +
                '}';
    }

    @Override
    public int compareTo(@NonNull Object obj) {
        LabelBean o = (LabelBean) obj;
        try {
            PinyinTool pinyinTool = new PinyinTool();
            return pinyinTool.toPinYin(this.getName()) - pinyinTool.toPinYin(o.getName());
        } catch (Exception e) {
            return o.getName().charAt(0) - getName().charAt(0);
        }
    }

}
