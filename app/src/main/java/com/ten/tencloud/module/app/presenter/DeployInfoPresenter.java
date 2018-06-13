package com.ten.tencloud.module.app.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.DeploymentBean;
import com.ten.tencloud.bean.DeploymentInfoBean;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.app.contract.AppDeployInfoContract;
import com.ten.tencloud.module.app.contract.AppDeployListContract;
import com.ten.tencloud.module.app.model.AppModel;

import java.util.List;

public class DeployInfoPresenter extends BasePresenter<AppDeployInfoContract.View> implements AppDeployInfoContract.Presenter<AppDeployInfoContract.View>{
    @Override
    public void deploymentPods(int app_id, Integer show_verbos) {
        mSubscriptions.add(AppModel.getInstance().deploymentPods(app_id, show_verbos)
        .subscribe(new JesSubscribe<List<DeploymentInfoBean>>(mView) {
            @Override
            public void _onSuccess(List<DeploymentInfoBean> deploymentInfoBeans) {
                mView.showDeploymentPodsList(deploymentInfoBeans);

            }
        }));

    }

    @Override
    public void deploymentReplicas(int app_id, Integer show_verbos) {
        mSubscriptions.add(AppModel.getInstance().deploymentReplicas(app_id, show_verbos)
                .subscribe(new JesSubscribe<List<DeploymentInfoBean>>(mView) {
                    @Override
                    public void _onSuccess(List<DeploymentInfoBean> deploymentInfoBeans) {

                        mView.showDeploymentReplicasList(deploymentInfoBeans);

                    }
                }));
    }
}
