package com.ten.tencloud.module.main.model;

import com.ten.tencloud.TenApp;
import com.ten.tencloud.bean.MessageBean;
import com.ten.tencloud.model.HttpResultFunc;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lxq on 2018/1/10.
 */

public class MsgModel {
    private static MsgModel INSTANCE;

    private MsgModel() {
    }

    public static synchronized MsgModel getInstance() {
        if (INSTANCE == null) {
            synchronized (MsgModel.class) {
                INSTANCE = new MsgModel();
            }
        }
        return INSTANCE;
    }

    /**
     * 获取消息数量
     *
     * @param status 0 未读 1 已读
     * @return
     */
    public Observable<Map<String, Integer>> getMsgCountByStatus(int status) {
        return TenApp.getRetrofitClient().getTenMsgApi()
                .getMsgCount(status)
                .map(new HttpResultFunc<Map<String, Integer>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取消息列表
     *
     * @param status 0未读，1已读
     * @param mode   1加入企业，2企业改变信息
     * @param page   分页
     * @return
     */

    public static final String MODE_ALL = "";
    public static final String MODE_JOIN = "1";
    public static final String MODE_CHANGE = "2";
    public static final String MODE_LEAVE = "3";

    public Observable<List<MessageBean>> getMsgList(String status, String mode, int page) {
        return TenApp.getRetrofitClient().getTenMsgApi()
                .getMsgListByStatus(mode, page)
                .map(new HttpResultFunc<List<MessageBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<MessageBean>> search(String status, String mode, String key) {
        return TenApp.getRetrofitClient().getTenMsgApi()
                .search(mode, key)
                .map(new HttpResultFunc<List<MessageBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
