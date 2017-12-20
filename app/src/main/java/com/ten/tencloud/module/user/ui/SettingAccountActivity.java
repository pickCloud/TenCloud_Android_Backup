package com.ten.tencloud.module.user.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.User;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.module.login.ui.ForgetStep01Activity;
import com.ten.tencloud.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingAccountActivity extends BaseActivity {

    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_register_time)
    TextView mTvRegisterTime;
    private User mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_setting_account);
        initTitleBar(true, "账号安全");
        mUserInfo = AppBaseCache.getInstance().getUserInfo();
        initView();
    }

    private void initView() {
        mTvPhone.setText(Utils.hide6Phone(mUserInfo.getMobile()));
        mTvRegisterTime.setText(mUserInfo.getCreate_time().substring(0, 10));
    }

    @OnClick({R.id.ll_phone, R.id.ll_change_passwd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_phone:
                startActivityNoValue(this, SettingChangePhoneActivity.class);
                break;
            case R.id.ll_change_passwd:
                Intent intent = new Intent(this, ForgetStep01Activity.class);
                intent.putExtra("isSetting", true);
                startActivity(intent);
                break;
        }
    }
}
