package com.ten.tencloud.module.server.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.ServerBean;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.server.contract.ServerHomeContract;
import com.ten.tencloud.module.server.model.ServerModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxq on 2017/11/23.
 */

public class ServerHomePresenter extends BasePresenter<ServerHomeContract.View>
        implements ServerHomeContract.Presenter<ServerHomeContract.View> {

    @Override
    public void getServerList(int id) {
        mSubscriptions.add(ServerModel.getInstance().getServerList(id)
                .subscribe(new JesSubscribe<List<ServerBean>>(mView) {
                    @Override
                    public void _onSuccess(List<ServerBean> serverBeans) {
                        List<ServerBean> result = new ArrayList<>();
                        if (serverBeans.size() < 3) {
                            mView.showServerList(serverBeans);
                            return;
                        }
                        for (int i = 0; i < 3; i++) {
                            result.add(serverBeans.get(i));
                        }
                        mView.showServerList(result);
                    }
                }));
    }
}
