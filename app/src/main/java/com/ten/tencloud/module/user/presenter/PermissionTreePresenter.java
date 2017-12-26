package com.ten.tencloud.module.user.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.PermissionTreeNodeBean;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.user.contract.PermissionTreeContract;
import com.ten.tencloud.module.user.model.UserModel;

import java.util.List;

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
                        mView.showTemplates(permissionTreeNodeBean);
                    }
                }));
    }
}
