package com.ten.tencloud.module.user.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.PermissionTreeNodeBean;

import java.util.List;

/**
 * Created by lxq on 2017/12/14.
 */
public class PermissionTreeContract {
    public interface View extends IBaseView {
        void showTemplates(List<PermissionTreeNodeBean> data);
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {

        void getTemplateResource(int cid);

    }
}
