package com.ten.tencloud.module.app.presenter;

import android.text.TextUtils;

import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.ReposErrorBean;
import com.ten.tencloud.model.JesException;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.app.contract.AppBranchListContract;
import com.ten.tencloud.module.app.model.AppModel;

import java.util.List;

/**
 * Create by chenxh@10.com on 2018/4/2.
 */
public class AppBranchListPresenter extends BasePresenter<AppBranchListContract.View> implements AppBranchListContract.Presenter<AppBranchListContract.View> {

    @Override
    public void getBranchList(String name, String url) {
        mSubscriptions.add(AppModel.getInstance().getReposBranches(name, url)
                .subscribe(new JesSubscribe<List<String>>(mView) {
                    @Override
                    public void _onSuccess(List<String> branches) {
                        if (branches == null || branches.size() == 0) {
                            mView.showEmpty();
                            return;
                        }
                        mView.showBranchList(branches);
                    }

                    @Override
                    public void _onError(JesException e) {
                        if (e.getCode() == 1) {
                            if (!TextUtils.isEmpty(e.getJson())) {
                                ReposErrorBean reposErrorBean = TenApp.getInstance().getGsonInstance().fromJson(e.getJson(), ReposErrorBean.class);
                                if (reposErrorBean.getData() != null && !TextUtils.isEmpty(reposErrorBean.getData().getUrl())) {
                                    mView.goAuth(reposErrorBean.getData().getUrl());
                                }
                            }
                        } else {
                            super._onError(e);
                        }
                    }
                }));
    }
}
