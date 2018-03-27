package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
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
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.module.other.contract.QiniuContract;
import com.ten.tencloud.module.other.presenter.QiniuPresenter;
import com.ten.tencloud.module.user.contract.CompanyInfoContract;
import com.ten.tencloud.module.user.presenter.CompanyInfoPresenter;
import com.ten.tencloud.utils.SelectPhotoHelper;
import com.ten.tencloud.utils.glide.GlideUtils;
import com.ten.tencloud.widget.CircleImageView;
import com.ten.tencloud.widget.dialog.PhotoSelectDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenxh@10.com on 2018/3/26.
 */
public class AppAddActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener
        , QiniuContract.View, CompanyInfoContract.View {

    @BindView(R.id.iv_logo)
    CircleImageView mIvLogo;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_description)
    EditText mEtDescription;
    @BindView(R.id.et_label)
    EditText mEtLabel;
    @BindView(R.id.tv_add_label)
    TextView mTvAddLabel;
    @BindView(R.id.fbl_label)
    FlexboxLayout mFlexboxLayout;
    @BindView(R.id.tv_warehouse)
    TextView mTvWarehouse;
    @BindView(R.id.btn_sure_add)
    Button mBtnSureAdd;

    private int mCid;

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
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_service_app_add);
        initTitleBar(true, "添加应用");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mCid = AppBaseCache.getInstance().getCid();
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

//        mCompanyInfoPresenter.getCompanyByCid(mCid);

    }

    @OnClick({R.id.iv_logo, R.id.tv_add_label, R.id.tv_warehouse, R.id.btn_sure_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_logo:
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
            case R.id.tv_add_label:
                startActivityForResult(new Intent(this, LabelAddActivity.class), Constants.ACTIVITY_REQUEST_CODE_COMMON1);
                break;
            case R.id.tv_warehouse:
                startActivityForResult(new Intent(this, WareHouseBindActivity.class), Constants.ACTIVITY_REQUEST_CODE_COMMON2);
                break;
            case R.id.btn_sure_add:
                break;
        }
    }

    @Override
    public void showCompanyInfo(CompanyBean companyInfo) {
        mName = companyInfo.getName();
        mContact = companyInfo.getContact();
        mMobile = companyInfo.getMobile();
        mImageUrl = companyInfo.getImage_url();
        GlideUtils.getInstance().loadCircleImage(this, mIvLogo, mImageUrl, R.mipmap.icon_com_photo);
        if (mImageUrl.contains("com/")) {
            String[] path = mImageUrl.split("com/");
            if (path.length > 1) {
                mImageUrl = path[1];
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.ACTIVITY_RESULT_CODE_REFRESH) {
            mCompanyInfoPresenter.getCompanyByCid(mCid);
        } else if (resultCode == RESULT_OK) {
            if (requestCode == Constants.ACTIVITY_REQUEST_CODE_COMMON1
                    && data != null && data.getStringArrayListExtra("labels") != null) {

            } else if (requestCode == Constants.ACTIVITY_REQUEST_CODE_COMMON2
                    && data != null && !TextUtils.isEmpty(data.getStringExtra("url"))) {
                mTvWarehouse.setText(data.getStringExtra("url"));
            }
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
        showMessage("LOGO上传成功");
        mCompanyInfoPresenter.getCompanyByCid(mCid);
    }

    @Override
    public void uploadFiled() {
        showMessage("LOGO上传失败");
    }
}
