package com.ten.tencloud.bean;

/**
 * Created by lxq on 2017/12/15.
 */

public class CompanyBean {

    /**
     * name : 泉州bb企业有限公司
     * id : 19
     * contact : 施先生
     * description : null
     * update_time : 2017-12-07 10:52:14
     * mobile : 15060897760
     * create_time : 2017-11-29 11:25:44
     */

    private String name;
    private int id;
    private String contact;
    private String description;
    private String mobile;
    private String update_time;
    private String create_time;
    private String image_url;

    //===========
    private String company_name;
    private int is_admin;
    private int cid;
    private int status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public int getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(int is_admin) {
        this.is_admin = is_admin;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
