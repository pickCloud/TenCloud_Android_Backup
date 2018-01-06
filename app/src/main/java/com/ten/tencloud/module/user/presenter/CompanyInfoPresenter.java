package com.ten.tencloud.module.user.presenter;

import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.CompanyBean;
import com.ten.tencloud.constants.GlobalStatusManager;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.user.contract.CompanyInfoContract;
import com.ten.tencloud.module.user.model.UserModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lxq on 2017/12/14.
 */

public class CompanyInfoPresenter extends BasePresenter<CompanyInfoContract.View>
        implements CompanyInfoContract.Presenter<CompanyInfoContract.View> {
    @Override
    public void getCompanyByCid(int cid) {
        mSubscriptions.add(UserModel.getInstance().getCompanyInfoByCid(cid)
                .subscribe(new JesSubscribe<List<CompanyBean>>(mView) {
                    @Override
                    public void _onSuccess(List<CompanyBean> companyBean) {
                        if (companyBean != null && companyBean.size() > 0) {
                            mView.showCompanyInfo(companyBean.get(0));
                        }
                    }
                }));
    }

    @Override
    public void updateCompanyInfo(int cid, String name, String contact, String mobile, String image) {
        Map<String, Object> map = new HashMap<>();
        map.put("cid", cid);
        map.put("name", name);
        map.put("contact", contact);
        map.put("mobile", mobile);
        map.put("image_url", image);

        mSubscriptions.add(UserModel.getInstance().updateCompanyInfo(map)
                .subscribe(new JesSubscribe<Object>(mView) {
                    @Override
                    public void _onSuccess(Object o) {
                        GlobalStatusManager.getInstance().setCompanyListNeedRefresh(true);
                        GlobalStatusManager.getInstance().setUserInfoNeedRefresh(true);
                        mView.updateSuccess();
                    }
                }));
    }
}
