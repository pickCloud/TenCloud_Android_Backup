package com.ten.tencloud.constants;

/**
 * 全局状态管理器
 * Created by lxq on 2017/12/22.
 */

public class GlobalStatusManager {

    private static GlobalStatusManager instance;

    /**
     * 服务器列表需要刷新
     */
    private boolean serverListNeedRefresh;

    /**
     * 用户信息需要刷新
     */
    private boolean userInfoNeedRefresh;

    /**
     * 模板信息
     */
    private boolean templateNeedRefresh;

    /**
     * 公式列表
     */
    private boolean companyListNeedRefresh;

    /**
     * 员工列表
     */
    private boolean employeeListNeedRefresh;

    private GlobalStatusManager() {
    }

    public synchronized static GlobalStatusManager getInstance() {
        if (instance == null) {
            synchronized (GlobalStatusManager.class) {
                instance = new GlobalStatusManager();
            }
        }
        return instance;
    }

    public boolean isServerListNeedRefresh() {
        return serverListNeedRefresh;
    }

    public void setServerListNeedRefresh(boolean serverListNeedRefresh) {
        this.serverListNeedRefresh = serverListNeedRefresh;
    }

    public boolean isUserInfoNeedRefresh() {
        return userInfoNeedRefresh;
    }

    public void setUserInfoNeedRefresh(boolean userInfoNeedRefresh) {
        this.userInfoNeedRefresh = userInfoNeedRefresh;
    }

    public boolean isTemplateNeedRefresh() {
        return templateNeedRefresh;
    }

    public void setTemplateNeedRefresh(boolean templateNeedRefresh) {
        this.templateNeedRefresh = templateNeedRefresh;
    }

    public boolean isCompanyListNeedRefresh() {
        return companyListNeedRefresh;
    }

    public void setCompanyListNeedRefresh(boolean companyListNeedRefresh) {
        this.companyListNeedRefresh = companyListNeedRefresh;
    }

    public boolean isEmployeeListNeedRefresh() {
        return employeeListNeedRefresh;
    }

    public void setEmployeeListNeedRefresh(boolean employeeListNeedRefresh) {
        this.employeeListNeedRefresh = employeeListNeedRefresh;
    }
}
