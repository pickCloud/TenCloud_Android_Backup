package com.ten.tencloud.module.server.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.ClusterBean;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.server.contract.ServerClusterListContract;
import com.ten.tencloud.module.server.model.ServerModel;

import java.util.List;

/**
 * Created by lxq on 2017/11/23.
 */

public class ServerClusterListPresenter extends BasePresenter<ServerClusterListContract.View>
        implements ServerClusterListContract.Presenter<ServerClusterListContract.View> {

    @Override
    public void getClusterList() {
        mSubscriptions.add(ServerModel.getInstance().getClusters().subscribe(new JesSubscribe<List<ClusterBean>>(mView) {

            @Override
            public void _onSuccess(List<ClusterBean> list) {
                if (list == null || list.size() == 0) {
                    mView.showEmpty();
                } else {
                    mView.showClusterList(list);
                }
            }
        }));
    }

}
