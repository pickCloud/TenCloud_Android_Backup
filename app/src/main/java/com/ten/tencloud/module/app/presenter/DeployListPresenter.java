package com.ten.tencloud.module.app.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.DeploymentBean;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.app.contract.AppDeployListContract;
import com.ten.tencloud.module.app.contract.AppListContract;
import com.ten.tencloud.module.app.contract.AppServiceHomeContract;
import com.ten.tencloud.module.app.model.AppModel;

import java.util.List;

public class DeployListPresenter extends BasePresenter<AppDeployListContract.View> implements AppDeployListContract.Presenter<AppDeployListContract.View>{
    @Override
    public void getDeployList(Integer app_id, Integer status, Integer deployment_id, Integer show_yaml, Integer show_log, Integer id, int page, boolean isMore) {
        mSubscriptions.add(AppModel.getInstance().getDeployList(app_id, status, deployment_id, show_yaml, show_log, page)
        .subscribe(new JesSubscribe<List<DeploymentBean>>(mView) {
            @Override
            public void _onSuccess(List<DeploymentBean> deploymentBeans) {
//                if (deploymentBeans == null || deploymentBeans.size() == 0) {
//                    mView.showEmpty();
//                    return;
//                }
                mView.showList(deploymentBeans);
            }
        }));
    }

    @Override
    public void getDeployList(Integer app_id, Integer deployment_id, Integer show_yaml) {
        mSubscriptions.add(AppModel.getInstance().getDeployList(app_id, deployment_id, show_yaml)
                .subscribe(new JesSubscribe<List<DeploymentBean>>(mView) {
                    @Override
                    public void _onSuccess(List<DeploymentBean> deploymentBeans) {
                        if (deploymentBeans == null || deploymentBeans.size() == 0) {
                            mView.showEmpty();
                            return;
                        }
                        mView.showList(deploymentBeans);
                    }
                }));
    }
}
