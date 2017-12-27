package com.ten.tencloud.module.user.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.PermissionTreeNodeBean;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.user.contract.PermissionTreeContract;
import com.ten.tencloud.module.user.model.UserModel;

import java.util.List;
import java.util.Map;

/**
 * Created by lxq on 2017/12/14.
 */

public class PermissionTreePresenter extends BasePresenter<PermissionTreeContract.View>
        implements PermissionTreeContract.Presenter<PermissionTreeContract.View> {

    @Override
    public void getTemplateResource(int cid) {
        mSubscriptions.add(UserModel.getInstance().getTemplateResource(cid)
                .subscribe(new JesSubscribe<List<PermissionTreeNodeBean>>(mView) {
                    @Override
                    public void _onSuccess(List<PermissionTreeNodeBean> permissionTreeNodeBean) {
                        mView.showTemplatesAll(permissionTreeNodeBean);
                    }
                }));
    }

    @Override
    public void getTemplate(int ptId) {
        mSubscriptions.add(UserModel.getInstance().getTemplate(ptId)
                .subscribe(new JesSubscribe<PermissionTreeNodeBean>(mView) {
                    @Override
                    public void _onSuccess(PermissionTreeNodeBean permissionTreeNodeBean) {
                        mView.showTemplates(permissionTreeNodeBean);
                    }
                }));
    }


    @Override
    public void updatePermission(int ptId, Map<String, Object> map) {
        mSubscriptions.add(UserModel.getInstance().updatePermissionTemplate(ptId, map)
                .subscribe(new JesSubscribe<Object>(mView) {
                    @Override
                    public void _onSuccess(Object o) {
                        mView.updateSuccess();
                    }
                }));
    }
}
