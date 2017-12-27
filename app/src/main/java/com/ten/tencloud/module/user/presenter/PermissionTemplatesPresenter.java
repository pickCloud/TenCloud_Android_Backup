package com.ten.tencloud.module.user.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.PermissionTemplateBean;
import com.ten.tencloud.constants.GlobalStatusManager;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.user.contract.PermissionTemplatesContract;
import com.ten.tencloud.module.user.model.UserModel;

import java.util.List;

/**
 * Created by lxq on 2017/12/14.
 */

public class PermissionTemplatesPresenter extends BasePresenter<PermissionTemplatesContract.View>
        implements PermissionTemplatesContract.Presenter<PermissionTemplatesContract.View> {

    @Override
    public void getTemplatesByCid(int cid) {
        mSubscriptions.add(UserModel.getInstance().getTemplatesByCid(cid)
                .subscribe(new JesSubscribe<List<PermissionTemplateBean>>(mView) {
                    @Override
                    public void _onSuccess(List<PermissionTemplateBean> permissionTemplateBeans) {
                        GlobalStatusManager.getInstance().setTemplateNeedRefresh(false);
                        if (permissionTemplateBeans == null || permissionTemplateBeans.size() == 0) {
                            mView.showEmptyView();
                        } else {
                            mView.showTemplates(permissionTemplateBeans);
                        }
                    }
                }));
    }
}
