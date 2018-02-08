package com.ten.tencloud.module.server.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.server.contract.ServerOperationContract;
import com.ten.tencloud.module.server.model.ServerModel;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lxq on 2017/11/23.
 */

public class ServerOperationPresenter extends BasePresenter<ServerOperationContract.View>
        implements ServerOperationContract.Presenter<ServerOperationContract.View> {

    @Override
    public void rebootServer(String id) {
        mSubscriptions.add(ServerModel.getInstance().rebootServer(id).subscribe(new JesSubscribe<Object>(mView) {
            @Override
            public void _onSuccess(Object o) {
                mView.rebootSuccess();
            }
        }));
    }

    @Override
    public void delServer(String id) {
        mSubscriptions.add(ServerModel.getInstance().delServer(id).subscribe(new JesSubscribe<Object>(mView) {
            @Override
            public void _onSuccess(Object o) {
                mView.delSuccess();
            }
        }));
    }

    @Override
    public void startServer(String id) {
        mSubscriptions.add(ServerModel.getInstance().startServer(id).subscribe(new JesSubscribe<Object>(mView) {
            @Override
            public void _onSuccess(Object o) {
                mView.startSuccess();
            }
        }));
    }

    @Override
    public void stopServer(String id) {
        mSubscriptions.add(ServerModel.getInstance().stopServer(id).subscribe(new JesSubscribe<Object>(mView) {
            @Override
            public void _onSuccess(Object o) {
                mView.stopSuccess();
            }
        }));
    }

    @Override
    public void queryServerState(final String id) {
        mSubscriptions.add(
                Observable.interval(5, 5, TimeUnit.SECONDS)
                        .flatMap(new Func1<Long, Observable<String>>() {
                            @Override
                            public Observable<String> call(Long aLong) {
                                return ServerModel.getInstance().queryServerState(id);
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new JesSubscribe<String>(mView) {
                            @Override
                            public void _onSuccess(String s) {
                                mView.showState(s);
                            }
                        }));
    }
}
