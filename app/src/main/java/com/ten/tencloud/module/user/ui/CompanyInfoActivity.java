package com.ten.tencloud.module.user.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.CompanyBean;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.module.user.contract.CompanyInfoContract;
import com.ten.tencloud.module.user.presenter.CompanyInfoPresenter;
import com.ten.tencloud.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

public class CompanyInfoActivity extends BaseActivity implements CompanyInfoContract.View {

    @BindView(R.id.iv_logo)
    ImageView mIvLogo;
    @BindView(R.id.tv_name_des)
    TextView mTvName;
    @BindView(R.id.tv_contacts_des)
    TextView mTvContacts;
    @BindView(R.id.tv_contacts_call_des)
    TextView mTvContactsCall;
    @BindView(R.id.tv_time_des)
    TextView mTvTime;
    @BindView(R.id.tv_authentication_des)
    TextView mTvAuthentication;

    @BindView(R.id.iv_arrow_logo)
    ImageView mIvArrowLogo;
    @BindView(R.id.iv_arrow_name)
    ImageView mIvArrowName;
    @BindView(R.id.iv_arrow_contacts)
    ImageView mIvArrowContacts;
    @BindView(R.id.iv_arrow_call)
    ImageView mIvArrowCall;
    @BindView(R.id.iv_arrow_auth)
    ImageView mIvArrowAuth;

    private int mCid;
    private boolean isAdmin;

    private CompanyInfoPresenter mCompanyInfoPresenter;
    private String mMobile;
    private String mName;
    private String mContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_company_info);
        initTitleBar(true, "企业资料");
        mCid = getIntent().getIntExtra("cid", 0);
        isAdmin = getIntent().getIntExtra("isAdmin", 0) != 0;
        mCompanyInfoPresenter = new CompanyInfoPresenter();
        mCompanyInfoPresenter.attachView(this);
        initView();
    }

    private void initView() {
        mIvArrowLogo.setVisibility(isAdmin ? View.VISIBLE : View.GONE);
        mIvArrowName.setVisibility(isAdmin ? View.VISIBLE : View.GONE);
        mIvArrowContacts.setVisibility(isAdmin ? View.VISIBLE : View.GONE);
        mIvArrowCall.setVisibility(isAdmin ? View.VISIBLE : View.GONE);
        mIvArrowAuth.setVisibility(isAdmin ? View.VISIBLE : View.GONE);
        mCompanyInfoPresenter.getCompanyByCid(mCid);
    }

    @OnClick({R.id.ll_name, R.id.ll_logo, R.id.ll_contacts,
            R.id.ll_contacts_call, R.id.ll_authentication})
    public void onClick(View view) {
        if (!isAdmin) return;
        switch (view.getId()) {
            case R.id.ll_logo:
                // TODO: 2017/12/20 选择logo上传
                break;
            case R.id.ll_name: {
                Intent intent = new Intent(this, UserUpdateActivity.class);
                intent.putExtra("cid", mCid);
                intent.putExtra("type", UserUpdateActivity.TYPE_COMPANY_NAME);
                intent.putExtra("cName", mName);
                intent.putExtra("cContact", mContact);
                intent.putExtra("cContactCall", mMobile);
                startActivityForResult(intent, 0);
                break;
            }
            case R.id.ll_contacts: {
                Intent intent = new Intent(this, UserUpdateActivity.class);
                intent.putExtra("cid", mCid);
                intent.putExtra("type", UserUpdateActivity.TYPE_COMPANY_CONTACT);
                intent.putExtra("cContact", mContact);
                intent.putExtra("cContactCall", mMobile);
                intent.putExtra("cName", mName);
                startActivityForResult(intent, 0);
                break;
            }
            case R.id.ll_contacts_call: {
                Intent intent = new Intent(this, UserUpdateActivity.class);
                intent.putExtra("cid", mCid);
                intent.putExtra("type", UserUpdateActivity.TYPE_COMPANY_CONTACT_CALL);
                intent.putExtra("cContact", mContact);
                intent.putExtra("cContactCall", mMobile);
                intent.putExtra("cName", mName);
                startActivityForResult(intent, 0);
                break;
            }
            case R.id.ll_authentication:
                // TODO: 2017/12/20  认证
                break;
        }
    }

    @Override
    public void showCompanyInfo(CompanyBean companyInfo) {
        mName = companyInfo.getName();
        mTvName.setText(mName);
        mContact = companyInfo.getContact();
        mTvContacts.setText(mContact);
        mMobile = companyInfo.getMobile();
        mTvContactsCall.setText(Utils.hide4Phone(mMobile));
        mTvTime.setText(companyInfo.getCreate_time());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Constants.ACTIVITY_RESULT_CODE_REFRESH) {
            mCompanyInfoPresenter.getCompanyByCid(mCid);
        }
    }
}
