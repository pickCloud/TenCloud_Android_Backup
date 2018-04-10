package com.ten.tencloud.bean;

/**
 * Created by chenxh@10.com on 2018/3/27.
 */
public class ReposBean {

    /**
     * repos_name : str
     * repos_url : str
     * http_url : str
     */

    private String repos_name;
    private String repos_url;
    private String http_url;

    public ReposBean(String repos_name, String repos_url) {
        this.repos_name = repos_name;
        this.repos_url = repos_url;
    }

    public String getRepos_name() {
        return repos_name;
    }

    public void setRepos_name(String repos_name) {
        this.repos_name = repos_name;
    }

    public String getRepos_url() {
        return repos_url;
    }

    public void setRepos_url(String repos_url) {
        this.repos_url = repos_url;
    }

    public String getHttp_url() {
        return http_url;
    }

    public void setHttp_url(String http_url) {
        this.http_url = http_url;
    }
}


