package com.ten.tencloud.module.server.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.ServerSystemLoadBean;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.server.contract.ServerSystemLoadContract;
import com.ten.tencloud.module.server.model.ServerModel;

/**
 * Created by lxq on 2017/11/23.
 */

public class ServerSystemLoadPresenter extends BasePresenter<ServerSystemLoadContract.View>
        implements ServerSystemLoadContract.Presenter<ServerSystemLoadContract.View> {
    @Override
    public void getServerSystemLoad(String id) {
        mSubscriptions.add(ServerModel.getInstance().getServerSystemLoad(id)
                .subscribe(new JesSubscribe<ServerSystemLoadBean>(mView) {
                    @Override
                    public void _onSuccess(ServerSystemLoadBean systemLoadBean) {
                        mView.showServerSystemLoad(systemLoadBean);
                    }

                    @Override
                    public void onStart() {

                    }
                }));
    }
}
