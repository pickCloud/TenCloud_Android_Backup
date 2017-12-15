package com.ten.tencloud.module.user.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.User;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.utils.DateUtils;
import com.ten.tencloud.utils.Utils;
import com.ten.tencloud.utils.glide.GlideUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class UserInfoActivity extends BaseActivity {

    @BindView(R.id.iv_avatar)
    ImageView mIvAvatar;
    @BindView(R.id.tv_name_des)
    TextView mTvName;
    @BindView(R.id.tv_autonym_des)
    TextView mTvAutonym;
    @BindView(R.id.tv_email_des)
    TextView mTvEmail;
    @BindView(R.id.tv_gender_des)
    TextView mTvGender;
    @BindView(R.id.tv_birth_des)
    TextView mTvBirth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_user_info);
        initTitleBar(true, "个人资料");
        initView();
    }

    private void initView() {
        User userInfo = AppBaseCache.getInstance().getUserInfo();
        GlideUtils.getInstance().loadCircleImage(this, mIvAvatar, userInfo.getImage_url(), R.mipmap.icon_userphoto);
        mTvName.setText(Utils.strIsEmptyForDefault(userInfo.getName(), "未设置"));
        mTvEmail.setText(Utils.strIsEmptyForDefault(userInfo.getEmail(), "未设置"));
        mTvBirth.setText(DateUtils.timestampToString(userInfo.getBirthday(), "yyyy-MM-dd"));
        if (userInfo.getGender() == 1) {
            mTvGender.setText("男");
        } else if (userInfo.getGender() == 2) {
            mTvGender.setText("女");
        } else {
            mTvGender.setText("未知");
        }
    }

    @OnClick({R.id.ll_avatar, R.id.ll_name, R.id.ll_autonym,
            R.id.ll_email, R.id.ll_gender, R.id.ll_birth})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_avatar:

                break;
            case R.id.ll_name: {
                Intent intent = new Intent(this, UserUpdateActivity.class);
                intent.putExtra("type", UserUpdateActivity.TYPE_NAME);
                startActivity(intent);
                break;
            }

            case R.id.ll_autonym:

                break;
            case R.id.ll_email: {
                Intent intent = new Intent(this, UserUpdateActivity.class);
                intent.putExtra("type", UserUpdateActivity.TYPE_EMAIL);
                startActivity(intent);
                break;
            }

            case R.id.ll_gender: {
                Intent intent = new Intent(this, UserUpdateActivity.class);
                intent.putExtra("type", UserUpdateActivity.TYPE_GENDER);
                startActivity(intent);
                break;
            }
            case R.id.ll_birth:

                break;
        }
    }
}
