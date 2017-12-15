package com.ten.tencloud.module.user.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.module.user.contract.UserUpdateContract;
import com.ten.tencloud.module.user.presenter.UserUpdatePresenter;

import butterknife.BindView;

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

    //记录修改类型
    private int type;
    private UserUpdatePresenter mUserUpdatePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_user_update);
        type = getIntent().getIntExtra("type", 0);
        String title = "修改资料";
        switch (type) {
            case TYPE_NAME:
                title = "修改姓名";
                mLlInput.setVisibility(View.VISIBLE);
                mLlGender.setVisibility(View.GONE);
                break;
            case TYPE_EMAIL:
                title = "修改邮箱";
                mLlInput.setVisibility(View.VISIBLE);
                mLlGender.setVisibility(View.GONE);
                break;
            case TYPE_GENDER:
                title = "修改性别";
                mLlGender.setVisibility(View.VISIBLE);
                mLlInput.setVisibility(View.GONE);
                break;
        }
        initTitleBar(true, title, "确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type) {
                    case TYPE_NAME:
                        // TODO: 2017/12/15 网络请求
                        mUserUpdatePresenter.updateUserInfo("", "");
                        break;
                    case TYPE_EMAIL:

                        break;
                    case TYPE_GENDER:

                        break;
                }
            }
        });
        mUserUpdatePresenter = new UserUpdatePresenter();
        mUserUpdatePresenter.attachView(this);
    }

    public void btnDel(View view) {
        mEtInfo.setText("");
    }

    @Override
    public void updateSuccess() {

    }
}
