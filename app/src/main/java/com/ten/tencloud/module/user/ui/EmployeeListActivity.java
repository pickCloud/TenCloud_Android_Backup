package com.ten.tencloud.module.user.ui;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.EmployeeBean;
import com.ten.tencloud.broadcast.RefreshBroadCastHandler;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.constants.GlobalStatusManager;
import com.ten.tencloud.listener.OnRefreshListener;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.module.user.adapter.RvEmployeeAdapter;
import com.ten.tencloud.module.user.contract.EmployeeListContract;
import com.ten.tencloud.module.user.model.EmployeesModel;
import com.ten.tencloud.module.user.presenter.EmployeesListPresenter;
import com.ten.tencloud.utils.UiUtils;
import com.ten.tencloud.utils.Utils;
import com.ten.tencloud.widget.StatusSelectPopView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class EmployeeListActivity extends BaseActivity implements EmployeeListContract.View {

    @BindView(R.id.rv_employee)
    RecyclerView mRvEmployee;
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.spv_status)
    StatusSelectPopView mSpvStatus;
    @BindView(R.id.layout)
    View layout;
    private EmployeesListPresenter mEmployeesListPresenter;
    private RvEmployeeAdapter mAdapter;
    private boolean mIsPermissionInvite;
    private boolean mIsAdmin;

    private int[] mStatusData = {EmployeesModel.STATUS_EMPLOYEE_SEARCH_ALL,
            EmployeesModel.STATUS_EMPLOYEE_SEARCH_CHECKING,
            EmployeesModel.STATUS_EMPLOYEE_SEARCH_PASS,
            EmployeesModel.STATUS_EMPLOYEE_SEARCH_NO_PASS,
            EmployeesModel.STATUS_EMPLOYEE_SEARCH_CREATE};
    private PopupWindow mMenuPopupWindow;
    private int status = EmployeesModel.STATUS_EMPLOYEE_SEARCH_ALL;
    private RefreshBroadCastHandler mRefreshBroadCastHandler;
    private RefreshBroadCastHandler mSwitchCompanyRefreshBroadCastHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_employee_list);
        mRefreshBroadCastHandler = new RefreshBroadCastHandler(this, RefreshBroadCastHandler.PERMISSION_REFRESH_ACTION);
        mRefreshBroadCastHandler.registerReceiver(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                initPermission();
            }
        });
        initPermission();
        initView();
    }

    /**
     * 初始化权限控制
     */
    private void initPermission() {
        mIsPermissionInvite = Utils.hasPermission("邀请新员工");
        mIsAdmin = AppBaseCache.getInstance().getSelectCompanyWithLogin().getIs_admin() != 0;
        if (!mIsPermissionInvite && !mIsAdmin) {
            initTitleBar(true, "员工列表");
        } else {
            initTitleBar(true, "员工列表", R.menu.menu_add, new OnMenuItemClickListener() {
                @Override
                public void onItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.menu_add) {
                        showMenuPopup();
                    }
                }
            });
        }
    }

    /**
     * 菜单弹窗
     */
    private void showMenuPopup() {
        if (mMenuPopupWindow == null) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.pop_employee, null);
            View joinConditionView = view.findViewById(R.id.tv_join_condition);
            joinConditionView.setVisibility(mIsPermissionInvite ? View.VISIBLE : View.GONE);
            joinConditionView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, EmployeeJoinConditionActivity.class);
                    startActivity(intent);
                    mMenuPopupWindow.dismiss();
                }
            });
            View inviteView = view.findViewById(R.id.tv_invite);
            inviteView.setVisibility(mIsPermissionInvite ? View.VISIBLE : View.GONE);
            inviteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityNoValue(mContext, EmployeeInviteActivity.class);
                    mMenuPopupWindow.dismiss();
                }
            });
            View transferView = view.findViewById(R.id.tv_transfer);
            transferView.setVisibility(mIsAdmin ? View.VISIBLE : View.GONE);
            transferView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, EmployeeSelectAdminActivity.class);
                    startActivity(intent);
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
        mMenuPopupWindow.showAtLocation(layout, Gravity.RIGHT | Gravity.TOP, dx, dy);
    }

    private void initView() {

        List<String> statusTitles = new ArrayList<>();
        statusTitles.add("全部");
        statusTitles.add("待审核");
        statusTitles.add("审核通过");
        statusTitles.add("审核不通过");
        statusTitles.add("创建人");

        mSpvStatus.initData(statusTitles);
        mSpvStatus.setOnSelectListener(new StatusSelectPopView.OnSelectListener() {
            @Override
            public void onSelect(int pos) {
                status = mStatusData[pos];
                search();
            }
        });

        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search();
                }
                return false;
            }
        });
        mEmployeesListPresenter = new EmployeesListPresenter();
        mEmployeesListPresenter.attachView(this);

        mRvEmployee.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RvEmployeeAdapter(this);
        mAdapter.setOnItemClickListener(new CJSBaseRecyclerViewAdapter.OnItemClickListener<EmployeeBean>() {
            @Override
            public void onObjectItemClicked(EmployeeBean employeeBean, int position) {
                Intent intent = new Intent(mContext, EmployeeInfoActivity.class);
                intent.putExtra("obj", employeeBean);
                startActivityForResult(intent, 0);
            }
        });
        mRvEmployee.setAdapter(mAdapter);
        mEmployeesListPresenter.getEmployees("", EmployeesModel.STATUS_EMPLOYEE_SEARCH_ALL);
    }

    private void search() {
        String search = mEtSearch.getText().toString().trim();
        mEmployeesListPresenter.getEmployees(search, status);
    }

    @Override
    public void showEmployees(List<EmployeeBean> employees) {
        mAdapter.setDatas(employees);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (GlobalStatusManager.getInstance().isEmployeeListNeedRefresh()) {
            search();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Constants.ACTIVITY_RESULT_CODE_REFRESH) {
            search();
        }
    }

    @Override
    public void showEmpty() {
        mAdapter.clear();
        showMessage("暂无数据");
    }

    @Override
    public void onFailure() {
        AppBaseCache.getInstance().setCid(0);
        GlobalStatusManager.getInstance().setUserInfoNeedRefresh(true);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRefreshBroadCastHandler.unregisterReceiver();
        mRefreshBroadCastHandler = null;
        mEmployeesListPresenter.detachView();
        if (mSwitchCompanyRefreshBroadCastHandler != null) {
            mSwitchCompanyRefreshBroadCastHandler.unregisterReceiver();
            mSwitchCompanyRefreshBroadCastHandler = null;
        }
    }
}
