package com.ten.tencloud.module.user.presenter;

import com.qiniu.android.utils.StringUtils;
import com.ten.tencloud.base.presenter.BasePresenter;
import com.ten.tencloud.bean.PermissionTemplate2Bean;
import com.ten.tencloud.bean.PermissionTemplateBean;
import com.ten.tencloud.bean.PermissionTreeNodeBean;
import com.ten.tencloud.bean.UserPermissionAndAll;
import com.ten.tencloud.constants.GlobalStatusManager;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.user.contract.PermissionTreeContract;
import com.ten.tencloud.module.user.model.UserModel;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;

/**
 * Created by lxq on 2017/12/14.
 */

public class PermissionTreePresenter extends BasePresenter<PermissionTreeContract.View>
        implements PermissionTreeContract.Presenter<PermissionTreeContract.View> {

    /**
     * 模板列表
     *
     * @param cid
     */
    @Override
    public void getTemplatesByCid(int cid) {
        mSubscriptions.add(UserModel.getInstance().getTemplatesByCid(cid)
                .subscribe(new JesSubscribe<List<PermissionTemplateBean>>(mView) {
                    @Override
                    public void _onSuccess(List<PermissionTemplateBean> permissionTemplateBeans) {
                        GlobalStatusManager.getInstance().setTemplateNeedRefresh(false);
                        if (permissionTemplateBeans == null || permissionTemplateBeans.size() == 0) {
                            mView.showMessage("暂无模板");
                        } else {
                            mView.showTemplateList(permissionTemplateBeans);
                        }
                    }
                }));
    }

    @Override
    public void getTemplateResource(int cid) {
        mSubscriptions.add(UserModel.getInstance().getTemplateResource(cid)
                .subscribe(new JesSubscribe<List<PermissionTreeNodeBean>>(mView) {
                    @Override
                    public void _onSuccess(List<PermissionTreeNodeBean> permissionTreeNodeBean) {
                        mView.showTemplatesAll(permissionTreeNodeBean);
                    }
                }));
    }

    @Override
    public void getTemplate(int ptId) {
        mSubscriptions.add(UserModel.getInstance().getTemplate(ptId)
                .subscribe(new JesSubscribe<PermissionTreeNodeBean>(mView) {
                    @Override
                    public void _onSuccess(PermissionTreeNodeBean permissionTreeNodeBean) {
                        mView.showTemplates(permissionTreeNodeBean);
                    }
                }));
    }


    @Override
    public void updatePermission(int ptId, PermissionTemplateBean bean) {
        mSubscriptions.add(UserModel.getInstance().updatePermissionTemplate(ptId, bean)
                .subscribe(new JesSubscribe<Object>(mView) {
                    @Override
                    public void _onSuccess(Object o) {
                        mView.updateSuccess();
                    }
                }));
    }

    @Override
    public void getUserPermission(final int uid) {
        final int cid = AppBaseCache.getInstance().getSelectCompanyWithLogin().getCid();
        mSubscriptions.add(Observable.zip(
                UserModel.getInstance().getUserPermission(cid, uid),
                UserModel.getInstance().getTemplateResource(cid),
                new Func2<PermissionTemplate2Bean, List<PermissionTreeNodeBean>, UserPermissionAndAll>() {
                    @Override
                    public UserPermissionAndAll call(PermissionTemplate2Bean permissionTemplate2Bean,
                                                     List<PermissionTreeNodeBean> permissionTreeNodeBeans) {
                        return new UserPermissionAndAll(permissionTemplate2Bean, permissionTreeNodeBeans);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new JesSubscribe<UserPermissionAndAll>(mView) {
                    @Override
                    public void _onSuccess(UserPermissionAndAll o) {
                        PermissionTemplate2Bean userPermission = o.getUserPermission();
                        PermissionTemplateBean exist = new PermissionTemplateBean();
                        exist.setCid(cid);
                        exist.setUid(uid);

                        exist.setPermissions(handPermissionIdToString(userPermission.getPermissions(), false));
                        exist.setAccess_filehub(handPermissionIdToString(userPermission.getAccess_filehub(), false));
                        exist.setAccess_projects(handPermissionIdToString(userPermission.getAccess_projects(), false));
                        exist.setAccess_servers(handPermissionIdToString(userPermission.getAccess_servers(), true));

                        mView.showExistPermission(exist);
                        mView.showTemplatesAll(o.getAll());
                    }
                }));
    }

    @Override
    public void viewUserPermission(int uid) {
        int cid = AppBaseCache.getInstance().getSelectCompanyWithLogin().getCid();
        mSubscriptions.add(UserModel.getInstance().viewUserPermission(cid, uid)
                .subscribe(new JesSubscribe<List<PermissionTreeNodeBean>>(mView) {
                    @Override
                    public void _onSuccess(List<PermissionTreeNodeBean> permissionTreeNodeBean) {
                        PermissionTreeNodeBean nodes = new PermissionTreeNodeBean();
                        nodes.setData(permissionTreeNodeBean);
                        mView.showTemplates(nodes);
                    }
                }));
    }

    @Override
    public void updateUserPermission(PermissionTemplateBean bean) {
        mSubscriptions.add(UserModel.getInstance().updateUserPermission(bean)
                .subscribe(new JesSubscribe<Object>(mView) {
                    @Override
                    public void _onSuccess(Object o) {
                        mView.updateUserPermissionSuccess();
                    }
                }));
    }

    private String handPermissionIdToString(List<PermissionTemplate2Bean.ID> ids, boolean isServer) {
        String[] permission = new String[ids.size()];
        for (int i = 0; i < ids.size(); i++) {
            int id;
            if (isServer) {
                id = ids.get(i).getSid();
            } else {
                id = ids.get(i).getId();
            }
            permission[i] = id + "";
        }
        return StringUtils.join(permission, ",");
    }
}
