package com.ten.tencloud.bean;

import java.util.List;

/**
 * Created by lxq on 2018/1/4.
 */

public class UserPermissionAndAll {
    private PermissionTemplate2Bean userPermission;
    private List<PermissionTreeNodeBean> all;

    public UserPermissionAndAll() {
    }

    public UserPermissionAndAll(PermissionTemplate2Bean userPermission, List<PermissionTreeNodeBean> all) {
        this.userPermission = userPermission;
        this.all = all;
    }

    public PermissionTemplate2Bean getUserPermission() {
        return userPermission;
    }

    public void setUserPermission(PermissionTemplate2Bean userPermission) {
        this.userPermission = userPermission;
    }

    public List<PermissionTreeNodeBean> getAll() {
        return all;
    }

    public void setAll(List<PermissionTreeNodeBean> all) {
        this.all = all;
    }
}
