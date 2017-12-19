package com.ten.tencloud.module.user.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.User;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.module.user.contract.UserUpdateContract;
import com.ten.tencloud.module.user.presenter.UserUpdatePresenter;
import com.ten.tencloud.utils.DateUtils;
import com.ten.tencloud.utils.Utils;
import com.ten.tencloud.utils.glide.GlideUtils;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class UserInfoActivity extends BaseActivity implements UserUpdateContract.View {

    private final static String TIME_FORMAT = "yyyy-MM-dd";

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
    private User mUserInfo;
    private TimePickerView mTimePickerView;
    private long mBirthDay;

    private UserUpdatePresenter mUserUpdatePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_user_info);
        initTitleBar(true, "个人资料");
        mUserUpdatePresenter = new UserUpdatePresenter();
        mUserUpdatePresenter.attachView(this);
        initView();
    }

    private void initView() {
        mUserInfo = AppBaseCache.getInstance().getUserInfo();
        GlideUtils.getInstance().loadCircleImage(this, mIvAvatar, mUserInfo.getImage_url(), R.mipmap.icon_userphoto);
        mTvName.setText(Utils.strIsEmptyForDefault(mUserInfo.getName(), "未设置"));
        mTvEmail.setText(Utils.strIsEmptyForDefault(mUserInfo.getEmail(), "未设置"));
        mBirthDay = mUserInfo.getBirthday();
        mTvBirth.setText(DateUtils.timestampToString(mBirthDay, TIME_FORMAT));
        if (mUserInfo.getGender() == 1) {
            mTvGender.setText("男");
        } else if (mUserInfo.getGender() == 2) {
            mTvGender.setText("女");
        } else {
            mTvGender.setText("未知");
        }
        Calendar startTime = Calendar.getInstance();
        startTime.set(1900, 0, 1);
        mTimePickerView = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mBirthDay = date.getTime() / 1000;
                mTvBirth.setText(DateUtils.timestampToString(mBirthDay, TIME_FORMAT));
                mUserUpdatePresenter.updateUserInfo("birthday", mBirthDay + "");
            }
        })
                .setRangDate(startTime, handTime(System.currentTimeMillis() / 1000))
                .setType(new boolean[]{true, true, true, false, false, false})
                .setDecorView((ViewGroup) getWindow().getDecorView())
                .isCenterLabel(false)
                .build();
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
                intent.putExtra("name", mUserInfo.getName());
                startActivityForResult(intent, 0);
                break;
            }

            case R.id.ll_autonym:

                break;
            case R.id.ll_email: {
                Intent intent = new Intent(this, UserUpdateActivity.class);
                intent.putExtra("type", UserUpdateActivity.TYPE_EMAIL);
                intent.putExtra("email", mUserInfo.getEmail());
                startActivityForResult(intent, 0);
                break;
            }

            case R.id.ll_gender: {
                Intent intent = new Intent(this, UserUpdateActivity.class);
                intent.putExtra("type", UserUpdateActivity.TYPE_GENDER);
                intent.putExtra("gender", mUserInfo.getGender());
                startActivityForResult(intent, 0);
                break;
            }
            case R.id.ll_birth:
                mTimePickerView.setDate(handTime(mBirthDay));
                mTimePickerView.show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Constants.ACTIVITY_RESULT_CODE_REFRESH) {
            initView();
        }
    }

    private Calendar handTime(long time) {
        String string = DateUtils.timestampToString(time, TIME_FORMAT);
        return DateUtils.strToCalendar(string, TIME_FORMAT);
    }

    @Override
    public void updateSuccess() {
        showMessage("信息修改成功");
        User userInfo = AppBaseCache.getInstance().getUserInfo();
        userInfo.setBirthday(mBirthDay);
        AppBaseCache.getInstance().setUserInfo(userInfo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserUpdatePresenter.detachView();
    }
}
