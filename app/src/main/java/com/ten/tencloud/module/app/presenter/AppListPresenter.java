package com.ten.tencloud.module.app.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.app.contract.AppListContract;
import com.ten.tencloud.module.app.model.AppModel;

import java.util.List;

/**
 * Create by chenxh@10.com on 2018/4/2.
 */
public class AppListPresenter extends BasePresenter<AppListContract.View> implements AppListContract.Presenter<AppListContract.View> {

    @Override
    public void getAppList() {
        mSubscriptions.add(AppModel.getInstance().getAppList()
                .subscribe(new JesSubscribe<List<AppBean>>(mView) {
                    @Override
                    public void _onSuccess(List<AppBean> appBeans) {
                        if (appBeans == null || appBeans.size() == 0) {
                            mView.showEmpty(false);
                            return;
                        }
                        mView.showAppList(appBeans, false);
                    }
                }));
    }

    @Override
    public void getAppListByPage(final boolean isLoadMore, Integer label) {
        handleLoadMore(isLoadMore);
        mSubscriptions.add(AppModel.getInstance().getAppListByPage(page, page_num, label)
                .subscribe(new JesSubscribe<List<AppBean>>(mView) {
                    @Override
                    public void _onSuccess(List<AppBean> appBeans) {
                        if (appBeans == null || appBeans.size() == 0) {
                            mView.showEmpty(isLoadMore);
                            return;
                        }
                        page++;
                        mView.showAppList(appBeans, isLoadMore);
                    }
                }));
    }


}
