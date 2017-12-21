package com.ten.tencloud.module.user.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.module.user.contract.CompanyNewContract;
import com.ten.tencloud.module.user.presenter.CompanyNewPresenter;
import com.ten.tencloud.utils.Utils;

import butterknife.BindView;

public class CompanyNewActivity extends BaseActivity implements CompanyNewContract.View {

    @BindView(R.id.et_name)
    EditText mEtCompanyName;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_contact)
    EditText mEtContact;
    private CompanyNewPresenter mCompanyNewPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_company_new);
        initTitleBar(true, "添加新企业");
        mCompanyNewPresenter = new CompanyNewPresenter();
        mCompanyNewPresenter.attachView(this);
    }

    public void submit(View view) {
        String name = mEtCompanyName.getText().toString().trim();
        String contact = mEtContact.getText().toString().trim();
        String phone = mEtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            showMessage("企业名称不能为空");
            return;
        }
        if (TextUtils.isEmpty(contact)) {
            showMessage("联系人不能为空");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            showMessage("联系方式不能为空");
            return;
        }
        if (!Utils.isMobile(phone)) {
            showMessage("手机号码格式不正确");
            return;
        }
        mCompanyNewPresenter.createCompany(name, contact, phone);
    }

    @Override
    public void showSuccess() {
        showMessage("创建成功");
        setResult(Constants.ACTIVITY_RESULT_CODE_REFRESH);
        finish();
    }

    @Override
    public void showFailed() {
        showMessage("创建失败");
    }
}
