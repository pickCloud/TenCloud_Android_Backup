package com.ten.tencloud.module.main.contract;

import com.ten.tencloud.base.presenter.IBasePresenter;
import com.ten.tencloud.base.view.IBaseView;
import com.ten.tencloud.bean.MessageBean;

import java.util.List;

/**
 * Created by lxq on 2018/1/2.
 */

public class MsgContract {
    public interface View extends IBaseView {
        void showEmpty(boolean isLoadMore);

        void showMsgList(List<MessageBean> msg, boolean isLoadMore);

        void jumpPage(boolean isEmployee);

    }

    public interface Presenter<V extends IBaseView> extends IBasePresenter<V> {
        void getMsgList(boolean isLoadMore, String status, String mode);

        void search(String status, String mode, String key);

        void checkCompany(int cid);
    }
}
