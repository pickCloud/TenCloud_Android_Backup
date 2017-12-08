package com.ten.tencloud.bean;

import java.util.List;

/**
 * Created by lxq on 2017/11/30.
 */

public class ServerLogBean {
    private String month;
    private List<LogInfo> logInfo;

    public static class LogInfo{

        /**
         * created_time : 2017-10-31 11:02:38
         * operation : 3      int 0:开机, 1:关机, 2:重启 3:修改
         * operation_status : 0     int 0:成功, 1:失败
         * user : Jon
         */

        private String created_time;
        private int operation;
        private int operation_status;
        private String user;

        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }

        public int getOperation() {
            return operation;
        }

        public void setOperation(int operation) {
            this.operation = operation;
        }

        public int getOperation_status() {
            return operation_status;
        }

        public void setOperation_status(int operation_status) {
            this.operation_status = operation_status;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }
    }
}
