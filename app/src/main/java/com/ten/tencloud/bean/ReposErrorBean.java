package com.ten.tencloud.bean;

/**
 * Create by chenxh@10.com on 2018/4/12.
 */
public class ReposErrorBean {

    /**
     * data : {"url":"https://github.com/login/oauth/authorize?client_id=5448811562b83dadc3cf&scope=repo%2Cuser%3Aemail&state=&redirect_uri=http%3A%2F%2Fcd.10.com%2Fapi%2Fgithub%2Foauth%2Fcallback%3Fredirect_url%3Dhttps%253A%252F%252Fgithub.com%252FAIUnicorn%253Ftoken%253Dtrue%26uid%3D79"}
     * message : Require token!
     * status : 1
     */

    private DataBean data;
    private String message;
    private int status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * url : https://github.com/login/oauth/authorize?client_id=5448811562b83dadc3cf&scope=repo%2Cuser%3Aemail&state=&redirect_uri=http%3A%2F%2Fcd.10.com%2Fapi%2Fgithub%2Foauth%2Fcallback%3Fredirect_url%3Dhttps%253A%252F%252Fgithub.com%252FAIUnicorn%253Ftoken%253Dtrue%26uid%3D79
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}


