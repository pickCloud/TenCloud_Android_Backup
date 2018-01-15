package com.ten.tencloud.module.user.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.EmployeeBean;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.constants.GlobalStatusManager;
import com.ten.tencloud.module.user.adapter.RvEmployeeSelectAdminAdapter;
import com.ten.tencloud.module.user.contract.EmployeeListContract;
import com.ten.tencloud.module.user.contract.EmployeeTransferAdminContract;
import com.ten.tencloud.module.user.model.EmployeesModel;
import com.ten.tencloud.module.user.presenter.EmployeesListPresenter;
import com.ten.tencloud.module.user.presenter.EmployeesTransferAdminPresenter;

import java.util.List;

import butterknife.BindView;

public class EmployeeSelectAdminActivity extends BaseActivity
        implements EmployeeListContract.View, EmployeeTransferAdminContract.View {

    @BindView(R.id.rv_employee)
    RecyclerView mRvEmployee;
    @BindView(R.id.et_search)
    EditText mEtSearch;

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
                if (selectObject == null) {
                    showMessage("请选择员工");
                    return;
                }
                mEmployeesTransferAdminPresenter.transferAdmin(selectObject.getId());
            }
        });
        initView();
    }

    private void initView() {
        mEmployeesListPresenter = new EmployeesListPresenter();
        mEmployeesListPresenter.attachView(this);
        mEmployeesTransferAdminPresenter = new EmployeesTransferAdminPresenter();
        mEmployeesTransferAdminPresenter.attachView(this);

        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search();
                }
                return false;
            }
        });

        mRvEmployee.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RvEmployeeSelectAdminAdapter(this);
        mRvEmployee.setAdapter(mAdapter);
        mEmployeesListPresenter.getEmployees("", EmployeesModel.STATUS_EMPLOYEE_SEARCH_PASS);
    }

    private void search() {
        String search = mEtSearch.getText().toString().trim();
        mEmployeesListPresenter.getEmployees(search, EmployeesModel.STATUS_EMPLOYEE_SEARCH_PASS);
    }

    @Override
    public void showEmployees(List<EmployeeBean> employees) {
        mAdapter.setDatas(employees);
        mAdapter.setSelectPos();
    }

    @Override
    public void showEmpty() {
        mAdapter.clear();
        showMessage("暂无数据");
    }

    @Override
    public void transferSuccess() {
        showMessage("更换成功");
        GlobalStatusManager.getInstance().setEmployeeListNeedRefresh(true);
        GlobalStatusManager.getInstance().setUserInfoNeedRefresh(true);
        GlobalStatusManager.getInstance().setCompanyListNeedRefresh(true);
        setResult(Constants.ACTIVITY_RESULT_CODE_REFRESH);
        finish();
    }
}
