package com.ten.tencloud.module.app.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.bean.ReposBean;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.app.contract.AppListContract;
import com.ten.tencloud.module.app.contract.ReposListContract;
import com.ten.tencloud.module.app.model.AppModel;

import java.util.List;

/**
 * Create by chenxh@10.com on 2018/4/2.
 */
public class ReposListPresenter extends BasePresenter<ReposListContract.View> implements ReposListContract.Presenter<ReposListContract.View> {


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
                }));
    }
}
