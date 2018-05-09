package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.module.app.contract.AppK8sDeployContract;
import com.ten.tencloud.module.app.presenter.AppK8sDeployPresenter;

import butterknife.BindView;

public class AppK8sRegularDeployStep1Activity extends BaseActivity implements AppK8sDeployContract.View {

    @BindView(R.id.et_name)
    EditText mEtName;

    private AppBean mAppBean;
    private AppK8sDeployPresenter mPresenter;
    private String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_k8s_regular_deploy_step1);
        initTitleBar(true, "kubernetes常规部署", "下一步", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
        mAppBean = getIntent().getParcelableExtra("appBean");
        mPresenter = new AppK8sDeployPresenter();
        mPresenter.attachView(this);
    }

    private void next() {
        mName = mEtName.getText().toString().trim().toLowerCase();
        if (TextUtils.isEmpty(mName)) {
            showMessage("名称不能为空");
            return;
        }
        mPresenter.checkDeployName(mName, mAppBean.getId());
    }

    @Override
    public void checkResult() {
        Intent intent = new Intent(mContext, AppK8sRegularDeployStep2Activity.class);
        intent.putExtra("name", mName);
        intent.putExtra("appBean", mAppBean);
        startActivity(intent);
    }

    @Override
    public void showYAML(String yaml) {

    }
}
