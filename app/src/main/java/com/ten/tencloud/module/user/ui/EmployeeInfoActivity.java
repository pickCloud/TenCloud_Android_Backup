package com.ten.tencloud.module.user.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.CompanyBean;
import com.ten.tencloud.bean.EmployeeBean;
import com.ten.tencloud.bean.User;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.module.main.ui.MainActivity;
import com.ten.tencloud.module.user.contract.EmployeeInfoContract;
import com.ten.tencloud.module.user.presenter.EmployeesInfoPresenter;
import com.ten.tencloud.utils.Utils;
import com.ten.tencloud.utils.glide.GlideUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class EmployeeInfoActivity extends BaseActivity implements EmployeeInfoContract.View {

    @BindView(R.id.iv_avatar)
    ImageView mIvAvatar;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_admin)
    TextView mTvAdmin;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_id_num)
    TextView mTvIdNum;
    @BindView(R.id.tv_apply_time)
    TextView mTvApplyTime;
    @BindView(R.id.tv_join_time)
    TextView mTvJoinTime;
    @BindView(R.id.tv_status)
    TextView mTvStatus;

    @BindView(R.id.btn_view_permission)
    Button mBtnViewPermission;//查看权限
    @BindView(R.id.btn_replace_admin)
    Button mBtnReplaceAdmin;//替换管理员
    @BindView(R.id.btn_leave)
    Button mBtnLeave; //离开企业(有权限)

    //审核
    @BindView(R.id.btn_allow)
    Button mBtnAllow;
    @BindView(R.id.btn_reject)
    Button mBtnReject;

    @BindView(R.id.btn_setting_permission)
    Button mBtnSettingPermission;//设置权限
    @BindView(R.id.btn_relieve)
    Button mBtnRelive;//解除关系
    @BindView(R.id.btn_leave_no_admin)
    Button mBtnLeaveNoAdmin;//没权限的员工看自己的

    private EmployeeBean mEmployeeInfo;
    private EmployeesInfoPresenter mEmployeesInfoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_employee_info);
        initTitleBar(true, "员工详情");
        mEmployeeInfo = getIntent().getParcelableExtra("obj");
        mEmployeesInfoPresenter = new EmployeesInfoPresenter();
        mEmployeesInfoPresenter.attachView(this);
        initView();
    }

    private void initView() {
        mTvAdmin.setVisibility(mEmployeeInfo.getIs_admin() != 0 ? View.VISIBLE : View.INVISIBLE);
        mTvName.setText(mEmployeeInfo.getName());
        mTvPhone.setText(mEmployeeInfo.getMobile());
        GlideUtils.getInstance().loadCircleImage(mContext, mIvAvatar, mEmployeeInfo.getImage_url(), R.mipmap.icon_userphoto);
        mTvApplyTime.setText(mEmployeeInfo.getCreate_time());
        mTvJoinTime.setText(mEmployeeInfo.getStatus() > 0 ? mEmployeeInfo.getUpdate_time() : "-");
        int status = mEmployeeInfo.getStatus();
        if (status == -1) {
            mTvStatus.setText("审核不通过");
            mTvStatus.setEnabled(false);
        } else if (status == 0) {
            mTvStatus.setText("待审核");
            mTvStatus.setEnabled(true);
            mTvStatus.setSelected(false);
        } else {
            mTvStatus.setText("审核通过");
            mTvStatus.setEnabled(true);
            mTvStatus.setSelected(true);
        }
        // TODO: 2018/1/3 身份证
        setButtonStatusForPermission();
    }

    /**
     * 根据权限设置按钮
     */
    private void setButtonStatusForPermission() {
        User userInfo = AppBaseCache.getInstance().getUserInfo();
        CompanyBean selectCompanyWithLogin = AppBaseCache.getInstance().getSelectCompanyWithLogin();
        boolean isAdmin = selectCompanyWithLogin.getIs_admin() != 0;
        int status = mEmployeeInfo.getStatus();
        if (status == 0) {
            if (Utils.hasPermission("审核员工")) {
                mBtnAllow.setVisibility(View.VISIBLE);
                mBtnReject.setVisibility(View.VISIBLE);
            }
        } else if (status > 0) {
            if (userInfo.getId() == mEmployeeInfo.getUid()) {
                mBtnLeaveNoAdmin.setVisibility(View.VISIBLE);
                if (isAdmin) {
                    mBtnReplaceAdmin.setVisibility(View.VISIBLE);
                    mBtnLeaveNoAdmin.setVisibility(View.GONE);
                    mBtnLeave.setVisibility(View.VISIBLE);
                }
            } else {
                if (Utils.hasPermission("解除和员工关系")) {
                    mBtnRelive.setVisibility(View.VISIBLE);
                }
            }
            if (Utils.hasPermission("查看员工信息")) {
                mBtnViewPermission.setVisibility(View.VISIBLE);
            }
            if (Utils.hasPermission("设置员工权限")) {
                mBtnSettingPermission.setVisibility(View.VISIBLE);
            }
        }
    }

    @OnClick({R.id.btn_setting_permission, R.id.btn_view_permission,
            R.id.btn_replace_admin, R.id.btn_leave, R.id.btn_leave_no_admin,
            R.id.btn_relieve, R.id.btn_allow, R.id.btn_reject})
    public void onClick(View view) {
        switch (view.getId()) {
            //设置权限
            case R.id.btn_setting_permission: {
                Intent intent = new Intent(this, PermissionTreeActivity.class);
                intent.putExtra("type", PermissionTreeActivity.TYPE_USER_SETTING);
                intent.putExtra("uid", mEmployeeInfo.getId());
                intent.putExtra("name", mEmployeeInfo.getName());
                startActivity(intent);
                break;
            }
            //查看权限
            case R.id.btn_view_permission: {
                Intent intent = new Intent(this, PermissionTreeActivity.class);
                intent.putExtra("type", PermissionTreeActivity.TYPE_USER_VIEW);
                intent.putExtra("uid", mEmployeeInfo.getId());
                intent.putExtra("name", mEmployeeInfo.getName());
                startActivity(intent);
                break;
            }
            //更换管理员
            case R.id.btn_replace_admin: {
                Intent intent = new Intent(this, EmployeeSelectAdminActivity.class);
                startActivity(intent);
                break;
            }
            //离开公司
            case R.id.btn_leave:
            case R.id.btn_leave_no_admin: {
                mEmployeesInfoPresenter.employeeDismissCompany(mEmployeeInfo.getId());
                break;
            }
            //解除关系
            case R.id.btn_relieve: {
                mEmployeesInfoPresenter.companyDismissEmployee(mEmployeeInfo.getId());
                break;
            }
            case R.id.btn_allow: {
                mEmployeesInfoPresenter.acceptApplication(mEmployeeInfo.getId());
                break;
            }
            case R.id.btn_reject: {
                mEmployeesInfoPresenter.rejectApplication(mEmployeeInfo.getId());
                break;
            }
        }
    }

    @Override
    public void employeeDismissCompanySuccess() {
        showMessage("离开公司成功");
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void companyDismissEmployeeSuccess() {
        showMessage("解除员工成功");
        finish();
    }

    @Override
    public void acceptApplicationSuccess() {
        showMessage("允许员工加入");
        mBtnAllow.setVisibility(View.GONE);
        mBtnReject.setVisibility(View.GONE);
        mTvStatus.setText("审核通过");
        mTvStatus.setEnabled(true);
        mTvStatus.setSelected(true);
    }

    @Override
    public void rejectApplicationSuccess() {
        showMessage("拒绝员工加入");
        mBtnAllow.setVisibility(View.GONE);
        mBtnReject.setVisibility(View.GONE);
        mTvStatus.setText("审核不通过");
        mTvStatus.setEnabled(false);
    }
}
