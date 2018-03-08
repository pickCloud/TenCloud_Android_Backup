package com.ten.tencloud.module.server.presenter;

import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.ServerSystemLoadBean;
import com.ten.tencloud.module.server.contract.ServerSystemLoadContract;

/**
 * Created by lxq on 2017/11/23.
 */

public class ServerSystemLoadPresenter extends BasePresenter<ServerSystemLoadContract.View>
        implements ServerSystemLoadContract.Presenter<ServerSystemLoadContract.View> {
    @Override
    public void getServerSystemLoad(String id) {
//        mSubscriptions.add(ServerModel.getInstance().getServerSystemLoad(id)
//                .subscribe(new JesSubscribe<ServerSystemLoadBean>(mView) {
//                    @Override
//                    public void _onSuccess(ServerSystemLoadBean systemLoadBean) {
//                        mView.showServerSystemLoad(systemLoadBean);
//                    }
//                }));
        String json = "{\n" +
                "      \"date\": \"2018-03-08 14:23:30\",\n" +
                "      \"ten_minute_load_\": 0.54,\n" +
                "      \"five_minute_load\": 0.81,\n" +
                "      \"login_users\": 1,\n" +
                "      \"one_minute_load\": 0.14,\n" +
                "      \"run_time\": \"6天6小时30分钟\"\n" +
                "    }";
        mView.showServerSystemLoad(TenApp.getInstance().getGsonInstance().fromJson(json, ServerSystemLoadBean.class));
    }
}
