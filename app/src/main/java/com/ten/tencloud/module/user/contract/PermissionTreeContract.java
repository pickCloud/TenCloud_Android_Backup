package com.ten.tencloud.module.user.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.PermissionTreeNodeBean;

import java.util.List;
import java.util.Map;

/**
 * Created by lxq on 2017/12/14.
 */
public class PermissionTreeContract {
    public interface View extends IBaseView {
        void showTemplatesAll(List<PermissionTreeNodeBean> data);

        void showTemplates(PermissionTreeNodeBean data);

        void updateSuccess();
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {

        void getTemplateResource(int cid);

        void getTemplate(int ptId);

        void updatePermission(int ptId, Map<String, Object> map);

    }
}
