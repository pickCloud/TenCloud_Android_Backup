package com.ten.tencloud.module.user.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.PermissionTemplateBean;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.constants.GlobalStatusManager;
import com.ten.tencloud.module.user.adapter.RvTemplateAdapter;
import com.ten.tencloud.module.user.contract.PermissionTemplatesContract;
import com.ten.tencloud.module.user.presenter.PermissionTemplatesPresenter;
import com.ten.tencloud.utils.Utils;
import com.ten.tencloud.widget.popup.DelPopupWindow;

import java.util.List;

import butterknife.BindView;

public class PermissionTemplateListActivity extends BaseActivity implements PermissionTemplatesContract.View {

    @BindView(R.id.rv_template)
    RecyclerView mRvTemplate;
    private PermissionTemplatesPresenter mTemplatesPresenter;
    private RvTemplateAdapter mAdapter;
    private int mCid;
    private boolean mIsPermissionAdd;
    private boolean mIsPermissionChange;
    private boolean mIsPermissionDel;
    private DelPopupWindow mDelPopupWindow;

    private PermissionTemplateBean delTempBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_permission_template_list);
        initPermission();
        if (mIsPermissionAdd) {
            initTitleBar(true, "权限模版管理", R.menu.menu_add_template, new OnMenuItemClickListener() {
                @Override
                public void onItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.menu_add_template) {
                        Intent intent = new Intent(mContext, PermissionTemplateNewActivity.class);
                        startActivityForResult(intent, 0);
                    }
                }
            });
        } else {
            initTitleBar(true, "权限模版管理");
        }
        mCid = getIntent().getIntExtra("cid", 0);
        mTemplatesPresenter = new PermissionTemplatesPresenter();
        mTemplatesPresenter.attachView(this);
        initView();
    }

    private void initPermission() {
        mIsPermissionAdd = Utils.hasPermission("新增权限模版");
        mIsPermissionChange = Utils.hasPermission("修改权限模版");
        mIsPermissionDel = Utils.hasPermission("删除模版");
    }

    private void initView() {
        mRvTemplate.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RvTemplateAdapter(this);
        mRvTemplate.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CJSBaseRecyclerViewAdapter.OnItemClickListener<PermissionTemplateBean>() {
            @Override
            public void onObjectItemClicked(PermissionTemplateBean permissionTemplateBean, int position) {
                if (mIsPermissionChange && permissionTemplateBean.getId() != 0) {
                    Intent intent = new Intent(mContext, PermissionChangeActivity.class);
                    intent.putExtra("obj", permissionTemplateBean);
                    startActivityForResult(intent, 0);
                } else {
                    Intent intent = new Intent(mContext, PermissionTreeActivity.class);
                    intent.putExtra("obj", permissionTemplateBean);
                    intent.putExtra("type", PermissionTreeActivity.TYPE_VIEW);
                    startActivityForResult(intent, 0);
                }

            }
        });
        mAdapter.setOnItemLongClickListener(new CJSBaseRecyclerViewAdapter.OnItemLongClickListener<PermissionTemplateBean>() {
            @Override
            public void onObjectItemLongClicked(final PermissionTemplateBean bean, View view, int position) {
                //删除
                if (mIsPermissionDel && bean.getId() != 0) {
                    mDelPopupWindow = new DelPopupWindow(mContext);
                    mDelPopupWindow.setOnButtonClickListener(new DelPopupWindow.OnButtonClickListener() {
                        @Override
                        public void onDelClick() {
                            mTemplatesPresenter.delTemplate(bean.getId());
                        }
                    });

                    int[] location = new int[2];
                    view.getLocationOnScreen(location);
                    int x = (view.getWidth() / 2) - (mDelPopupWindow.getWidth() / 2);
                    int y = location[1] + (view.getHeight() / 2);
                    mDelPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, x, y);
                }
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
    public void delSuccess() {
        mTemplatesPresenter.getTemplatesByCid(mCid);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Constants.ACTIVITY_RESULT_CODE_REFRESH) {
            mTemplatesPresenter.getTemplatesByCid(mCid);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (GlobalStatusManager.getInstance().isTemplateNeedRefresh()) {
            mTemplatesPresenter.getTemplatesByCid(mCid);
        }
    }
}
