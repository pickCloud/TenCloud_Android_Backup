package com.ten.tencloud.bean;

/**
 * Created by lxq on 2018/3/8.
 */

public class ServerSystemLoadBean {

    /**
     * date : 2018-03-08 14:23:30
     * fifth_minute_load : 0.24
     * five_minute_load : 0.34
     * login_users : 1
     * one_minute_load : 0.14
     * run_time : 6天6小时30分钟
     */

    private String date;
    private float fifth_minute_load;
    private float five_minute_load;
    private int login_users;
    private float one_minute_load;
    private String run_time;
    private Monitor monitor;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getFifth_minute_load() {
        return fifth_minute_load;
    }

    public void setFifth_minute_load(float fifth_minute_load) {
        this.fifth_minute_load = fifth_minute_load;
    }

    public float getFive_minute_load() {
        return five_minute_load;
    }

    public void setFive_minute_load(float five_minute_load) {
        this.five_minute_load = five_minute_load;
    }

    public int getLogin_users() {
        return login_users;
    }

    public void setLogin_users(int login_users) {
        this.login_users = login_users;
    }

    public float getOne_minute_load() {
        return one_minute_load;
    }

    public void setOne_minute_load(float one_minute_load) {
        this.one_minute_load = one_minute_load;
    }

    public String getRun_time() {
        return run_time;
    }

    public void setRun_time(String run_time) {
        this.run_time = run_time;
    }

    public Monitor getMonitor() {
        return monitor;
    }

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    public static class Monitor {

        /**
         * serverID : 191
         * name : 测试12
         * colorType : 4
         * cpuUsageRate : 0
         * memUsageRate : 20.15
         * diskUsageRate : 4.23
         * diskUtilize : 5.17
         * netUsageRate : 0.0/0.0
         * netDownload : 0Kb/s
         * netUpload : 0Kb/s
         */

        private String serverID;
        private String name;
        private int colorType;
        private float cpuUsageRate;
        private float memUsageRate;
        private float diskUsageRate;
        private float diskUtilize;
        private String netUsageRate;
        private String netDownload;
        private String netUpload;
        private String netInputMax;
        private String netOutputMax;


        public String getServerID() {
            return serverID;
        }

        public void setServerID(String serverID) {
            this.serverID = serverID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getColorType() {
            return colorType;
        }

        public void setColorType(int colorType) {
            this.colorType = colorType;
        }

        public float getCpuUsageRate() {
            return cpuUsageRate;
        }

        public void setCpuUsageRate(float cpuUsageRate) {
            this.cpuUsageRate = cpuUsageRate;
        }

        public float getMemUsageRate() {
            return memUsageRate;
        }

        public void setMemUsageRate(float memUsageRate) {
            this.memUsageRate = memUsageRate;
        }

        public float getDiskUsageRate() {
            return diskUsageRate;
        }

        public void setDiskUsageRate(float diskUsageRate) {
            this.diskUsageRate = diskUsageRate;
        }

        public float getDiskUtilize() {
            return diskUtilize;
        }

        public void setDiskUtilize(float diskUtilize) {
            this.diskUtilize = diskUtilize;
        }

        public String getNetUsageRate() {
            return netUsageRate;
        }

        public void setNetUsageRate(String netUsageRate) {
            this.netUsageRate = netUsageRate;
        }

        public String getNetDownload() {
            return netDownload;
        }

        public void setNetDownload(String netDownload) {
            this.netDownload = netDownload;
        }

        public String getNetUpload() {
            return netUpload;
        }

        public void setNetUpload(String netUpload) {
            this.netUpload = netUpload;
        }

        public String getNetInputMax() {
            return netInputMax;
        }

        public void setNetInputMax(String netInputMax) {
            this.netInputMax = netInputMax;
        }

        public String getNetOutputMax() {
            return netOutputMax;
        }

        public void setNetOutputMax(String netOutputMax) {
            this.netOutputMax = netOutputMax;
        }
    }
}
