package com.ten.tencloud.module.user.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.PermissionTemplateBean;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.constants.GlobalStatusManager;
import com.ten.tencloud.module.user.contract.PermissionRenameContract;
import com.ten.tencloud.module.user.presenter.PermissionRenamePresenter;
import com.ten.tencloud.utils.Utils;

import butterknife.BindView;

public class PermissionChangeNameActivity extends BaseActivity implements PermissionRenameContract.View {

    @BindView(R.id.et_info)
    EditText mEtInfo;
    private String mName;
    private int mCid;
    private PermissionRenamePresenter mRenamePresenter;
    private int mPtId;
    private PermissionTemplateBean mTemplateBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_permission_change_name);
        initTitleBar(true, "修改权限名称", "确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mName = mEtInfo.getText().toString().trim();
                if (TextUtils.isEmpty(mName)) {
                    showMessage("名称不能为空");
                    return;
                }
                mRenamePresenter.renameTemplate(mPtId, mCid, mName);
            }
        });
        mRenamePresenter = new PermissionRenamePresenter();
        mRenamePresenter.attachView(this);
        initView();
    }

    private void initView() {
        mTemplateBean = getIntent().getParcelableExtra("obj");
        mName = mTemplateBean.getName();
        mCid = mTemplateBean.getCid();
        mPtId = mTemplateBean.getId();
        mEtInfo.setText(mName);
        mEtInfo.setSelection(Utils.isEmptyDefaultForString(mName, "").length());
    }

    public void btnDel(View view) {
        mEtInfo.setText("");
    }

    @Override
    public void success() {
        showMessage("修改成功");
        GlobalStatusManager.getInstance().setTemplateNeedRefresh(true);
        Intent data = new Intent();
        data.putExtra("name", mName);
        setResult(Constants.ACTIVITY_RESULT_CODE_REFRESH, data);
        finish();
    }
}
