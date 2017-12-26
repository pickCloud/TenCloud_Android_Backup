package com.ten.tencloud.module.user.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.PermissionTemplateBean;

import java.util.List;

/**
 * Created by lxq on 2017/12/14.
 */
public class PermissionTemplatesContract {
    public interface View extends IBaseView {

        void showEmptyView();

        void showTemplates(List<PermissionTemplateBean> data);
    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {

        void getTemplatesByCid(int cid);

    }
}
