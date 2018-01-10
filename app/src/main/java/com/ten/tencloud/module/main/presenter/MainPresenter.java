package com.ten.tencloud.module.main.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.main.contract.MainContract;
import com.ten.tencloud.module.main.model.MsgModel;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by lxq on 2018/1/2.
 */

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter<MainContract.View> {


    @Override
    public void getMsgCount() {
        mSubscriptions.add(Observable.interval(0,30, TimeUnit.SECONDS)
                .flatMap(new Func1<Long, Observable<Map<String, Integer>>>() {
                    @Override
                    public Observable<Map<String, Integer>> call(Long aLong) {
                        return MsgModel.getInstance().getMsgCountByStatus(0);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new JesSubscribe<Map<String, Integer>>(mView) {
                    @Override
                    public void _onSuccess(Map<String, Integer> stringIntegerMap) {
                        mView.showMsgCount(stringIntegerMap.get("num") + "");
                    }

                    @Override
                    public void onStart() {
                    }
                }));
    }
}
