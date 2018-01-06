package com.ten.tencloud.module.user.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.module.user.contract.EmployeeInviteContract;
import com.ten.tencloud.module.user.contract.EmployeeJoinSettingContract;
import com.ten.tencloud.module.user.presenter.EmployeesInvitePresenter;
import com.ten.tencloud.module.user.presenter.EmployeesJoinSettingPresenter;

import butterknife.BindView;

public class EmployeeInviteActivity extends BaseActivity
        implements EmployeeJoinSettingContract.View, EmployeeInviteContract.View {

    @BindView(R.id.tv_setting)
    TextView mTvSetting;

    private EmployeesJoinSettingPresenter mJoinSettingPresenter;
    private EmployeesInvitePresenter mEmployeesInvitePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_employee_invite);
        initTitleBar(true, "邀请员工");
        mJoinSettingPresenter = new EmployeesJoinSettingPresenter();
        mJoinSettingPresenter.attachView(this);
        mEmployeesInvitePresenter = new EmployeesInvitePresenter();
        mEmployeesInvitePresenter.attachView(this);
    }

    @Override
    public void setJoinSettingSuccess() {

    }

    @Override
    public void showJoinSetting(String setting) {
        String text = "手机";
        if (setting.contains("name")) {
            text += "、姓名";
        }
        if (setting.contains("id_card")) {
            text += "、身份证";
        }
        mTvSetting.setText(text);
    }

    public void setting(View view) {
        startActivityNoValue(this, EmployeeJoinConditionActivity.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mJoinSettingPresenter.getJoinSetting();
    }

    public void generateUrl(View view) {
        mEmployeesInvitePresenter.generateUrl();
    }

    @Override
    public void showUrl(String url) {
        showMessage("邀请链接：" + url);
    }
}
