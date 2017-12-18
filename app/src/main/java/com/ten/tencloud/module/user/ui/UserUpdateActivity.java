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

    public final static int TYPE_NAME = 1;
    public final static int TYPE_EMAIL = 2;
    public final static int TYPE_GENDER = 3;

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

    private String name;
    private String email;
    private int genderTag = 3;//记录性别 1 男 2 女 3未知

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_user_update);
        type = getIntent().getIntExtra("type", 0);
        String title = "修改资料";
        Intent intent = getIntent();
        switch (type) {
            case TYPE_NAME: {
                title = "修改姓名";
                mLlInput.setVisibility(View.VISIBLE);
                mLlGender.setVisibility(View.GONE);
                mEtInfo.setHint("输入姓名");
                name = intent.getStringExtra("name");
                mEtInfo.setText(name);
                mEtInfo.setSelection(Utils.isEmptyDefaultForString(name, "").length());
                break;
            }
            case TYPE_EMAIL: {
                title = "修改邮箱";
                mLlInput.setVisibility(View.VISIBLE);
                mLlGender.setVisibility(View.GONE);
                mEtInfo.setHint("输入邮箱");
                email = intent.getStringExtra("email");
                mEtInfo.setText(email);
                mEtInfo.setSelection(Utils.isEmptyDefaultForString(email, "").length());
                mEtInfo.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;
            }
            case TYPE_GENDER: {
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
        }
        initTitleBar(true, title, "确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type) {
                    case TYPE_NAME: {
                        name = mEtInfo.getText().toString().trim();
                        if (TextUtils.isEmpty(name)) {
                            showMessage("姓名不能为空");
                            return;
                        }
                        mUserUpdatePresenter.updateUserInfo("name", name);
                        break;
                    }
                    case TYPE_EMAIL: {
                        email = mEtInfo.getText().toString().trim();
                        if (TextUtils.isEmpty(email)) {
                            showMessage("邮箱不能为空");
                            return;
                        } else if (!Utils.isEmail(email)) {
                            showMessage("邮箱格式不正确");
                            return;
                        }
                        mUserUpdatePresenter.updateUserInfo("email", email);
                        break;
                    }
                    case TYPE_GENDER: {
                        mUserUpdatePresenter.updateUserInfo("gender", genderTag + "");
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
        showMessage("信息修改成功");
        User userInfo = AppBaseCache.getInstance().getUserInfo();
        switch (type) {
            case TYPE_NAME: {
                userInfo.setName(name);
                break;
            }
            case TYPE_EMAIL: {
                userInfo.setEmail(email);
                break;
            }
            case TYPE_GENDER: {
                userInfo.setGender(genderTag);
                break;
            }
        }
        AppBaseCache.getInstance().setUserInfo(userInfo);
        setResult(Constants.ACTIVITY_RESULT_CODE_REFRESH);
        finish();
    }
}
