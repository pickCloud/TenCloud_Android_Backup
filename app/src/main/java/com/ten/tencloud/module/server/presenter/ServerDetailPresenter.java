package com.ten.tencloud.module.server.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.ServerDetailBean;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.server.contract.ServerDetailContract;
import com.ten.tencloud.module.server.model.ServerModel;

/**
 * Created by lxq on 2017/11/23.
 */

public class ServerDetailPresenter extends BasePresenter<ServerDetailContract.View>
        implements ServerDetailContract.Presenter<ServerDetailContract.View> {

    @Override
    public void getServerDetail(String id) {
        mSubscriptions.add(ServerModel.getInstance().getServerDetail(id).subscribe(new JesSubscribe<ServerDetailBean>(mView) {
            @Override
            public void _onSuccess(ServerDetailBean serverDetailBean) {
                mView.showServerDetail(serverDetailBean);
            }
        }));
    }

}
