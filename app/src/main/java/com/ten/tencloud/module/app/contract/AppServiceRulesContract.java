package com.ten.tencloud.module.app.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.ExampleBean;
import com.ten.tencloud.bean.ServiceBean;
import com.ten.tencloud.bean.ServicePortBean;

import java.util.List;
import java.util.Map;

public class AppServiceRulesContract {

    public interface View extends IBaseView{

        void showResult(Object o);
        void servicePortList(List<ServicePortBean> servicePortBeanList);
        void podLabels(List<ExampleBean> exampleBeans);

    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V>{
        void servicePort(int app_id);
        void ingressIngress(Map<String, Object> serviceBean);
        void podLabels(int app_id);

    }

}
