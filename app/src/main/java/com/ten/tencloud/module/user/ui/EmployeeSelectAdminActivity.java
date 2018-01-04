package com.ten.tencloud.module.user.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.CompanyBean;
import com.ten.tencloud.bean.EmployeeBean;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.module.user.adapter.RvEmployeeSelectAdminAdapter;
import com.ten.tencloud.module.user.contract.EmployeeListContract;
import com.ten.tencloud.module.user.contract.EmployeeTransferAdminContract;
import com.ten.tencloud.module.user.presenter.EmployeesListPresenter;
import com.ten.tencloud.module.user.presenter.EmployeesTransferAdminPresenter;

import java.util.List;

import butterknife.BindView;

public class EmployeeSelectAdminActivity extends BaseActivity
        implements EmployeeListContract.View, EmployeeTransferAdminContract.View {

    @BindView(R.id.rv_employee)
    RecyclerView mRvEmployee;

    private EmployeesListPresenter mEmployeesListPresenter;
    private RvEmployeeSelectAdminAdapter mAdapter;
    private EmployeesTransferAdminPresenter mEmployeesTransferAdminPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_employee_select_admin);
        initTitleBar("选择", R.mipmap.icon_close, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }, "确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmployeeBean selectObject = mAdapter.getSelectObject();
                mEmployeesTransferAdminPresenter.transferAdmin(selectObject.getUid());
            }
        });
        initView();
    }

    private void initView() {
        mEmployeesListPresenter = new EmployeesListPresenter();
        mEmployeesListPresenter.attachView(this);
        mEmployeesTransferAdminPresenter = new EmployeesTransferAdminPresenter();
        mEmployeesTransferAdminPresenter.attachView(this);
        mRvEmployee.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RvEmployeeSelectAdminAdapter(this);
        mRvEmployee.setAdapter(mAdapter);
        CompanyBean companyBean = AppBaseCache.getInstance().getSelectCompanyWithLogin();
        mEmployeesListPresenter.getEmployees(companyBean.getCid());
    }

    @Override
    public void showEmployees(List<EmployeeBean> employees) {
        mAdapter.setDatas(employees);
    }

    @Override
    public void showEmpty() {
        mAdapter.clear();
        showMessage("暂无数据");
    }

    @Override
    public void transferSuccess() {
        showMessage("更换成功");
        finish();
    }
}
