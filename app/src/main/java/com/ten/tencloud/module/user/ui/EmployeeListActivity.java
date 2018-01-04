package com.ten.tencloud.module.user.ui;

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
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.EmployeeBean;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.module.user.adapter.RvEmployeeAdapter;
import com.ten.tencloud.module.user.contract.EmployeeListContract;
import com.ten.tencloud.module.user.presenter.EmployeesListPresenter;
import com.ten.tencloud.utils.UiUtils;

import java.util.List;

import butterknife.BindView;

public class EmployeeListActivity extends BaseActivity implements EmployeeListContract.View {

    @BindView(R.id.rv_employee)
    RecyclerView mRvEmployee;
    @BindView(R.id.layout)
    View layout;
    private EmployeesListPresenter mEmployeesListPresenter;
    private RvEmployeeAdapter mAdapter;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_employee_list);
        initTitleBar(true, "员工列表", R.menu.menu_add, new OnMenuItemClickListener() {
            @Override
            public void onItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_add) {
                    showPopup();
                }
            }
        });
        initView();
    }

    /**
     *
     */
    private void showPopup() {
        if (mPopupWindow == null) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.pop_employee, null);
            view.findViewById(R.id.tv_join_condition).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, EmployeeJoinConditionActivity.class);
                    startActivity(intent);
                    mPopupWindow.dismiss();
                }
            });
            view.findViewById(R.id.tv_invite).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityNoValue(mContext, EmployeeInviteActivity.class);
                    mPopupWindow.dismiss();
                }
            });
            View transferView = view.findViewById(R.id.tv_transfer);
            if (AppBaseCache.getInstance().getSelectCompanyWithLogin().getIs_admin() != 0) {
                transferView.setVisibility(View.VISIBLE);
            } else {
                transferView.setVisibility(View.GONE);
            }
            transferView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, EmployeeSelectAdminActivity.class);
                    startActivity(intent);
                    mPopupWindow.dismiss();
                }
            });
            mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            mPopupWindow.setFocusable(true);
            mPopupWindow.setOutsideTouchable(true);
        }
        int dx = UiUtils.dip2px(this, 5);
        int dy = UiUtils.dip2px(this, 80);
        mPopupWindow.showAtLocation(layout, Gravity.RIGHT | Gravity.TOP, dx, dy);
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
