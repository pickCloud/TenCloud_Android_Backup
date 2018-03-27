package com.ten.tencloud.module.app.model;

/**
 * Created by chenxh@10.com on 2018/3/27.
 */
public class AppServiceHomeModel {

    private AppServiceHomeModel() {
    }

    private static class SingletonInstance {
        private static final AppServiceHomeModel INSTANCE = new AppServiceHomeModel();
    }

    public static AppServiceHomeModel getInstance() {
        return SingletonInstance.INSTANCE;
    }


}