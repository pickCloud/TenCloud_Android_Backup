package com.ten.tencloud.module.app.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.LabelBean;
import com.ten.tencloud.model.JesException;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.app.contract.AppLabelSelectContract;
import com.ten.tencloud.module.app.model.AppModel;

import java.util.TreeSet;

/**
 * Create by chenxh@10.com on 2018/4/11.
 */
public class AppLabelSelectPresenter extends BasePresenter<AppLabelSelectContract.View> implements AppLabelSelectContract.Presenter<AppLabelSelectContract.View> {

    @Override
    public void newLabel(final String name, int type) {
        mSubscriptions.add(AppModel.getInstance().newLabel(name, type)
                .subscribe(new JesSubscribe<LabelBean>(mView) {
                    @Override
                    public void _onSuccess(LabelBean bean) {
                        bean.setName(name);
                        mView.labelAddResult(bean);
                    }

                    @Override
                    public void _onError(JesException e) {
                        super._onError(e);
                    }
                }));
    }

    @Override
    public void getLabelList(int type) {
        mSubscriptions.add(AppModel.getInstance().getLabelList(type)
                .subscribe(new JesSubscribe<TreeSet<LabelBean>>(mView) {
                    @Override
                    public void _onSuccess(TreeSet<LabelBean> labelBeans) {
                        if (labelBeans == null || labelBeans.size() == 0) {
                            mView.showEmpty();
                            return;
                        }
                        mView.showLabelList(labelBeans);
                    }

                }));
    }
}
