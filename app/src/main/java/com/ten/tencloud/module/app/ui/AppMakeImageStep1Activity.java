package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.broadcast.RefreshBroadCastHandler;
import com.ten.tencloud.constants.IntentKey;
import com.ten.tencloud.listener.OnRefreshWithDataListener;

import butterknife.BindView;
import butterknife.OnClick;

public class AppMakeImageStep1Activity extends BaseActivity {

    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_version)
    EditText mEtVersion;
    @BindView(R.id.tv_branch)
    TextView mTvBranch;
    private AppBean mAppBean;
    private RefreshBroadCastHandler mBranchRefreshHandler;

    private String mBranch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_make_image_step1);
        initTitleBar(true, "构建镜像", "下一步", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
        mBranchRefreshHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.APP_BRANCH_CHANGE_ACTION);
        mBranchRefreshHandler.registerReceiver(new OnRefreshWithDataListener() {

            @Override
            public void onRefresh(Bundle bundle) {
                mTvBranch.setText(bundle.getString("branchName"));
                mBranch = bundle.getString("branchName");
            }
        });
        mAppBean = getIntent().getParcelableExtra(IntentKey.APP_ITEM);

        if (!ObjectUtils.isEmpty(mAppBean.getName())){
            mEtName.setText(mAppBean.getName());
        }
    }

    private void next() {
        String imageVersion = mEtVersion.getText().toString().trim();
        String imageName = mEtName.getText().toString().trim();
        if (TextUtils.isEmpty(imageVersion)) {
            showMessage("请填写版本号");
            return;
        }
        if (TextUtils.isEmpty(imageName)) {
            showMessage("请填写镜像名称");
            return;
        }
        if (TextUtils.isEmpty(mBranch)) {
            showMessage("请选择分支");
            return;
        }
        Intent intent = new Intent(mContext, AppMakeImageStep2Activity.class);
        intent.putExtra("imageVersion", imageVersion);
        intent.putExtra("imageName", imageName);
        intent.putExtra("branchName", mBranch);
        intent.putExtra("appBean", mAppBean);
        startActivity(intent);
    }

    @OnClick({R.id.ll_select_branch})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_select_branch:
                Intent intent = new Intent(this, AppBranchesActivity.class);
                intent.putExtra("reposName", mAppBean.getRepos_name());
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBranchRefreshHandler.unregisterReceiver();
    }
}
