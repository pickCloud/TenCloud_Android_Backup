package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppBean;

import butterknife.BindView;

public class AppK8sRegularDeployStep1Activity extends BaseActivity {

    @BindView(R.id.et_name)
    EditText mEtName;

    private AppBean mAppBean;

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
    }

    private void next() {
        String name = mEtName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            showMessage("名称不能为空");
            return;
        }
        Intent intent = new Intent(mContext, AppK8sRegularDeployStep2Activity.class);
        intent.putExtra("name", name);
        intent.putExtra("appBean", mAppBean);
        startActivity(intent);
    }
}
