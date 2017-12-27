package com.ten.tencloud.module.user.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.PermissionTemplateBean;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.module.user.contract.PermissionNewContract;
import com.ten.tencloud.module.user.presenter.PermissionNewPresenter;

import org.greenrobot.essentials.StringUtils;

import java.util.List;
import java.util.Map;

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
        initTitleBar(true, "新增权限模板");
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
            Map<String, List<Integer>> map = TenApp.getInstance().getGsonInstance().fromJson(json, new TypeToken<Map<String, List<Integer>>>() {
            }.getType());
            List<Integer> permissions = map.get("permissions");
            List<Integer> access_filehub = map.get("access_filehub");
            List<Integer> access_projects = map.get("access_projects");
            List<Integer> access_servers = map.get("access_servers");
            int funcCount = access_filehub.size() + access_projects.size() + access_servers.size();
            mTvDataCount.setText(funcCount + "");
            mTvFuncCount.setText(permissions.size() + "");
            mTemplateBean.setPermissions(StringUtils.join(permissions, ","));
            mTemplateBean.setAccess_filehub(StringUtils.join(access_filehub, ","));
            mTemplateBean.setAccess_projects(StringUtils.join(access_projects, ","));
            mTemplateBean.setAccess_servers(StringUtils.join(access_servers, ","));
        }
    }

    public void submit(View view) {
        String name = mEtTemplateName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            showMessage("模板名称不能为空");
            return;
        }
        mTemplateBean.setName(name);
        mPresenter.addTemplate(mTemplateBean);
    }

    @Override
    public void addSuccess() {
        showMessage("新增成功");
        finish();
    }
}
