package com.ten.tencloud.module.main.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.CompanyBean;
import com.ten.tencloud.bean.MessageBean;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.model.JesException;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.main.contract.MsgContract;
import com.ten.tencloud.module.main.model.MsgModel;
import com.ten.tencloud.module.user.model.UserModel;

import java.util.List;

import rx.functions.Func1;

/**
 * Created by lxq on 2018/1/2.
 */

public class MsgPresenter extends BasePresenter<MsgContract.View> implements MsgContract.Presenter<MsgContract.View> {
    @Override
    public void checkCompany(final int cid) {
        mSubscriptions.add(UserModel.getInstance().getCompaniesWithType(UserModel.COMPANIES_TYPE_ALL)
                .map(new Func1<List<CompanyBean>, Boolean>() {
                    @Override
                    public Boolean call(List<CompanyBean> companyBeans) {
                        if (companyBeans != null && companyBeans.size() > 0) {
                            for (CompanyBean company : companyBeans) {
                                if (cid == company.getCid()) {
                                    if (company.getStatus() == Constants.EMPLOYEE_STATUS_CODE_PASS
                                            || company.getStatus() == Constants.EMPLOYEE_STATUS_CODE_CREATE) {
                                        return true;
                                    }
                                }
                            }
                        }
                        return false;
                    }
                })
                .subscribe(new JesSubscribe<Boolean>(mView) {
                    @Override
                    public void _onSuccess(Boolean result) {
                        mView.jumpPage(result);
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
                    public void _onStart() {

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
                }));
    }
}
