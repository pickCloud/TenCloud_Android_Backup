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
    public Observable<List<MessageBean>> getMsgList(String status, int mode, int page) {
        return TenApp.getRetrofitClient().getTenMsgApi()
                .getMsgListByStatus(status, mode, page)
                .map(new HttpResultFunc<List<MessageBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
