package com.ten.tencloud.module.user.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.user.contract.PermissionRenameContract;
import com.ten.tencloud.module.user.model.UserModel;

/**
 * Created by lxq on 2017/12/14.
 */

public class PermissionRenamePresenter extends BasePresenter<PermissionRenameContract.View>
        implements PermissionRenameContract.Presenter<PermissionRenameContract.View> {

    @Override
    public void renameTemplate(int ptId, int cid, String name) {
        mSubscriptions.add(UserModel.getInstance().renameTemplate(ptId, cid, name)
                .subscribe(new JesSubscribe<Object>(mView) {
                    @Override
                    public void _onSuccess(Object o) {
                        mView.success();
                    }
                }));
    }
}
