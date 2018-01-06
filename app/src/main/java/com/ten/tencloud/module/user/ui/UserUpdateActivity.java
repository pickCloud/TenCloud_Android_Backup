package com.ten.tencloud.module.user.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.User;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.module.user.contract.UserUpdateContract;
import com.ten.tencloud.module.user.presenter.UserUpdatePresenter;
import com.ten.tencloud.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

public class UserUpdateActivity extends BaseActivity implements UserUpdateContract.View {

    /**
     * 个人信息
     */
    public final static int TYPE_USER_NAME = 1;
    public final static int TYPE_USER_EMAIL = 2;
    public final static int TYPE_USER_GENDER = 3;

    /**
     * 企业信息
     */
    public final static int TYPE_COMPANY_NAME = 11;
    public final static int TYPE_COMPANY_CONTACT = 12;
    public final static int TYPE_COMPANY_CONTACT_CALL = 13;


    @BindView(R.id.et_info)
    EditText mEtInfo;
    @BindView(R.id.ll_input)
    View mLlInput;
    @BindView(R.id.ll_gender)
    View mLlGender;
    @BindView(R.id.iv_man)
    View mIvMan;
    @BindView(R.id.iv_women)
    View mIvWoman;


    //记录修改类型
    private int type;
    private UserUpdatePresenter mUserUpdatePresenter;

    //个人
    private String userName;
    private String userEmail;
    private int genderTag = 3;//记录性别 1 男 2 女 3未知

