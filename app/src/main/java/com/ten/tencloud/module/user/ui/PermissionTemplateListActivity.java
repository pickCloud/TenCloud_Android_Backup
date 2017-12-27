package com.ten.tencloud.module.user.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.PermissionTemplateBean;
import com.ten.tencloud.constants.GlobalStatusManager;
import com.ten.tencloud.module.user.adapter.RvTemplateAdapter;
import com.ten.tencloud.module.user.contract.PermissionTemplatesContract;
import com.ten.tencloud.module.user.presenter.PermissionTemplatesPresenter;

import java.util.List;

import butterknife.BindView;

public class PermissionTemplateListActivity extends BaseActivity implements PermissionTemplatesContract.View {

    @BindView(R.id.rv_template)
    RecyclerView mRvTemplate;
    private PermissionTemplatesPresenter mTemplatesPresenter;
    private RvTemplateAdapter mAdapter;
    private int mCid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_permission_template_list);
        initTitleBar(true, "权限模板管理", R.menu.menu_add_template, new OnMenuItemClickListener() {
            @Override
            public void onItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_add_template) {
                    Intent intent = new Intent(mContext, PermissionTemplateNewActivity.class);
                    startActivity(intent);
                }
            }
        });
        mCid = getIntent().getIntExtra("cid", 0);
        mTemplatesPresenter = new PermissionTemplatesPresenter();
        mTemplatesPresenter.attachView(this);
        initView();
    }

    private void initView() {
        mRvTemplate.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RvTemplateAdapter(this);
        mRvTemplate.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CJSBaseRecyclerViewAdapter.OnItemClickListener<PermissionTemplateBean>() {
            @Override
            public void onObjectItemClicked(PermissionTemplateBean permissionTemplateBean, int position) {
                // TODO: 2017/12/25 权限
                Intent intent = new Intent(mContext, PermissionChangeActivity.class);
                intent.putExtra("obj", permissionTemplateBean);
//                intent.putExtra("type", PermissionTreeActivity.TYPE_VIEW);
                startActivity(intent);
            }
        });
        mTemplatesPresenter.getTemplatesByCid(mCid);
    }

    @Override
    public void showEmptyView() {
        showMessage("暂无数据");
        mAdapter.clear();
    }

    @Override
    public void showTemplates(List<PermissionTemplateBean> data) {
        mAdapter.setDatas(data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (GlobalStatusManager.getInstance().isTemplateNeedRefresh()) {
            mTemplatesPresenter.getTemplatesByCid(mCid);
        }
    }
}
