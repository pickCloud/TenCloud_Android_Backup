package com.ten.tencloud.module.user.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import com.ten.tencloud.module.user.adapter.RvCompanyAdapter;
import com.ten.tencloud.module.user.contract.CompanyListContract;
import com.ten.tencloud.module.user.presenter.CompanyListPresenter;
import com.ten.tencloud.utils.UiUtils;

import java.util.List;

import butterknife.BindView;

public class CompanyListActivity extends BaseActivity implements CompanyListContract.View {

    @BindView(R.id.rv_company)
    RecyclerView mRvCompany;
    private CompanyListPresenter mCompanyListPresenter;
    private RvCompanyAdapter mAdapter;
    private PopupWindow mMenuPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_company_list);
        initTitleBar(true, "我的企业", R.menu.menu_add, new OnMenuItemClickListener() {
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
                    AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                            .setMessage("你确认要切换企业账号吗？")
                            .setNegativeButton("取消", null)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    AppBaseCache.getInstance().setCid(companyBean.getCid());
                                    GlobalStatusManager.getInstance().setCompanyListNeedRefresh(true);
                                    dialog.dismiss();
                                    TenApp.getInstance().jumpMainActivity();
                                }
                            })
                            .create();
                    alertDialog.show();
                }
            }
        });
        mRvCompany.setAdapter(mAdapter);
        mCompanyListPresenter.getCompanies();
    }

    @Override
    public void showCompanies(List<CompanyBean> companies) {
        mAdapter.setDatas(companies);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Constants.ACTIVITY_RESULT_CODE_REFRESH) {
            mCompanyListPresenter.getCompanies();
        }
    }
}
