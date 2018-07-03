package com.ten.tencloud.module.image.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.module.other.contract.QiniuContract;
import com.ten.tencloud.module.other.presenter.QiniuPresenter;
import com.ten.tencloud.utils.SelectPhotoHelper;
import com.ten.tencloud.utils.glide.GlideUtils;
import com.ten.tencloud.widget.dialog.ImageAddSuccessDialog;
import com.ten.tencloud.widget.dialog.PhotoSelectDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class ImageAddActivity extends BaseActivity implements QiniuContract.View, InvokeListener, TakePhoto.TakeResultListener {

    @BindView(R.id.iv_logo)
    ImageView mIvLogo;

    private ImageAddSuccessDialog mImageAddSuccessDialog;
    private PhotoSelectDialog mPhotoSelectDialog;
    private SelectPhotoHelper mPhotoHelper;
    private QiniuPresenter mQiniuPresenter;
    private InvokeParam invokeParam;
    private TakePhoto takePhoto;

    private String mLogoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_image_add);
        initTitleBar(true, "添加镜像");

        mQiniuPresenter = new QiniuPresenter();
        mQiniuPresenter.attachView(this);

        SelectPhotoHelper.Options options = new SelectPhotoHelper.Options();
        options.isCrop = true;
        options.isCompress = true;
        options.cropHeight = 400;
        options.cropWidth = 400;
        mPhotoHelper = new SelectPhotoHelper(options);

        initView();
        initData();
    }

    private void initView() {
        mImageAddSuccessDialog = new ImageAddSuccessDialog(mContext, new ImageAddSuccessDialog.OnBtnListener() {
            @Override
            public void onSelectFile() {
                startActivityNoValue(mContext, ImageAddSelectFileActivity.class);
            }
        });
    }

    private void initData() {

    }

    @OnClick({R.id.btn_image_add, R.id.ll1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_image_add:
                mImageAddSuccessDialog.show();
                break;
            case R.id.ll1:
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
        }
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


    @Override
    public void takeSuccess(TResult result) {
        String path = result.getImage().getCompressPath();//压缩后的路径
        GlideUtils.getInstance().loadCircleImage(this, mIvLogo, path, R.mipmap.icon_app_photo);
        mQiniuPresenter.uploadFile(path);
    }

    @Override
    public void takeFail(TResult result, String msg) {
    }

    @Override
    public void takeCancel() {
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
        mLogoUrl = path;
    }

    @Override
    public void uploadFiled() {
        showMessage("图片上传失败");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mQiniuPresenter.detachView();
    }
}
