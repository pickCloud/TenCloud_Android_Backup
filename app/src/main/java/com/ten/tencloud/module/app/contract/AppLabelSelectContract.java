package com.ten.tencloud.module.app.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.LabelBean;

import java.util.HashSet;
import java.util.TreeSet;

/**
 * Create by chenxh@10.com on 2018/4/2.
 */
public class AppLabelSelectContract {

    public interface View extends IBaseView {

        void labelAddResult(boolean result);

        void showEmpty();

        void showLabelList(TreeSet<LabelBean> labelBeans);

    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {

        void newLabel(String name, int type);

        void getLabelList(int type);

    }
}
