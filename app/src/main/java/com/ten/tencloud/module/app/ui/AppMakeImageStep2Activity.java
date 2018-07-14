package com.ten.tencloud.module.app.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.module.app.model.AppMakeModel;
import com.ten.tencloud.widget.dialog.AppMakeImageDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class AppMakeImageStep2Activity extends BaseActivity {

    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.btn_start)
    Button mBtnStart;
    @BindView(R.id.ll_failed)
    View mLlFailed;
    @BindView(R.id.ll_success)
    View mLlSuccess;


    private AppMakeModel mAppMakeModel;
    private String mImageVersion;
    private String mImageName;
    private String mBranchName;
    private AppBean mAppBean;

    // TODO: 2018/4/18 测试用
    private String dockerFile = "# Using a compact OS\n" +
            "FROM daocloud.io/nginx:1.11-alpine\n" +
            "\n" +
            "MAINTAINER Golfen Guo <golfen.guo@daocloud.io>\n" +
            "\n" +
            "# Add 2048 stuff into Nginx server\n" +
            "COPY . /usr/share/nginx/html\n" +
            "\n" +
            "EXPOSE 80\n" +
            "\n" +
            "# Start Nginx and keep it running background and start php\n" +
            "CMD sed -i \"s/ContainerID: /ContainerID: \"$(hostname)\"/g\" /usr/share/nginx/html/index.html && nginx -g \"daemon off;\"\n";
    private AppMakeImageDialog mAppMakeImageDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_make_image_step2);

        Intent intent = getIntent();
        mImageVersion = intent.getStringExtra("imageVersion");
        mImageName = intent.getStringExtra("imageName");
        mBranchName = intent.getStringExtra("branchName");
        mAppBean = intent.getParcelableExtra("appBean");

        initTitleBar(true, "构建镜像");

        initView();
        initData();
    }

    private void initView() {
        mBtnStart.setVisibility(View.VISIBLE);
        mEtCode.setText(dockerFile);
        mEtCode.setSelection(mEtCode.getText().toString().length());
    }

    private void initData() {
        mAppMakeModel = new AppMakeModel(new AppMakeModel.OnAppMakeListener() {
            @Override
            public void onSuccess() {
                showLogDialog("镜像构建成功", true);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mMakeStatus = 0;
                        mAppMakeImageDialog.setStatus(true);
                        mBtnStart.setVisibility(View.GONE);
                        mLlFailed.setVisibility(View.GONE);
                        mLlSuccess.setVisibility(View.VISIBLE);
                        findViewById(R.id.tv_edit).setVisibility(View.VISIBLE);
                        mEtCode.setFocusable(false);
                        mEtCode.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                        mEtCode.setClickable(false); // user navigates with wheel and selects widget
                    }
                });
            }

            @Override
            public void onFailure(String message) {
                showLogDialog(message, true);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mMakeStatus = 1;
                        mAppMakeImageDialog.setStatus(false);
                        mBtnStart.setVisibility(View.GONE);
                        mLlFailed.setVisibility(View.VISIBLE);
                        mLlSuccess.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onMessage(String text) {
                showLogDialog(text, true);
            }
        });
    }

    @OnClick({R.id.btn_start, R.id.tv_view_log, R.id.tv_include_images, R.id.btn_view_image, R.id.tv_edit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_edit:
                mEtCode.setFocusable(true);
                mEtCode.setFocusableInTouchMode(true); // user touches widget on phone with touch screen
                mEtCode.setClickable(true); // user navigates with wheel and selects widget
                mBtnStart.setVisibility(View.VISIBLE);
                mLlFailed.setVisibility(View.GONE);
                mLlSuccess.setVisibility(View.GONE);
                mBtnStart.setEnabled(true);
                mBtnStart.setText("重新构建");
                break;

            case R.id.btn_start:
//                String fileName = "dockerfile_" + System.currentTimeMillis();
//                FileUtils.writeTxtToFile(mEtCode.getText().toString().trim(), Constants.TEMP_DIR, fileName);
//                showMessage("写入成功");
                startMake();
                break;
            case R.id.tv_view_log:
                showLogDialog("", false);
                break;
            case R.id.btn_view_image: {
                Intent intent = new Intent(this, AppSubDetailActivity.class);
                intent.putExtra("viewImage",true);
                startActivity(intent);
                break;
            }
            case R.id.tv_include_images: {
                Intent intent = new Intent(this, AppIncludeImageActivity.class);
                startActivityForResult(intent, 0);
                break;
            }
        }
    }

    //构建状态
    private int mMakeStatus = -1; // -1 初始化状态 0 成功 1 失败

    private void showLogDialog(final String msg, final boolean isAdd) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mAppMakeImageDialog == null) {
                    mAppMakeImageDialog = new AppMakeImageDialog(mContext);
                    mAppMakeImageDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            if (mMakeStatus == 1) {
                                mBtnStart.setVisibility(View.VISIBLE);
                                mLlFailed.setVisibility(View.GONE);
                                mLlSuccess.setVisibility(View.GONE);
                                mBtnStart.setEnabled(true);
                                mBtnStart.setText("重新构建");
                            }
                        }
                    });
                }
                if (!mAppMakeImageDialog.isShowing()) {
                    mAppMakeImageDialog.show();
                }
                mAppMakeImageDialog.setLog(msg, isAdd);
            }
        });
    }

    private void startMake() {
        String dockerFile = mEtCode.getText().toString();
        Map<String, Object> map = new HashMap<>();
        map.put("image_name", mImageName);
        map.put("version", mImageVersion);
        map.put("repos_https_url", mAppBean.getRepos_https_url());
        map.put("branch_name", mBranchName);
        map.put("app_id", mAppBean.getId());
        map.put("app_name", mAppBean.getName());
        map.put("dockerfile", dockerFile);
        String json = TenApp.getInstance().getGsonInstance().toJson(map);
        mAppMakeModel.connect();
        mAppMakeModel.send(json);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mBtnStart.setText("构建中...请稍候...");
                mBtnStart.setEnabled(false);
            }
        });
        mMakeStatus = -1;
        showLogDialog("正在构建中...请稍候...\n", true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Constants.ACTIVITY_RESULT_CODE_FINISH) {
            String imageName = data.getStringExtra("imageName");
            String imageVersion = data.getStringExtra("imageVersion");
            mEtCode.append("FROM " + imageName + ":" + imageVersion + "\n");
            mEtCode.setSelection(mEtCode.getText().toString().length());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAppMakeModel.close();
        mAppMakeModel.onDestroy();
        mAppMakeModel = null;
    }
}
