package com.ten.tencloud.module.app.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.DeploymentBean;
import com.ten.tencloud.bean.DeploymentInfoBean;

import java.util.List;

/**
 * Create by chenxh@10.com on 2018/4/3.
 */
public class AppDeployInfoContract {

    public interface View extends IBaseView {
        void showEmpty();

        void showDeploymentPodsList(List<DeploymentInfoBean> data);
        void showDeploymentReplicasList(List<DeploymentInfoBean> data);
    }

   public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {

        void deploymentPods(int app_id, Integer show_verbos);
        void deploymentReplicas(int app_id, Integer show_verbos);
    }
}