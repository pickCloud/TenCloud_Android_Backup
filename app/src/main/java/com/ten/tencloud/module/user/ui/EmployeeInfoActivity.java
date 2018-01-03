package com.ten.tencloud.module.user.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.EmployeeBean;
import com.ten.tencloud.bean.User;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.utils.Utils;

import butterknife.BindView;

public class EmployeeInfoActivity extends BaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_employee_info);
        initTitleBar(true, "员工详情");
        mEmployeeInfo = getIntent().getParcelableExtra("obj");
        initView();
    }

    private void initView() {
        mTvAdmin.setVisibility(mEmployeeInfo.getIs_admin() != 0 ? View.VISIBLE : View.INVISIBLE);
        mTvName.setText(mEmployeeInfo.getName());
        mTvPhone.setText(mEmployeeInfo.getMobile());
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
        // TODO: 2018/1/3 头像 身份证

        setButtonStatusForPermission();
    }

    /**
     * 根据权限设置按钮
     */
    private void setButtonStatusForPermission() {
        User userInfo = AppBaseCache.getInstance().getUserInfo();
        int status = mEmployeeInfo.getStatus();
        //查看本人的
        if (userInfo.getId() == mEmployeeInfo.getUid()) {
            mBtnLeaveNoAdmin.setVisibility(View.VISIBLE);
            if (Utils.hasPermission("更换员工管理员")) {
                mBtnReplaceAdmin.setVisibility(View.VISIBLE);
            }
        }else {
            if (status == 1 && Utils.hasPermission("解除和员工关系")) {
                mBtnRelive.setVisibility(View.VISIBLE);
            }
        }
        if (status == 0 && Utils.hasPermission("审核员工")) {
            mBtnAllow.setVisibility(View.VISIBLE);
            mBtnReject.setVisibility(View.VISIBLE);
        }

        if (Utils.hasPermission("查看员工信息")) {
            mBtnViewPermission.setVisibility(View.VISIBLE);
        }
        if (status > 0 && Utils.hasPermission("设置员工权限")) {
            mBtnSettingPermission.setVisibility(View.VISIBLE);
        }
    }
}