    //企业
    private int cid;
    private String companyName;
    private String companyContact;
    private String companyContactCall;
    private String mImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_user_update);
        type = getIntent().getIntExtra("type", 0);
        cid = getIntent().getIntExtra("cid", 0);
        companyName = getIntent().getStringExtra("cName");
        companyContact = getIntent().getStringExtra("cContact");
        companyContactCall = getIntent().getStringExtra("cContactCall");
        mImageUrl = getIntent().getStringExtra("image_url");
        String title = "修改资料";
        Intent intent = getIntent();
        switch (type) {
            case TYPE_USER_NAME: {
                title = "修改姓名";
                mLlInput.setVisibility(View.VISIBLE);
                mLlGender.setVisibility(View.GONE);
                mEtInfo.setHint("输入姓名");
                userName = intent.getStringExtra("name");
                mEtInfo.setText(userName);
                mEtInfo.setSelection(Utils.isEmptyDefaultForString(userName, "").length());
                break;
            }
            case TYPE_USER_EMAIL: {
                title = "修改邮箱";
                mLlInput.setVisibility(View.VISIBLE);
                mLlGender.setVisibility(View.GONE);
                mEtInfo.setHint("输入邮箱");
                userEmail = intent.getStringExtra("email");
                mEtInfo.setText(userEmail);
                mEtInfo.setSelection(Utils.isEmptyDefaultForString(userEmail, "").length());
                mEtInfo.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;
            }
            case TYPE_USER_GENDER: {
                title = "修改性别";
                mLlGender.setVisibility(View.VISIBLE);
                mLlInput.setVisibility(View.GONE);
                genderTag = intent.getIntExtra("gender", 3);
                if (genderTag == 1) {
                    mIvMan.setVisibility(View.VISIBLE);
                    mIvWoman.setVisibility(View.INVISIBLE);
                } else if (genderTag == 2) {
                    mIvMan.setVisibility(View.INVISIBLE);
                    mIvWoman.setVisibility(View.VISIBLE);
                }
                break;
            }

            case TYPE_COMPANY_NAME: {
                title = "修改企业名称";
                mLlInput.setVisibility(View.VISIBLE);
                mLlGender.setVisibility(View.GONE);
                mEtInfo.setHint("输入企业名称");
                mEtInfo.setText(companyName);
                mEtInfo.setSelection(Utils.isEmptyDefaultForString(companyName, "").length());
                break;
            }
            case TYPE_COMPANY_CONTACT: {
                title = "修改联系人";
                mLlInput.setVisibility(View.VISIBLE);
                mLlGender.setVisibility(View.GONE);
                mEtInfo.setHint("输入联系人");
                mEtInfo.setText(companyContact);
                mEtInfo.setSelection(Utils.isEmptyDefaultForString(companyContact, "").length());
                break;
            }
            case TYPE_COMPANY_CONTACT_CALL: {
                title = "修改联系方式";
                mLlInput.setVisibility(View.VISIBLE);
                mLlGender.setVisibility(View.GONE);
                mEtInfo.setHint("输入手机号");
                mEtInfo.setInputType(InputType.TYPE_CLASS_PHONE);
                mEtInfo.setText(companyContactCall);
                mEtInfo.setSelection(Utils.isEmptyDefaultForString(companyContactCall, "").length());
                break;
            }
        }
        initTitleBar(true, title, "确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type) {
                    case TYPE_USER_NAME: {
                        userName = mEtInfo.getText().toString().trim();
                        if (TextUtils.isEmpty(userName)) {
                            showMessage("姓名不能为空");
                            return;
                        }
                        mUserUpdatePresenter.updateUserInfo("userName", userName);
                        break;
                    }
                    case TYPE_USER_EMAIL: {
                        userEmail = mEtInfo.getText().toString().trim();
                        if (TextUtils.isEmpty(userEmail)) {
                            showMessage("邮箱不能为空");
                            return;
                        } else if (!Utils.isEmail(userEmail)) {
                            showMessage("邮箱格式不正确");
                            return;
                        }
                        mUserUpdatePresenter.updateUserInfo("email", userEmail);
                        break;
                    }
                    case TYPE_USER_GENDER: {
                        mUserUpdatePresenter.updateUserInfo("gender", genderTag + "");
                        break;
                    }
                    case TYPE_COMPANY_NAME: {
                        companyName = mEtInfo.getText().toString().trim();
                        if (TextUtils.isEmpty(companyName)) {
                            showMessage("企业名称不能为空");
                            return;
                        }
                        mUserUpdatePresenter.updateCompanyInfo(cid, companyName, companyContact, companyContactCall, mImageUrl);
                        break;
                    }
                    case TYPE_COMPANY_CONTACT: {
                        companyContact = mEtInfo.getText().toString().trim();
                        if (TextUtils.isEmpty(companyContact)) {
                            showMessage("联系人不能为空");
                            return;
                        }
                        mUserUpdatePresenter.updateCompanyInfo(cid, companyName, companyContact, companyContactCall, mImageUrl);
                        break;
                    }
                    case TYPE_COMPANY_CONTACT_CALL: {
                        companyContactCall = mEtInfo.getText().toString().trim();
                        if (TextUtils.isEmpty(companyContactCall)) {
                            showMessage("联系方式不能为空");
                            return;
                        } else if (!Utils.isMobile(companyContactCall)) {
                            showMessage("联系方式格式不正确");
                            return;
                        }
                        mUserUpdatePresenter.updateCompanyInfo(cid, companyName, companyContact, companyContactCall, mImageUrl);
                        break;
                    }
                }
            }
        });
        mUserUpdatePresenter = new UserUpdatePresenter();
        mUserUpdatePresenter.attachView(this);
    }

    @OnClick({R.id.ll_man, R.id.ll_woman})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_man:
                mIvWoman.setVisibility(View.INVISIBLE);
                mIvMan.setVisibility(View.VISIBLE);
                genderTag = 1;
                break;
            case R.id.ll_woman:
                mIvWoman.setVisibility(View.VISIBLE);
                mIvMan.setVisibility(View.INVISIBLE);
                genderTag = 2;
                break;
        }
    }

    public void btnDel(View view) {
        mEtInfo.setText("");
    }

    @Override
    public void updateSuccess() {
        User userInfo = AppBaseCache.getInstance().getUserInfo();
        switch (type) {
            case TYPE_USER_NAME: {
                userInfo.setName(userName);
                AppBaseCache.getInstance().setUserInfo(userInfo);
                showMessage("信息修改成功");
                break;
            }
            case TYPE_USER_EMAIL: {
                userInfo.setEmail(userEmail);
                AppBaseCache.getInstance().setUserInfo(userInfo);
                showMessage("信息修改成功");
                break;
            }
            case TYPE_USER_GENDER: {
                userInfo.setGender(genderTag);
                AppBaseCache.getInstance().setUserInfo(userInfo);
                showMessage("信息修改成功");
                break;
            }
            case TYPE_COMPANY_NAME: {
                startActivityNoValue(this, CompanyNameSubmitActivity.class);
                break;
            }
        }
        setResult(Constants.ACTIVITY_RESULT_CODE_REFRESH);
        finish();
    }
}
