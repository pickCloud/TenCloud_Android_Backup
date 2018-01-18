package com.ten.tencloud.module.main.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.CompanyBean;
import com.ten.tencloud.bean.MessageBean;
import com.ten.tencloud.model.JesException;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.main.contract.MsgContract;
import com.ten.tencloud.module.main.model.MsgModel;
import com.ten.tencloud.module.user.model.UserModel;

import java.util.List;

/**
 * Created by lxq on 2018/1/2.
 */

public class MsgPresenter extends BasePresenter<MsgContract.View> implements MsgContract.Presenter<MsgContract.View> {
    @Override
    public void getCompanyByCid(int cid) {
        mSubscriptions.add(UserModel.getInstance().getCompanyInfoByCid(cid)
                .subscribe(new JesSubscribe<List<CompanyBean>>(mView) {
                    @Override
                    public void _onSuccess(List<CompanyBean> companyBean) {
                        if (companyBean != null && companyBean.size() > 0) {
                            mView.jumpPage(true);
                        } else {
                            mView.jumpPage(false);
                        }
                    }

                    @Override
                    public void _onError(JesException e) {
                        mView.jumpPage(false);
                    }
                }));
    }

    @Override
    public void getMsgList(final boolean isLoadMore, String status, String mode) {
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

                    @Override
                    public void onStart() {

                    }
                }));
    }

    @Override
    public void search(String status, String mode, String key) {
        mSubscriptions.add(MsgModel.getInstance().search(status, mode, key)
                .subscribe(new JesSubscribe<List<MessageBean>>(mView) {
                    @Override
                    public void _onSuccess(List<MessageBean> data) {
                        if (data == null || data.size() == 0) {
                            mView.showEmpty(false);
                            return;
                        }
                        mView.showMsgList(data, false);
                    }

                    @Override
                    public void onStart() {

                    }
                }));
    }
}
