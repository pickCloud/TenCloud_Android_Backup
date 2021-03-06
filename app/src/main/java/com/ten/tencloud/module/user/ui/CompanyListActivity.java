package com.ten.tencloud.module.user.ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.CompanyBean;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.constants.GlobalStatusManager;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.module.login.ui.JoinComStep2Activity;
import com.ten.tencloud.module.user.adapter.RvCompanyAdapter;
import com.ten.tencloud.module.user.contract.CompanyListContract;
import com.ten.tencloud.module.user.presenter.CompanyListPresenter;
import com.ten.tencloud.utils.UiUtils;
import com.ten.tencloud.widget.dialog.CommonDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CompanyListActivity extends BaseActivity implements CompanyListContract.View {

    @BindView(R.id.rv_company)
    RecyclerView mRvCompany;
    @BindView(R.id.empty_view)
    View mEmptyView;
    private CompanyListPresenter mCompanyListPresenter;
    private RvCompanyAdapter mAdapter;
    private PopupWindow mMenuPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_company_list);
        initTitleBar(true, "我的公司", R.menu.menu_add, new OnMenuItemClickListener() {
            @Override
            public void onItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_add:
                        showMenuPopup();
                        break;
                }
            }
        });
        mCompanyListPresenter = new CompanyListPresenter();
        mCompanyListPresenter.attachView(this);
        initView();
    }

    private void showMenuPopup() {
        if (mMenuPopupWindow == null) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.pop_company, null);
            View joinView = view.findViewById(R.id.tv_join);
            joinView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityNoValue(mContext, EmployeeJoinActivity.class);
                    mMenuPopupWindow.dismiss();
                }
            });
            View addView = view.findViewById(R.id.tv_add);
            addView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, CompanyNewActivity.class);
                    startActivityForResult(intent, 0);
                    mMenuPopupWindow.dismiss();
                }
            });
            mMenuPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            mMenuPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            mMenuPopupWindow.setFocusable(true);
            mMenuPopupWindow.setOutsideTouchable(true);
        }
        int dx = UiUtils.dip2px(this, 5);
        int dy = UiUtils.dip2px(this, 80);
        mMenuPopupWindow.showAtLocation(mRvCompany, Gravity.RIGHT | Gravity.TOP, dx, dy);
    }

    private void initView() {
        mRvCompany.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RvCompanyAdapter(this);
        mAdapter.setOnItemClickListener(new CJSBaseRecyclerViewAdapter.OnItemClickListener<CompanyBean>() {
            @Override
            public void onObjectItemClicked(final CompanyBean companyBean, int position) {
                int status = companyBean.getStatus();
                if (status == Constants.EMPLOYEE_STATUS_CODE_PASS || status == Constants.EMPLOYEE_STATUS_CODE_CREATE) {
                    new CommonDialog(mContext)
                            .setMessage("确定切换企业身份？")
                            .setPositiveButton("确定", new CommonDialog.OnButtonClickListener() {
                                @Override
                                public void onClick(Dialog dialog) {
                                    AppBaseCache.getInstance().setCid(companyBean.getCid());
                                    GlobalStatusManager.getInstance().setCompanyListNeedRefresh(true);
                                    dialog.dismiss();
                                    TenApp.getInstance().jumpMainActivity();
                                }
                            })
                            .show();
                } else if (status == Constants.EMPLOYEE_STATUS_CODE_NO_PASS || status == Constants.EMPLOYEE_STATUS_CODE_WAITING) {
                    new CommonDialog(mContext)
                            .setMessage("重新提交申请？")
                            .setPositiveButton("确定", new CommonDialog.OnButtonClickListener() {
                                @Override
                                public void onClick(Dialog dialog) {
                                    Intent intent = new Intent(mContext, JoinComStep2Activity.class);
                                    intent.putExtra("code", companyBean.getCode());
                                    startActivity(intent);
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
            }
        });
        mRvCompany.setAdapter(mAdapter);
        mCompanyListPresenter.getCompanies();
    }

    @OnClick({R.id.tv_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add:
                startActivityNoValue(this, CompanyNewActivity.class);
                break;
        }
    }

    @Override
    public void showCompanies(List<CompanyBean> companies) {
        mAdapter.setDatas(companies);
        mRvCompany.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showEmptyView() {
        mRvCompany.setVisibility(View.INVISIBLE);
        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Constants.ACTIVITY_RESULT_CODE_REFRESH) {
            mCompanyListPresenter.getCompanies();
        }
    }
}
