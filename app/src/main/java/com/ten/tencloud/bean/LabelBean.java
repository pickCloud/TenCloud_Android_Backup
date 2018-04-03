package com.ten.tencloud.bean;

/**
 * Create by chenxh@10.com on 2018/4/3.
 */
public class LabelBean {

    private String name;
    private boolean isCheck;

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


}
