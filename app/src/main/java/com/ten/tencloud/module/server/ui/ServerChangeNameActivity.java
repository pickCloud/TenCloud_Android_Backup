package com.ten.tencloud.module.server.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.module.server.contract.ServerChangeNameContract;
import com.ten.tencloud.module.server.presenter.ServerChangeNamePresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class ServerChangeNameActivity extends BaseActivity implements ServerChangeNameContract.View {

    @BindView(R.id.et_server)
    EditText mEtServer;

    private String mId;
    private String mName;
    private ServerChangeNamePresenter mServerChangeNamePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_server_change_name);
        initTitleBar(true, "修改名称", "确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEtServer.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    showMessage("名称不能为空");
                    return;
                }
                if (name.equals(mName)) {
                    showMessage("名称未变化");
                    return;
                }
                mName = name;
                mServerChangeNamePresenter.changeName(mId, name);
            }
        });
        Intent intent = getIntent();
        mId = intent.getStringExtra("id");
        mName = intent.getStringExtra("name");
        mServerChangeNamePresenter = new ServerChangeNamePresenter();
        mServerChangeNamePresenter.attachView(this);
        initView();
    }

    private void initView() {
        if (!TextUtils.isEmpty(mName)) {
            mEtServer.setText(mName);
            mEtServer.setSelection(mName.length());//光标移到最后
        }
    }

    @OnClick({R.id.ib_del})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_del:
                mEtServer.setText("");
                break;
        }
    }

    @Override
    public void changeSuccess() {
        showMessage("修改成功");
        Intent data = new Intent();
        data.putExtra("name", mName);
        setResult(ServerDetailBasicPager.CODE_CHANGE_NAME, data);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mServerChangeNamePresenter.detachView();
        hideLoading();
    }
}
