package com.ten.tencloud.module.server.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.ServerHistoryBean;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.server.contract.ServerHistoryContract;
import com.ten.tencloud.module.server.model.ServerModel;

import java.util.List;

/**
 * Created by lxq on 2017/11/23.
 */

public class ServerHistoryPresenter extends BasePresenter<ServerHistoryContract.View>
        implements ServerHistoryContract.Presenter<ServerHistoryContract.View> {

    @Override
    public void getServerHistory(final boolean isLoadMore, String id, int type, String startTime, String endTime) {
        handleLoadMore(isLoadMore);
        mSubscriptions.add(ServerModel.getInstance().getServerHistory(id, type, startTime, endTime, page).subscribe(new JesSubscribe<List<ServerHistoryBean>>(mView) {
            @Override
            public void _onSuccess(List<ServerHistoryBean> data) {
                if (data == null || data.size() == 0) {
                    mView.showNoData(isLoadMore);
                    return;
                }
                page++;
                mView.showServerHistory(data, isLoadMore);
            }
        }));
    }
}
