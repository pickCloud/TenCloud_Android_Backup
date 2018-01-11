package com.ten.tencloud.module.user.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.PermissionTemplateBean;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.module.user.contract.PermissionNewContract;
import com.ten.tencloud.module.user.presenter.PermissionNewPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class PermissionTemplateNewActivity extends BaseActivity implements PermissionNewContract.View {

    @BindView(R.id.et_template_name)
    EditText mEtTemplateName;
    @BindView(R.id.tv_func_count)
    TextView mTvFuncCount;
    @BindView(R.id.tv_data_count)
    TextView mTvDataCount;
    private PermissionTemplateBean mTemplateBean;
    private PermissionNewPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_permission_template_new);
        initTitleBar(true, "新增权限模版");
        mTemplateBean = new PermissionTemplateBean();
        mTemplateBean.setCid(AppBaseCache.getInstance().getCid());
        mPresenter = new PermissionNewPresenter();
        mPresenter.attachView(this);
    }

    @OnClick({R.id.ll_change_permission})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_change_permission:
                Intent intent = new Intent(this, PermissionTreeActivity.class);
                intent.putExtra("type", PermissionTreeActivity.TYPE_NEW);
                intent.putExtra("obj", mTemplateBean);
                startActivityForResult(intent, 0);
                overridePendingTransition(R.anim.slide_in_top, 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Constants.ACTIVITY_RESULT_CODE_REFRESH) {
            String json = data.getStringExtra("json");
            PermissionTemplateBean temp = TenApp.getInstance().getGsonInstance().fromJson(json, PermissionTemplateBean.class);
            int funcCount = handCount(temp.getAccess_filehub()) + handCount(temp.getAccess_projects()) + handCount(temp.getAccess_servers());
            mTvDataCount.setText(funcCount + "");
            mTvFuncCount.setText(handCount(temp.getPermissions()) + "");
            mTemplateBean.setPermissions(temp.getPermissions());
            mTemplateBean.setAccess_filehub(temp.getAccess_filehub());
            mTemplateBean.setAccess_projects(temp.getAccess_projects());
            mTemplateBean.setAccess_servers(temp.getAccess_servers());
        }
    }

    /**
     * 处理权限数量
     *
     * @param permission
     * @return
     */
    private int handCount(String permission) {
        if (TextUtils.isEmpty(permission)) {
            return 0;
        }
        return permission.split(",").length;
    }

    public void submit(View view) {
        String name = mEtTemplateName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            showMessage("模版名称不能为空");
            return;
        }
        String funcCount = mTvFuncCount.getText().toString().trim();
        String dataCount = mTvDataCount.getText().toString().trim();
        if ("0".equals(funcCount) && "0".equals(dataCount)) {
            mTemplateBean.setAccess_servers("");
            mTemplateBean.setAccess_projects("");
            mTemplateBean.setAccess_filehub("");
            mTemplateBean.setPermissions("");
        }
        mTemplateBean.setName(name);
        mPresenter.addTemplate(mTemplateBean);
    }

    @Override
    public void addSuccess() {
        showMessage("新增成功");
        setResult(Constants.ACTIVITY_RESULT_CODE_REFRESH);
        finish();
    }
}
