package com.ten.tencloud.module.user.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.PermissionTemplateBean;
import com.ten.tencloud.constants.Constants;

import butterknife.BindView;
import butterknife.OnClick;

public class PermissionChangeActivity extends BaseActivity {

    @BindView(R.id.tv_template_name)
    TextView mTvTemplateName;
    @BindView(R.id.tv_func_count)
    TextView mTvFuncCount;
    @BindView(R.id.tv_data_count)
    TextView mTvDataCount;

    private PermissionTemplateBean mTemplateBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_permission_change);
        initTitleBar(true, "修改权限模板");
        mTemplateBean = getIntent().getParcelableExtra("obj");
        initView();
    }

    private void initView() {
        mTvTemplateName.setText(mTemplateBean.getName());
        mTvFuncCount.setText("" + handCount(mTemplateBean.getPermissions()));
        int dataCount = handCount(mTemplateBean.getAccess_filehub()) +
                handCount(mTemplateBean.getAccess_projects()) +
                handCount(mTemplateBean.getAccess_servers());
        mTvDataCount.setText(dataCount + "");
    }

    @OnClick({R.id.ll_change_name, R.id.ll_change_permission})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_change_name: {
                Intent intent = new Intent(this, PermissionChangeNameActivity.class);
                intent.putExtra("obj", mTemplateBean);
                startActivityForResult(intent, 0);
                break;
            }
            case R.id.ll_change_permission: {
                Intent intent = new Intent(this, PermissionTreeActivity.class);
                intent.putExtra("obj", mTemplateBean);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_top, 0);
                break;
            }
        }
    }

    private int handCount(String permission) {
        if (TextUtils.isEmpty(permission)) {
            return 0;
        }
        return permission.split(",").length;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Constants.ACTIVITY_RESULT_CODE_REFRESH) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name)) {
                mTvTemplateName.setText(name);
            }
        }
    }
}
