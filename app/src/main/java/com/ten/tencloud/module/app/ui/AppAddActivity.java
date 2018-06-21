package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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
import com.orhanobut.logger.Logger;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.bean.DeploymentBean;
import com.ten.tencloud.bean.LabelBean;
import com.ten.tencloud.bean.ServiceBriefBean;
import com.ten.tencloud.broadcast.RefreshBroadCastHandler;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.listener.DialogListener;
import com.ten.tencloud.listener.OnRefreshWithDataListener;
import com.ten.tencloud.model.JesException;
import com.ten.tencloud.model.subscribe.JesSubscribe;
import com.ten.tencloud.module.app.contract.AppDetailContract;
import com.ten.tencloud.module.app.contract.SubApplicationContract;
import com.ten.tencloud.module.app.model.AppModel;
import com.ten.tencloud.module.app.presenter.AppDetailPresenter;
import com.ten.tencloud.module.app.presenter.SubApplicationPresenter;
import com.ten.tencloud.module.other.contract.QiniuContract;
import com.ten.tencloud.module.other.presenter.QiniuPresenter;
import com.ten.tencloud.utils.SelectPhotoHelper;
import com.ten.tencloud.utils.glide.GlideUtils;
import com.ten.tencloud.widget.CircleImageView;
import com.ten.tencloud.widget.dialog.LabelSelectDialog;
import com.ten.tencloud.widget.dialog.PhotoSelectDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenxh@10.com on 2018/3/26.
 */
