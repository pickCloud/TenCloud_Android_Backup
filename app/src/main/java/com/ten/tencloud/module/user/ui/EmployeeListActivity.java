package com.ten.tencloud.module.user.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.EmployeeBean;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.module.user.adapter.RvEmployeeAdapter;
import com.ten.tencloud.module.user.contract.EmployeeListContract;
import com.ten.tencloud.module.user.presenter.EmployeesListPresenter;

import java.util.List;

import butterknife.BindView;

public class EmployeeListActivity extends BaseActivity implements EmployeeListContract.View {

    @BindView(R.id.rv_employee)
    RecyclerView mRvEmployee;
    private EmployeesListPresenter mEmployeesListPresenter;
    private RvEmployeeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_employee_list);
        initTitleBar(true, "员工列表", R.menu.menu_add, new OnMenuItemClickListener() {
            @Override
            public void onItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_add) {
                    // TODO: 2018/1/2  popup
                }
            }
        });
        initView();
    }

    private void initView() {
        mEmployeesListPresenter = new EmployeesListPresenter();
        mEmployeesListPresenter.attachView(this);

        mRvEmployee.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RvEmployeeAdapter(this);
        mAdapter.setOnItemClickListener(new CJSBaseRecyclerViewAdapter.OnItemClickListener<EmployeeBean>() {
            @Override
            public void onObjectItemClicked(EmployeeBean employeeBean, int position) {
                Intent intent = new Intent(mContext, EmployeeInfoActivity.class);
                intent.putExtra("obj", employeeBean);
                startActivity(intent);
            }
        });
        mRvEmployee.setAdapter(mAdapter);
        mEmployeesListPresenter.getEmployees(AppBaseCache.getInstance().getCid());
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
}
