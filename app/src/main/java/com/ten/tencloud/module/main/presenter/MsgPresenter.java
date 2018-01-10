package com.ten.tencloud.module.main.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.MessageBean;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.main.contract.MsgContract;
import com.ten.tencloud.module.main.model.MsgModel;

import java.util.List;

/**
 * Created by lxq on 2018/1/2.
 */

public class MsgPresenter extends BasePresenter<MsgContract.View> implements MsgContract.Presenter<MsgContract.View> {

    @Override
    public void getMsgList(final boolean isLoadMore, String status, int mode) {
        handleLoadMore(isLoadMore);
        mSubscriptions.add(MsgModel.getInstance().getMsgList(status, mode, page)
                .subscribe(new JesSubscribe<List<MessageBean>>(mView) {
                    @Override
                    public void _onSuccess(List<MessageBean> data) {
                        if (data == null || data.size() == 0) {
                            mView.showEmpty(isLoadMore);
                            return;
                        }
                        page++;
                        mView.showMsgList(data, isLoadMore);
                    }
                }));
    }
}
