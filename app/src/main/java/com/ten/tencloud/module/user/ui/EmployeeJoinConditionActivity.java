package com.ten.tencloud.module.user.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.module.user.contract.EmployeeJoinSettingContract;
import com.ten.tencloud.module.user.presenter.EmployeesJoinSettingPresenter;

import butterknife.BindView;

public class EmployeeJoinConditionActivity extends BaseActivity implements EmployeeJoinSettingContract.View {

    @BindView(R.id.cb_id_num)
    CheckBox mCbIdNum;
    private EmployeesJoinSettingPresenter mEmployeesJoinSettingPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_employee_join_condition);
        initTitleBar(true, "设置加入条件", "确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String setting = "name,mobile";
                if (mCbIdNum.isChecked()) {
                    setting += ",id_card";
                }
                mEmployeesJoinSettingPresenter.setJoinSetting(setting);
            }
        });
        mEmployeesJoinSettingPresenter = new EmployeesJoinSettingPresenter();
        mEmployeesJoinSettingPresenter.attachView(this);
        mEmployeesJoinSettingPresenter.getJoinSetting();
    }

    @Override
    public void setJoinSettingSuccess() {
        showMessage("设置成功");
        setResult(Constants.ACTIVITY_RESULT_CODE_REFRESH);
        finish();
    }

    @Override
    public void showJoinSetting(String setting) {
        mCbIdNum.setChecked(setting.contains("id_card"));
    }
}
