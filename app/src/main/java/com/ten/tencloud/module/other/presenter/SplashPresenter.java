package com.ten.tencloud.module.other.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.ServerThresholdBean;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.other.contract.SplashContract;
import com.ten.tencloud.module.server.model.ServerModel;

/**
 * Created by lxq on 2018/1/2.
 */

public class SplashPresenter extends BasePresenter<SplashContract.View> implements SplashContract.Presenter<SplashContract.View> {


    @Override
    public void getThreshold() {
        mSubscriptions.add(ServerModel.getInstance().getThreshold()
                .subscribe(new JesSubscribe<ServerThresholdBean>(mView) {
                    @Override
                    public void _onSuccess(ServerThresholdBean thresholdBean) {
                        mView.showThreshold(thresholdBean);
                    }

                    @Override
                    public void onStart() {

                    }
                }));
    }
}
