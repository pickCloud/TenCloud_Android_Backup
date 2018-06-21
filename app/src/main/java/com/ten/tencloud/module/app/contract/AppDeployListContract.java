package com.ten.tencloud.module.app.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.DeploymentBean;

import java.util.List;

/**
 * Create by chenxh@10.com on 2018/4/3.
 */
public class AppDeployListContract {

    public interface View extends IBaseView {
        void showEmpty();

        void showList(List<DeploymentBean> data);
    }

   public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {

        void getDeployList(Integer app_id, Integer status, Integer deployment_id, Integer show_yaml, Integer show_log, Integer id, int page, boolean isMore);
        void getDeployList(Integer app_id, Integer deployment_id, Integer show_yaml);
    }
}