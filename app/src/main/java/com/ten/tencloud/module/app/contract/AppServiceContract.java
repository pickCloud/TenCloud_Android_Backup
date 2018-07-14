package com.ten.tencloud.module.app.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.ServiceBean;

import java.util.List;

public class AppServiceContract {

    public interface View extends IBaseView{
        void showServiceDetails(ServiceBean serviceBean);
        void showServiceList(List<ServiceBean> serviceBeans);
        void showResult(Object o);
        void showIngressInfo(ServiceBean ingressInfo);

    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V>{
        void getServiceList(int app_id);
        void getServiceDetails(int app_id, int service_id, int show_yaml);
        void deleteService(int app_id, int service_id);
        void ingressInfo(int app_id, int show_detail);

    }

}
