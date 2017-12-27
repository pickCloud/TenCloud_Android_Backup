package com.ten.tencloud.module.user.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.PermissionTemplateBean;

/**
 * Created by lxq on 2017/12/14.
 */
public class PermissionNewContract {
    public interface View extends IBaseView {

        void addSuccess();
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {

        void addTemplate(PermissionTemplateBean bean);

    }
}
