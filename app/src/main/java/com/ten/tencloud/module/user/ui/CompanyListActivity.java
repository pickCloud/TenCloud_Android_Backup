package com.ten.tencloud.module.user.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.CompanyBean;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.module.user.adapter.RvCompanyAdapter;
import com.ten.tencloud.module.user.contract.CompanyListContract;
import com.ten.tencloud.module.user.presenter.CompanyListPresenter;

import java.util.List;

import butterknife.BindView;

public class CompanyListActivity extends BaseActivity implements CompanyListContract.View {

    @BindView(R.id.rv_company)
    RecyclerView mRvCompany;
    private CompanyListPresenter mCompanyListPresenter;
    private RvCompanyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_company_list);
        initTitleBar(true, "我的企业", R.menu.menu_add, new OnMenuItemClickListener() {
            @Override
            public void onItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_add_server:
                        Intent intent = new Intent(mContext, CompanyNewActivity.class);
                        startActivityForResult(intent, 0);
                        break;
                }
            }
        });
        mCompanyListPresenter = new CompanyListPresenter();
        mCompanyListPresenter.attachView(this);
        initView();
    }

    private void initView() {
        mRvCompany.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RvCompanyAdapter(this);
        mRvCompany.setAdapter(mAdapter);
        mCompanyListPresenter.getCompanies();
    }

    @Override
    public void showCompanies(List<CompanyBean> companies) {
        mAdapter.setDatas(companies);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Constants.ACTIVITY_RESULT_CODE_REFRESH){
            mCompanyListPresenter.getCompanies();
        }
    }
}
