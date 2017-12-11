package com.ten.tencloud.module.server.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.server.contract.ServerChangeNameContract;
import com.ten.tencloud.module.server.model.ServerModel;

/**
 * Created by lxq on 2017/11/23.
 */

public class ServerChangeNamePresenter extends BasePresenter<ServerChangeNameContract.View>
        implements ServerChangeNameContract.Presenter<ServerChangeNameContract.View> {

    @Override
    public void changeName(String id, String name) {
        mSubscriptions.add(ServerModel.getInstance().changeServerName(id, name)
                .subscribe(new JesSubscribe<Object>(mView) {
                    @Override
                    public void _onSuccess(Object o) {
                        mView.changeSuccess();
                    }
                }));
    }
}
