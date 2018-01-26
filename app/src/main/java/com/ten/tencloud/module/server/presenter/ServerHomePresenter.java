package com.ten.tencloud.module.server.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.ServerBean;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.server.contract.ServerHomeContract;
import com.ten.tencloud.module.server.model.ServerModel;

import java.util.List;

/**
 * Created by lxq on 2017/11/23.
 */

public class ServerHomePresenter extends BasePresenter<ServerHomeContract.View>
        implements ServerHomeContract.Presenter<ServerHomeContract.View> {

    @Override
    public void getWarnServerList(int id) {
        mSubscriptions.add(ServerModel.getInstance().getWarnServerList(id)
                .subscribe(new JesSubscribe<List<ServerBean>>(mView) {
                    @Override
                    public void _onSuccess(List<ServerBean> serverBeans) {
                        if (serverBeans == null || serverBeans.size() == 0) {
                            mView.showEmptyView();
                        } else {
                            mView.showWarnServerList(serverBeans);
                        }
                    }
                }));
    }
}
