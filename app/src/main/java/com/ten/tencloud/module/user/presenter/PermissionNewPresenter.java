package com.ten.tencloud.module.user.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.PermissionTemplateBean;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.user.contract.PermissionNewContract;
import com.ten.tencloud.module.user.model.UserModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lxq on 2017/12/14.
 */

public class PermissionNewPresenter extends BasePresenter<PermissionNewContract.View>
        implements PermissionNewContract.Presenter<PermissionNewContract.View> {

    @Override
    public void addTemplate(PermissionTemplateBean bean) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", bean.getName());
        map.put("cid", bean.getCid());
        map.put("permissions", bean.getPermissions());
        map.put("access_servers", bean.getAccess_servers());
        map.put("access_projects", bean.getAccess_projects());
        map.put("access_filehub", bean.getAccess_filehub());
        mSubscriptions.add(UserModel.getInstance().addTemplate(map)
                .subscribe(new JesSubscribe<Object>(mView) {
                    @Override
                    public void _onSuccess(Object o) {
                        mView.addSuccess();
                    }
                }));
    }
}
