package com.ten.tencloud.module.server.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.ServerMonitorBean;

/**
 * Created by lxq on 2017/11/23.
 */

public class ServerMonitorContract {
    public interface View extends IBaseView {
        void showServerMonitorInfo(ServerMonitorBean serverMonitor);

    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {
        void getServerMonitorInfo(String id,String startTime,String endTime);
    }
}
