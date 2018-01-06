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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.EmployeeBean;
import com.ten.tencloud.constants.GlobalStatusManager;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.module.user.adapter.RvEmployeeAdapter;
import com.ten.tencloud.module.user.contract.EmployeeListContract;
import com.ten.tencloud.module.user.model.EmployeesModel;
import com.ten.tencloud.module.user.presenter.EmployeesListPresenter;
import com.ten.tencloud.utils.UiUtils;
import com.ten.tencloud.utils.Utils;

import java.util.List;

import butterknife.BindView;

public class EmployeeListActivity extends BaseActivity implements EmployeeListContract.View {

    @BindView(R.id.rv_employee)
    RecyclerView mRvEmployee;
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.ll_status)
    LinearLayout mLlStatus;
    @BindView(R.id.iv_option)
    ImageView mIvOption;
    @BindView(R.id.layout)
    View layout;
    private EmployeesListPresenter mEmployeesListPresenter;
    private RvEmployeeAdapter mAdapter;
    private PopupWindow mMenuPopupWindow;
    private TextView[] mTvStatusArray;
    private ImageView[] mIvStatusArray;
    private PopupWindow mStatusPopupWindow;
    private boolean mIsPermissionSettingJoin;
    private boolean mIsPermissionnvite;
    private boolean mIsAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_employee_list);
        initPermission();
        if (!mIsPermissionSettingJoin && !mIsPermissionnvite && !mIsAdmin) {
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
        initView();
    }

    /**
     * 初始化权限控制
     */
    private void initPermission() {
        mIsPermissionSettingJoin = Utils.hasPermission("设置员工加入条件");
        mIsPermissionnvite = Utils.hasPermission("邀请员工");
        mIsAdmin = AppBaseCache.getInstance().getSelectCompanyWithLogin().getIs_admin() != 0;
    }

    /**
     * 菜单弹窗
     */
    private void showMenuPopup() {
        if (mMenuPopupWindow == null) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.pop_employee, null);
            View joinConditionView = view.findViewById(R.id.tv_join_condition);
            joinConditionView.setVisibility(mIsPermissionSettingJoin ? View.VISIBLE : View.GONE);
            joinConditionView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, EmployeeJoinConditionActivity.class);
                    startActivity(intent);
                    mMenuPopupWindow.dismiss();
                }
            });
            View inviteView = view.findViewById(R.id.tv_invite);
            inviteView.setVisibility(mIsPermissionnvite ? View.VISIBLE : View.GONE);
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


    private int status = EmployeesModel.STATUS_EMPLOYEE_SEARCH_ALL;

    private String statusText = "全部";

    private void showStatusPopup() {
        if (mStatusPopupWindow == null) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.pop_employee_status, null);
            TextView tvStatusALl = view.findViewById(R.id.tv_all);
            TextView tvStatusPass = view.findViewById(R.id.tv_pass);
            TextView tvStatusNoPass = view.findViewById(R.id.tv_no_pass);
            TextView tvStatusChecking = view.findViewById(R.id.tv_checking);
            ImageView ivStatusALl = view.findViewById(R.id.iv_all);
            ImageView ivStatusPass = view.findViewById(R.id.iv_pass);
            ImageView ivStatusNoPass = view.findViewById(R.id.iv_no_pass);
            ImageView ivStatusChecking = view.findViewById(R.id.iv_checking);
            mTvStatusArray = new TextView[]{tvStatusALl, tvStatusChecking, tvStatusPass, tvStatusNoPass};
            mIvStatusArray = new ImageView[]{ivStatusALl, ivStatusChecking, ivStatusPass, ivStatusNoPass};
            tvStatusALl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("全部".equals(statusText)) {
                        return;
                    }
                    statusText = "全部";
                    mTvStatus.setText(statusText);
                    status = EmployeesModel.STATUS_EMPLOYEE_SEARCH_ALL;
                    search();
                    mStatusPopupWindow.dismiss();
                }
            });
            tvStatusPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("审核通过".equals(statusText)) {
                        return;
                    }
                    statusText = "审核通过";
                    mTvStatus.setText(statusText);
                    status = EmployeesModel.STATUS_EMPLOYEE_SEARCH_PASS;
                    search();
                    mStatusPopupWindow.dismiss();
                }
            });
            tvStatusChecking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("待审核".equals(statusText)) {
                        return;
                    }
                    statusText = "待审核";
                    mTvStatus.setText(statusText);
                    status = EmployeesModel.STATUS_EMPLOYEE_SEARCH_CHECKING;
                    search();
                    mStatusPopupWindow.dismiss();
                }
            });
            tvStatusNoPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("审核不通过".equals(statusText)) {
                        return;
                    }
                    statusText = "审核不通过";
                    mTvStatus.setText(statusText);
                    status = EmployeesModel.STATUS_EMPLOYEE_SEARCH_NO_PASS;
                    search();
                    mStatusPopupWindow.dismiss();
                }
            });
            mStatusPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            mStatusPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            mStatusPopupWindow.setFocusable(true);
            mStatusPopupWindow.setOutsideTouchable(true);
            mStatusPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    mIvOption.animate().rotation(0);
                    mTvStatus.setTextColor(getResources().getColor(R.color.text_color_556278));
                    mLlStatus.setBackgroundResource(R.drawable.shape_round_3f4656);
                }
            });
        }
        if (!mStatusPopupWindow.isShowing()) {
            mIvOption.animate().rotation(180);
            mTvStatus.setTextColor(getResources().getColor(R.color.text_color_899ab6));
            mLlStatus.setBackgroundResource(R.drawable.shape_round_3f4656_top);
            if ("全部".equals(statusText)) {
                setPopSelect(0);
            } else if ("待审核".equals(statusText)) {
                setPopSelect(1);
            } else if ("审核通过".equals(statusText)) {
                setPopSelect(2);
            } else if ("审核不通过".equals(statusText)) {
                setPopSelect(3);
            }
            mStatusPopupWindow.showAsDropDown(mLlStatus);
        }
    }

    private void setPopSelect(int pos) {
        for (int i = 0; i < mIvStatusArray.length; i++) {
            if (i == pos) {
                mIvStatusArray[i].setVisibility(View.VISIBLE);
                mTvStatusArray[i].setSelected(true);
            } else {
                mIvStatusArray[i].setVisibility(View.INVISIBLE);
                mTvStatusArray[i].setSelected(false);
            }
        }
    }

    private void initView() {
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

        mTvStatus.setText(statusText);
        mLlStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStatusPopup();
            }
        });

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
    public void showEmpty() {
        mAdapter.clear();
        showMessage("暂无数据");
    }
}
