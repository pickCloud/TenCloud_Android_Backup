package com.ten.tencloud.module.app.presenter;

import android.text.TextUtils;

import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.ReposBean;
import com.ten.tencloud.bean.ReposErrorBean;
import com.ten.tencloud.model.JesException;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.app.contract.AppReposListContract;
import com.ten.tencloud.module.app.model.AppModel;

import java.util.List;

/**
 * Create by chenxh@10.com on 2018/4/2.
 */
public class AppReposListPresenter extends BasePresenter<AppReposListContract.View> implements AppReposListContract.Presenter<AppReposListContract.View> {


    @Override
    public void getReposList(String url) {
        mSubscriptions.add(AppModel.getInstance().getReposList(url)
                .subscribe(new JesSubscribe<List<ReposBean>>(mView) {
                    @Override
                    public void _onSuccess(List<ReposBean> reposBeans) {
                        if (reposBeans == null || reposBeans.size() == 0) {
                            mView.showEmpty();
                            return;
                        }
                        mView.showReposList(reposBeans);
                    }

                    @Override
                    public void _onError(JesException e) {
                        if (e.getCode() == 1) {
                            if (!TextUtils.isEmpty(e.getJson())) {
                                ReposErrorBean reposErrorBean = TenApp.getInstance().getGsonInstance().fromJson(e.getJson(), ReposErrorBean.class);
                                if (reposErrorBean.getData() != null && !TextUtils.isEmpty(reposErrorBean.getData().getUrl())){
                                    mView.goAuth(reposErrorBean.getData().getUrl());
                                }
                            }
                        } else {
                            super._onError(e);
                        }
                    }
                }));
    }

    @Override
    public void githubClear() {
        mSubscriptions.add(AppModel.getInstance().githubClear()
        .subscribe(new JesSubscribe<Object>(mView) {
            @Override
            public void _onSuccess(Object o) {
                mView.githubClearSuc();
            }
        }));
    }
}