public class AppAddActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener,
        QiniuContract.View, AppDetailContract.View, SubApplicationContract.View {

    @BindView(R.id.iv_logo)
    CircleImageView mIvLogo;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_description)
    EditText mEtDescription;
    @BindView(R.id.fbl_label)
    FlexboxLayout mFlexboxLayout;
    @BindView(R.id.tv_repos)
    TextView mTvRepos;
    @BindView(R.id.btn_sure_add)
    Button mBtnSureAdd;
    @BindView(R.id.tv_label_edit)
    TextView mTvLabelEdit;

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private SelectPhotoHelper mPhotoHelper;
    private QiniuPresenter mQiniuPresenter;

    private PhotoSelectDialog mPhotoSelectDialog;

    private String mLogoUrl;
    private String mAppName;
    private String mDescription;
    private String mReposName;
    private String mReposUrl;
    private String mReposHttpUrl;
    private AppBean mAppBean;
    private RefreshBroadCastHandler mAppRefreshHandler;
    private int mId;

    private AppDetailPresenter mAppDetailPresenter;
    private LabelSelectDialog mLabelSelectDialog;
    private ArrayList<LabelBean> mLabelBeans;
    private RefreshBroadCastHandler mImageRefreshHandler;
    private RefreshBroadCastHandler mAppInfoRefreshHandler;
    private int mMasterAppId;
    private int mType;//0-创建主应用，1-创建子应用
    private SubApplicationPresenter mSubApplicationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_service_app_add);

        mAppDetailPresenter = new AppDetailPresenter();
        mAppDetailPresenter.attachView(this);

        mSubApplicationPresenter = new SubApplicationPresenter();
        mSubApplicationPresenter.attachView(this);

        mId = getIntent().getIntExtra("id", -1);
        mMasterAppId = getIntent().getIntExtra("master_app", -1);
        mType = getIntent().getIntExtra("type", 0);

        if (mMasterAppId == -1){//主应用
            if (mId == -1) {
                initTitleBar(true, "添加应用");
                mBtnSureAdd.setText("确定添加");
            } else {
                initTitleBar(true, "修改应用");
                mBtnSureAdd.setText("确定修改");
                mAppDetailPresenter.getAppById(mId);
            }
        }else{//子应用
            if (mId == -1) {
                initTitleBar(true, "添加应用");
                mBtnSureAdd.setText("确定添加");
            } else {
                initTitleBar(true, "修改应用");
                mBtnSureAdd.setText("确定修改");
                mSubApplicationPresenter.getSubApplicationListById(mMasterAppId, mId);
            }
        }


        mAppRefreshHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.APP_LIST_CHANGE_ACTION);
        mAppInfoRefreshHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.APP_INFO_CHANGE_ACTION);
        mImageRefreshHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.IMAGE_SOURCE_CHANGE_ACTION);
        mImageRefreshHandler.registerReceiver(new OnRefreshWithDataListener() {

            @Override
            public void onRefresh(Bundle bundle) {
                mTvRepos.setText(bundle.getString("url"));
                mReposName = bundle.getString("name");
                mReposUrl = bundle.getString("url");
                mReposHttpUrl = bundle.getString("httpUrl");
            }
        });
        mQiniuPresenter = new QiniuPresenter();
        mQiniuPresenter.attachView(this);

        SelectPhotoHelper.Options options = new SelectPhotoHelper.Options();
        options.isCrop = true;
        options.isCompress = true;
        options.cropHeight = 400;
        options.cropWidth = 400;
        mPhotoHelper = new SelectPhotoHelper(options);

    }

    public void createLabelView(ArrayList<LabelBean> data) {
        mFlexboxLayout.removeAllViews();
        for (LabelBean label : data) {
            createLabelView(label);
        }
    }

    private void createLabelView(final LabelBean label) {
        View view = View.inflate(this, R.layout.item_app_service_label, null);
        final TextView checkBox = view.findViewById(R.id.tv_label_name);
        checkBox.setText(label.getName());
        mFlexboxLayout.addView(view);
    }

    @OnClick({R.id.ll1, R.id.fbl_label, R.id.tv_label_edit, R.id.tv_repos, R.id.btn_sure_add})
    public void onClick(View view) {
        switch (view.getId()) {
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
            case R.id.tv_label_edit:
            case R.id.fbl_label:
                mLabelSelectDialog = new LabelSelectDialog(this, new DialogListener<ArrayList<LabelBean>>() {
                    @Override
                    public void onRefresh(ArrayList<LabelBean> data) {
                        mLabelBeans = data;
                        if (data == null || data.size() == 0) {
                            mTvLabelEdit.setVisibility(View.VISIBLE);
                            mFlexboxLayout.setVisibility(View.GONE);
                            mFlexboxLayout.removeAllViews();
                        } else {
                            mTvLabelEdit.setVisibility(View.GONE);
                            mFlexboxLayout.setVisibility(View.VISIBLE);
                            createLabelView(data);
                        }
                    }
                });
                mLabelSelectDialog.show();
                mLabelSelectDialog.setEditLabelData(mLabelBeans);
                mLabelSelectDialog.setHistoryLabelData(mLabelBeans);
                break;
            case R.id.tv_repos:
                startActivityForResult(new Intent(this, AppRepositoryActivity.class), Constants.ACTIVITY_REQUEST_CODE_COMMON2);
                break;
            case R.id.btn_sure_add:
                addOrUpdateApp();
                break;
        }
    }

    private void addOrUpdateApp() {
        mAppName = mEtName.getText().toString().trim();
        mDescription = mEtDescription.getText().toString().trim();
        List<Integer> labels = new ArrayList<>();
        if (mLabelBeans != null) {
            for (LabelBean labelBean : mLabelBeans) {
                labels.add(labelBean.getId());
            }
        }
        if (TextUtils.isEmpty(mAppName)) {
            showToastMessage(R.string.tips_verify_app_empty);
            return;
        } else if ((TextUtils.isEmpty(mDescription))) {
            showToastMessage(R.string.tips_verify_app_decription_empty);
            return;
        }
        if (mType == 0) {//主应用
            if (mId == -1) {
                AppModel.getInstance()
                        .newApp(mAppName, mDescription, mReposName, mReposUrl, mReposHttpUrl, mLogoUrl, 0, labels, "0")
                        .subscribe(new JesSubscribe<Object>(this) {

                            @Override
                            public void onStart() {
                                super.onStart();
                                mBtnSureAdd.setEnabled(false);
                                mBtnSureAdd.setText("正在添加应用...请稍候...");
                            }

                            @Override
                            public void _onSuccess(Object o) {
                                showToastMessage("应用添加成功");
                                mBtnSureAdd.setEnabled(true);
                                mBtnSureAdd.setText("确定添加");
                                mAppRefreshHandler.sendBroadCast();
                                finish();
                            }

                            @Override
                            public void _onError(JesException e) {
                                super._onError(e);
                                showToastMessage(e.getMessage());
                                mBtnSureAdd.setEnabled(true);
                                mBtnSureAdd.setText("确定添加");
                            }
                        });
            } else {
                AppModel.getInstance()
                        .updateApp(mId, mAppName, mDescription, mReposName, mReposUrl, mReposHttpUrl, mLogoUrl, labels)
                        .subscribe(new JesSubscribe<Object>(this) {

                            @Override
                            public void onStart() {
                                super.onStart();
                                mBtnSureAdd.setEnabled(false);
                                mBtnSureAdd.setText("正在修改应用...请稍候...");
                            }

                            @Override
                            public void _onSuccess(Object o) {
                                showToastMessage("应用修改成功");
                                mBtnSureAdd.setEnabled(true);
                                mBtnSureAdd.setText("确定修改");
                                mAppRefreshHandler.sendBroadCast();
                                mAppInfoRefreshHandler.sendBroadCast();
                                finish();
                            }

                            @Override
                            public void _onError(JesException e) {
                                super._onError(e);
                                showToastMessage(e.getMessage());
                                mBtnSureAdd.setEnabled(true);
                                mBtnSureAdd.setText("确定修改");
                            }
                        });
            }
        }else if (mType == 1) {//子应用
            if (mId == -1) {
                AppModel.getInstance()
                        .newApp(mAppName, mDescription, mReposName, mReposUrl, mReposHttpUrl, mLogoUrl, 0, labels, mMasterAppId + "")
                        .subscribe(new JesSubscribe<Object>(this) {

                            @Override
                            public void onStart() {
                                super.onStart();
                                mBtnSureAdd.setEnabled(false);
                                mBtnSureAdd.setText("正在添加应用...请稍候...");
                            }

                            @Override
                            public void _onSuccess(Object o) {
                                showToastMessage("应用添加成功");
                                mBtnSureAdd.setEnabled(true);
                                mBtnSureAdd.setText("确定添加");
                                mAppRefreshHandler.sendBroadCast();
                                finish();
                            }

                            @Override
                            public void _onError(JesException e) {
                                super._onError(e);
                                showToastMessage(e.getMessage());
                                mBtnSureAdd.setEnabled(true);
                                mBtnSureAdd.setText("确定添加");
                            }
                        });
            } else {
                AppModel.getInstance()
                        .updateApp(mMasterAppId, mAppName, mDescription, mReposName, mReposUrl, mReposHttpUrl, mLogoUrl, labels)
                        .subscribe(new JesSubscribe<Object>(this) {

                            @Override
                            public void onStart() {
                                super.onStart();
                                mBtnSureAdd.setEnabled(false);
                                mBtnSureAdd.setText("正在修改应用...请稍候...");
                            }

                            @Override
                            public void _onSuccess(Object o) {
                                showToastMessage("应用修改成功");
                                mBtnSureAdd.setEnabled(true);
                                mBtnSureAdd.setText("确定修改");
                                mAppRefreshHandler.sendBroadCast();
                                mAppInfoRefreshHandler.sendBroadCast();
                                finish();
                            }

                            @Override
                            public void _onError(JesException e) {
                                super._onError(e);
                                showToastMessage(e.getMessage());
                                mBtnSureAdd.setEnabled(true);
                                mBtnSureAdd.setText("确定修改");
                            }
                        });
            }
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.ACTIVITY_REQUEST_CODE_COMMON1
                    && data != null && data.getStringArrayListExtra("labels") != null) {

            } else if (requestCode == Constants.ACTIVITY_REQUEST_CODE_COMMON2 && data != null) {
                mTvRepos.setText(data.getStringExtra("url"));
                mReposName = data.getStringExtra("name");
                mReposUrl = data.getStringExtra("url");
                mReposHttpUrl = data.getStringExtra("httpUrl");
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
        String path = result.getImage().getCompressPath();//压缩后的路径
        GlideUtils.getInstance().loadCircleImage(this, mIvLogo, path, R.mipmap.icon_app_photo);
        mQiniuPresenter.uploadFile(path);
    }

    @Override
    public void takeFail(TResult result, String msg) {
        Logger.i("takeFail:" + msg);
    }

    @Override
    public void takeCancel() {
        Logger.i(getResources().getString(com.jph.takephoto.R.string.msg_operation_canceled));
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
    public void showAppDetail(AppBean appBean) {
        mAppBean = appBean;
        mAppName = appBean.getName();
        mDescription = appBean.getDescription();
        mReposName = appBean.getRepos_name();
        mReposUrl = appBean.getRepos_ssh_url();
        mReposHttpUrl = appBean.getRepos_https_url();
        mLogoUrl = appBean.getLogo_url();

        mTvRepos.setText(appBean.getRepos_ssh_url());
        if (!TextUtils.isEmpty(appBean.getLogo_url())) {
            GlideUtils.getInstance().loadCircleImage(mContext, mIvLogo, appBean.getLogo_url(), R.mipmap.icon_app_photo);
            mLogoUrl = appBean.getLogo_url();
        }
        if (!TextUtils.isEmpty(appBean.getName()))
            mEtName.setText(appBean.getName());
        if (!TextUtils.isEmpty(appBean.getDescription()))
            mEtDescription.setText(appBean.getDescription());
        String label_name = appBean.getLabel_name();
        String labelId = appBean.getLabels();
        if (TextUtils.isEmpty(label_name) || TextUtils.isEmpty(labelId)) {
            return;
        }
        String[] labels = label_name.split(",");
        String[] labelIds;
        if (labelId.startsWith("0,")) {
            labelIds = labelId.replace("0,", "").split(",");
        } else {
            labelIds = labelId.split(",");
        }
        mLabelBeans = new ArrayList<>();

        for (int i = 0; i < labels.length; i++) {
            mLabelBeans.add(new LabelBean(Integer.parseInt(labelIds[i]), labels[i]));
        }
        if (mLabelBeans == null || mLabelBeans.size() == 0) {
            mTvLabelEdit.setVisibility(View.VISIBLE);
            mFlexboxLayout.setVisibility(View.GONE);
            mFlexboxLayout.removeAllViews();
        } else {
            mTvLabelEdit.setVisibility(View.GONE);
            mFlexboxLayout.setVisibility(View.VISIBLE);
            createLabelView(mLabelBeans);
        }
    }

    @Override
    public void showServiceBriefDetails(ServiceBriefBean serverBatchBean) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mQiniuPresenter.detachView();
        mAppDetailPresenter.detachView();
        mSubApplicationPresenter.detachView();
        mImageRefreshHandler.unregisterReceiver();
    }

    @Override
    public void showSubApplicationList(List<AppBean> appBeans) {

    }

    @Override
    public void showSubApplicationDetails(AppBean appBean) {
        showAppDetail(appBean);
    }

    @Override
    public void showDeploymentLatestDetails(DeploymentBean appBean) {

    }
}
