package com.ten.tencloud.module.user.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.ten.tencloud.bean.CompanyBean;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.module.other.contract.QiniuContract;
import com.ten.tencloud.module.other.presenter.QiniuPresenter;
import com.ten.tencloud.module.user.contract.CompanyInfoContract;
import com.ten.tencloud.module.user.presenter.CompanyInfoPresenter;
import com.ten.tencloud.utils.SelectPhotoHelper;
import com.ten.tencloud.utils.Utils;
import com.ten.tencloud.utils.glide.GlideUtils;
import com.ten.tencloud.widget.dialog.PhotoSelectDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class CompanyInfoActivity extends BaseActivity implements CompanyInfoContract.View
        , TakePhoto.TakeResultListener, InvokeListener, QiniuContract.View {

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
    private boolean isPermissionChangeCompanyInfo;

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private SelectPhotoHelper mPhotoHelper;
    private QiniuPresenter mQiniuPresenter;

    private CompanyInfoPresenter mCompanyInfoPresenter;
    private String mMobile;
    private String mName;
    private String mContact;
    private String mImageUrl;

    private PhotoSelectDialog mPhotoSelectDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_company_info);
        initTitleBar(true, "企业资料");
        mCid = getIntent().getIntExtra("cid", 0);
        isPermissionChangeCompanyInfo = Utils.hasPermission("修改企业信息");
        mCompanyInfoPresenter = new CompanyInfoPresenter();
        mCompanyInfoPresenter.attachView(this);
        mQiniuPresenter = new QiniuPresenter();
        mQiniuPresenter.attachView(this);

        SelectPhotoHelper.Options options = new SelectPhotoHelper.Options();
        options.isCrop = true;
        options.isCompress = true;
        options.cropHeight = 400;
        options.cropWidth = 400;
        mPhotoHelper = new SelectPhotoHelper(options);

        initView();
    }

    private void initView() {
        mIvArrowLogo.setVisibility(isPermissionChangeCompanyInfo ? View.VISIBLE : View.GONE);
        mIvArrowName.setVisibility(isPermissionChangeCompanyInfo ? View.VISIBLE : View.GONE);
        mIvArrowContacts.setVisibility(isPermissionChangeCompanyInfo ? View.VISIBLE : View.GONE);
        mIvArrowCall.setVisibility(isPermissionChangeCompanyInfo ? View.VISIBLE : View.GONE);
        mIvArrowAuth.setVisibility(isPermissionChangeCompanyInfo ? View.VISIBLE : View.GONE);
        mCompanyInfoPresenter.getCompanyByCid(mCid);
    }

    @OnClick({R.id.ll_name, R.id.ll_logo, R.id.ll_contacts,
            R.id.ll_contacts_call, R.id.ll_authentication})
    public void onClick(View view) {
        if (!isPermissionChangeCompanyInfo) return;
        switch (view.getId()) {
            case R.id.ll_logo:
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
                intent.putExtra("cid", mCid);
                intent.putExtra("type", UserUpdateActivity.TYPE_COMPANY_NAME);
                intent.putExtra("cName", mName);
                intent.putExtra("image_url", mImageUrl);
                intent.putExtra("cContact", mContact);
                intent.putExtra("cContactCall", mMobile);
                startActivityForResult(intent, 0);
                break;
            }
            case R.id.ll_contacts: {
                Intent intent = new Intent(this, UserUpdateActivity.class);
                intent.putExtra("cid", mCid);
                intent.putExtra("type", UserUpdateActivity.TYPE_COMPANY_CONTACT);
                intent.putExtra("image_url", mImageUrl);
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
                intent.putExtra("image_url", mImageUrl);
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
        mImageUrl = companyInfo.getImage_url();
        GlideUtils.getInstance().loadCircleImage(this, mIvLogo, mImageUrl, R.mipmap.icon_com_photo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.ACTIVITY_RESULT_CODE_REFRESH) {
            mCompanyInfoPresenter.getCompanyByCid(mCid);
        }
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
        mCompanyInfoPresenter.updateCompanyInfo(mCid, mName, mContact, mMobile, path);
    }

    @Override
    public void updateSuccess() {
        showMessage("头像上传成功");
        mCompanyInfoPresenter.getCompanyByCid(mCid);
    }

    @Override
    public void uploadFiled() {
        showMessage("头像上传失败");
    }
}
