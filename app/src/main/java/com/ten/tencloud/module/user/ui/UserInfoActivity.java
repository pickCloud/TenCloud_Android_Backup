package com.ten.tencloud.module.user.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.socks.library.KLog;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.User;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.module.other.contract.QiniuContract;
import com.ten.tencloud.module.other.presenter.QiniuPresenter;
import com.ten.tencloud.module.user.contract.UserInfoContract;
import com.ten.tencloud.module.user.contract.UserUpdateContract;
import com.ten.tencloud.module.user.presenter.UserInfoPresenter;
import com.ten.tencloud.module.user.presenter.UserUpdatePresenter;
import com.ten.tencloud.utils.DateUtils;
import com.ten.tencloud.utils.SelectPhotoHelper;
import com.ten.tencloud.utils.Utils;
import com.ten.tencloud.utils.glide.GlideUtils;
import com.ten.tencloud.widget.dialog.PhotoSelectDialog;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class UserInfoActivity extends BaseActivity implements UserInfoContract.View, UserUpdateContract.View,
        TakePhoto.TakeResultListener, InvokeListener, QiniuContract.View {

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

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;

    private UserUpdatePresenter mUserUpdatePresenter;
    private SelectPhotoHelper mPhotoHelper;
    private QiniuPresenter mQiniuPresenter;
    private UserInfoPresenter mUserInfoPresenter;
    private PhotoSelectDialog mPhotoSelectDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_user_info);
        initTitleBar(true, "个人资料");
        mUserUpdatePresenter = new UserUpdatePresenter();
        mUserUpdatePresenter.attachView(this);
        mQiniuPresenter = new QiniuPresenter();
        mQiniuPresenter.attachView(this);
        mUserInfoPresenter = new UserInfoPresenter();
        mUserInfoPresenter.attachView(this);

        SelectPhotoHelper.Options options = new SelectPhotoHelper.Options();
        options.isCrop = true;
        options.isCompress = true;
        options.cropHeight = 400;
        options.cropWidth = 400;
        mPhotoHelper = new SelectPhotoHelper(options);

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
                if (mPhotoSelectDialog == null) {
                    mPhotoSelectDialog = new PhotoSelectDialog(this);
                    mPhotoSelectDialog.setOnBtnClickListener(new PhotoSelectDialog.OnBtnClickListener() {
                        @Override
                        public void onTake() {
                            mPhotoHelper.onClick(true, getTakePhoto());
                        }

                        @Override
                        public void onGallery() {
                            mPhotoHelper.onClick(false, getTakePhoto());
                        }
                    });
                }
                mPhotoSelectDialog.show();
                break;
            case R.id.ll_name: {
                Intent intent = new Intent(this, UserUpdateActivity.class);
                intent.putExtra("type", UserUpdateActivity.TYPE_USER_NAME);
                intent.putExtra("name", mUserInfo.getName());
                startActivityForResult(intent, 0);
                break;
            }

            case R.id.ll_autonym:

                break;
            case R.id.ll_email: {
                Intent intent = new Intent(this, UserUpdateActivity.class);
                intent.putExtra("type", UserUpdateActivity.TYPE_USER_EMAIL);
                intent.putExtra("email", mUserInfo.getEmail());
                startActivityForResult(intent, 0);
                break;
            }

            case R.id.ll_gender: {
                Intent intent = new Intent(this, UserUpdateActivity.class);
                intent.putExtra("type", UserUpdateActivity.TYPE_USER_GENDER);
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
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
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
        mUserInfoPresenter.getUserInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserUpdatePresenter.detachView();
    }

    /**
     * 图片选择
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    @Override
    public void takeSuccess(TResult result) {
        KLog.i("takeSuccess：" + result.getImage().getCompressPath());
        String path = result.getImage().getCompressPath();//压缩后的路径
        mQiniuPresenter.uploadFile(path);
    }

    @Override
    public void takeFail(TResult result, String msg) {
        KLog.i("takeFail:" + msg);
    }

    @Override
    public void takeCancel() {
        KLog.i(getResources().getString(com.jph.takephoto.R.string.msg_operation_canceled));
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    public void uploadSuccess(String path) {
        mUserUpdatePresenter.updateUserInfo("image_url", path);
    }

    @Override
    public void uploadFiled() {
        showMessage("头像上传失败");
    }

    @Override
    public void showUserInfo(User user) {
        initView();
    }
}
