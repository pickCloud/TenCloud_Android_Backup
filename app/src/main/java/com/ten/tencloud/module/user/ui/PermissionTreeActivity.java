package com.ten.tencloud.module.user.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;

import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.base.view.BasePager;
import com.ten.tencloud.bean.PermissionTemplateBean;
import com.ten.tencloud.bean.PermissionTreeNodeBean;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.module.user.adapter.VpPermissionAdapter;
import com.ten.tencloud.module.user.contract.PermissionTreeContract;
import com.ten.tencloud.module.user.presenter.PermissionTreePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class PermissionTreeActivity extends BaseActivity implements PermissionTreeContract.View {

    public static final int TYPE_UPDATE = 0;
    public static final int TYPE_VIEW = 1;
    public static final int TYPE_NEW = 3;

    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.vp_content)
    ViewPager mVpContent;

    String[] titles = {"功能", "数据"};

    private PermissionTemplateBean mTemplateBean;
    private VpPermissionAdapter mAdapter;
    private PermissionTreePresenter mTreePresenter;
    private PermissionTreePager mFuncPager;
    private PermissionTreePager mDataPager;

    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_permission_tree);
        mTemplateBean = getIntent().getParcelableExtra("obj");
        type = getIntent().getIntExtra("type", TYPE_UPDATE);
        if (type == TYPE_VIEW) {
            initTitleBar(true, "查看权限", mTemplateBean.getName());
        } else {
            initTitleBar("模板权限项选择", R.mipmap.icon_close, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            }, "确认", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mFuncPager == null || mDataPager == null) {
                        return;
                    }
                    Map<String, String> funcSelectNode = mFuncPager.getSelectNode();
                    Map<String, String> dataSelectNode = mDataPager.getSelectNode();
                    dataSelectNode.putAll(funcSelectNode);
                    if (type == TYPE_NEW) {
                        Intent data = new Intent();
                        data.putExtra("json", TenApp.getInstance()
                                .getGsonInstance().toJson(dataSelectNode));
                        setResult(Constants.ACTIVITY_RESULT_CODE_REFRESH, data);
                        finish();
                    } else if (type == TYPE_UPDATE) {
                        mTemplateBean.setPermissions(dataSelectNode.get("permissions"));
                        mTemplateBean.setAccess_filehub(dataSelectNode.get("access_filehub"));
                        mTemplateBean.setAccess_projects(dataSelectNode.get("access_projects"));
                        mTemplateBean.setAccess_servers(dataSelectNode.get("access_servers"));
                        mTreePresenter.updatePermission(mTemplateBean.getId(), mTemplateBean);
                    }
                }
            });
        }
        initView();
    }

    private void initView() {
        mTreePresenter = new PermissionTreePresenter();
        mTreePresenter.attachView(this);
        if (type == TYPE_VIEW) {
            //查看具体模板的权限
            mTreePresenter.getTemplate(AppBaseCache.getInstance().getCid());
        } else {
            //更新权限
            mTreePresenter.getTemplateResource(AppBaseCache.getInstance().getCid());
        }
    }

    @Override
    public void showTemplatesAll(List<PermissionTreeNodeBean> data) {
        ArrayList<BasePager> pagers = new ArrayList<>();
        mFuncPager = new PermissionTreePager(this);
        mFuncPager.putArgument("resource", data.get(0).getData());
        mFuncPager.putArgument("select", mTemplateBean);
        mFuncPager.putArgument("type", PermissionTreePager.TYPE_FUNC);
        mFuncPager.initView();
        pagers.add(mFuncPager);
        mDataPager = new PermissionTreePager(this);
        mDataPager.putArgument("resource", data.get(1).getData());
        mDataPager.putArgument("select", mTemplateBean);
        mDataPager.putArgument("type", PermissionTreePager.TYPE_DATA);
        mDataPager.initView();
        pagers.add(mDataPager);
        mAdapter = new VpPermissionAdapter(titles, pagers);
        mVpContent.setOffscreenPageLimit(pagers.size());
        mVpContent.setAdapter(mAdapter);
        mTab.setupWithViewPager(mVpContent);
    }

    @Override
    public void showTemplates(PermissionTreeNodeBean data) {
        ArrayList<BasePager> pagers = new ArrayList<>();
        mFuncPager = new PermissionTreePager(this);
        mFuncPager.putArgument("resource", data.getData().get(0).getData());
        mFuncPager.putArgument("type", PermissionTreePager.TYPE_FUNC);
        mFuncPager.putArgument("isNew", true);
        mFuncPager.initView();
        pagers.add(mFuncPager);
        mDataPager = new PermissionTreePager(this);
        mDataPager.putArgument("resource", data.getData().get(1).getData());
        mDataPager.putArgument("type", PermissionTreePager.TYPE_DATA);
        mDataPager.putArgument("isNew", true);
        mDataPager.initView();
        pagers.add(mDataPager);
        int funcCount = handCount(mTemplateBean.getPermissions());
        int dataCount = handCount(mTemplateBean.getAccess_servers())
                + handCount(mTemplateBean.getAccess_projects())
                + handCount(mTemplateBean.getAccess_filehub());
        titles[0] = "功能(" + funcCount + ")";
        titles[1] = "数据(" + dataCount + ")";
        mAdapter = new VpPermissionAdapter(titles, pagers);
        mVpContent.setOffscreenPageLimit(pagers.size());
        mVpContent.setAdapter(mAdapter);
        mTab.setupWithViewPager(mVpContent);
    }

    @Override
    public void updateSuccess() {
        showMessage("模板权限更新成功");
        Intent data = new Intent();
        data.putExtra("obj", mTemplateBean);
        setResult(Constants.ACTIVITY_RESULT_CODE_REFRESH, data);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        if (TYPE_UPDATE == type || TYPE_VIEW == type) {
            overridePendingTransition(0, R.anim.slide_in_bttom);
        }
    }

    private int handCount(String permission) {
        if (TextUtils.isEmpty(permission)) {
            return 0;
        }
        return permission.split(",").length;
    }

}