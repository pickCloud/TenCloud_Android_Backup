package com.ten.tencloud.module.server.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.server.contract.ServerDockerContract;
import com.ten.tencloud.module.server.model.ServerModel;

import java.util.List;

/**
 * Created by lxq on 2017/11/23.
 */

public class ServerDockerPresenter extends BasePresenter<ServerDockerContract.View>
        implements ServerDockerContract.Presenter<ServerDockerContract.View> {

    @Override
    public void getDockerList(String id) {
        mSubscriptions.add(ServerModel.getInstance().getContaninersByServer(id).subscribe(new JesSubscribe<List<List<String>>>(mView) {
            @Override
            public void _onSuccess(List<List<String>> lists) {
                if (lists == null || lists.size() == 0) {
                    mView.showEmpty();
                } else {
                    mView.showDocker(lists);
                }
            }
        }));
    }
}
