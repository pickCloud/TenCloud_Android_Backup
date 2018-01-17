package com.ten.tencloud.module.user.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.CompanyBean;
import com.ten.tencloud.bean.User;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.module.user.contract.EmployeeInviteContract;
import com.ten.tencloud.module.user.contract.EmployeeJoinSettingContract;
import com.ten.tencloud.module.user.presenter.EmployeesInvitePresenter;
import com.ten.tencloud.module.user.presenter.EmployeesJoinSettingPresenter;
import com.ten.tencloud.widget.dialog.ShareDialog;

import butterknife.BindView;

public class EmployeeInviteActivity extends BaseActivity
        implements EmployeeJoinSettingContract.View, EmployeeInviteContract.View {

    @BindView(R.id.tv_setting)
    TextView mTvSetting;
    @BindView(R.id.tv_name)
    TextView mTvName;

    private EmployeesJoinSettingPresenter mJoinSettingPresenter;
    private EmployeesInvitePresenter mEmployeesInvitePresenter;
    private ShareDialog mShareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_employee_invite);
        initTitleBar(true, "邀请员工");
        CompanyBean companyBean = AppBaseCache.getInstance().getSelectCompanyWithLogin();
        if (companyBean != null) {
            mTvName.setText(companyBean.getCompany_name());
        }
        mJoinSettingPresenter = new EmployeesJoinSettingPresenter();
        mJoinSettingPresenter.attachView(this);
        mEmployeesInvitePresenter = new EmployeesInvitePresenter();
        mEmployeesInvitePresenter.attachView(this);
        mJoinSettingPresenter.getJoinSetting();
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
        Intent intent = new Intent(this, EmployeeJoinConditionActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Constants.ACTIVITY_RESULT_CODE_REFRESH) {
            mJoinSettingPresenter.getJoinSetting();
        }
    }

    public void generateUrl(View view) {
        mEmployeesInvitePresenter.generateUrl();
    }

    @Override
    public void showUrl(String url) {
        if (mShareDialog == null) {
            mShareDialog = new ShareDialog(mContext);
        }
        User userInfo = AppBaseCache.getInstance().getUserInfo();
        CompanyBean selectCompanyWithLogin = AppBaseCache.getInstance().getSelectCompanyWithLogin();
        String msg = userInfo.getName() + " 邀请你加入 " + selectCompanyWithLogin.getCompany_name() + " " + url;
        mShareDialog.setContent(msg);
        mShareDialog.show();
    }
}
