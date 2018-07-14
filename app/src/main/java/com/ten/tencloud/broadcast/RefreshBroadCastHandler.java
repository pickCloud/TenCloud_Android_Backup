package com.ten.tencloud.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.ten.tencloud.TenApp;
import com.ten.tencloud.listener.OnRefreshListener;
import com.ten.tencloud.listener.OnRefreshWithDataListener;

/**
 * Created by lxq on 2018/1/30.
 */

public class RefreshBroadCastHandler {

    /**
     * 权限发生变化
     */
    public final static String PERMISSION_REFRESH_ACTION = "PERMISSION_REFRESH_ACTION";
    /**
     * 切换公司
     */
    public final static String SWITCH_COMPANY_REFRESH_ACTION = "SWITCH_COMPANY_REFRESH_ACTION";

    /**
     * 服务器列表变化
     */
    public final static String SERVER_LIST_CHANGE_ACTION = "SERVER_LIST_CHANGE_ACTION";

    /**
     * 服务器信息变化
     */
    public final static String SERVER_INFO_CHANGE_ACTION = "SERVER_INFO_CHANGE_ACTION";

    /**
     * 设置权限勾选
     */
    public final static String PERMISSION_SETTING_CHANGE_ACTION = "PERMISSION_SETTING_CHANGE_ACTION";

    /**
     * 应用列表变化
     */
    public final static String APP_LIST_CHANGE_ACTION = "APP_LIST_CHANGE_ACTION";
    /**
     * 应用列表变化
     */
    public final static String APP_LIST_CHANGE_DELETE = "APP_LIST_CHANGE_DELETE";

    /**
     * 应用详情变化
     */
    public final static String APP_INFO_CHANGE_ACTION = "APP_INFO_CHANGE_ACTION";

    /**
     * 镜像来源变化
     */
    public final static String IMAGE_SOURCE_CHANGE_ACTION = "IMAGE_SOURCE_CHANGE_ACTION";

    /**
     * 选择代码分支
     */
    public final static String APP_BRANCH_CHANGE_ACTION = "APP_BRANCH_CHANGE_ACTION";


    /**
     * 选择代码分支
     */
    public final static String APP_SERVICE_DELETE_ACTION = "APP_SERVICE_DELETE";

    private LocalBroadcastManager mLocalBroadcastManager;
    private BroadcastReceiver mReceiver;

    private String mAction;

    public RefreshBroadCastHandler(String action) {
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(TenApp.getInstance());
        mAction = action;
    }

    public void sendBroadCast() {
        Intent intent = new Intent(mAction);
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    public void sendBroadCastWithData(Bundle extras) {
        Intent intent = new Intent(mAction);
        intent.putExtras(extras);
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    public void registerReceiver(final OnRefreshListener onRefreshListener) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(mAction);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                onRefreshListener.onRefresh();
            }
        };
        mLocalBroadcastManager.registerReceiver(mReceiver, filter);
    }

    public void registerReceiver(final OnRefreshWithDataListener onRefreshListener) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(mAction);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                onRefreshListener.onRefresh(intent.getExtras());
            }
        };
        mLocalBroadcastManager.registerReceiver(mReceiver, filter);
    }

    public void unregisterReceiver() {
        mLocalBroadcastManager.unregisterReceiver(mReceiver);
    }
}
