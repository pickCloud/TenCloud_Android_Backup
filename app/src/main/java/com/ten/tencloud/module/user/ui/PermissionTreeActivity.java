package com.ten.tencloud.module.user.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.base.view.BasePager;
import com.ten.tencloud.bean.PermissionTemplateBean;
import com.ten.tencloud.bean.PermissionTreeNodeBean;
import com.ten.tencloud.module.user.adapter.VpPermissionAdapter;
import com.ten.tencloud.module.user.contract.PermissionTreeContract;
import com.ten.tencloud.module.user.presenter.PermissionTreePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class PermissionTreeActivity extends BaseActivity implements PermissionTreeContract.View {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_permission_tree);
        mTemplateBean = getIntent().getParcelableExtra("obj");
        initTitleBar("模板权限项选择", R.mipmap.icon_close, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }, "确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Integer[]> selectNode = mFuncPager.getSelectNode();
            }
        });
        initView();
    }

    private void initView() {
        mTreePresenter = new PermissionTreePresenter();
        mTreePresenter.attachView(this);
        mTreePresenter.getTemplateResource(mTemplateBean.getCid());
    }

    @Override
    public void showTemplates(List<PermissionTreeNodeBean> data) {
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
}
