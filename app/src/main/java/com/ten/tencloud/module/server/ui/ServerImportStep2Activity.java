package com.ten.tencloud.module.server.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.ServerBatchBean;
import com.ten.tencloud.module.server.contract.ServerAddBatchStep2Contract;
import com.ten.tencloud.module.server.presenter.ServerAddBatchStep2Presenter;

import java.util.List;

import butterknife.BindView;

public class ServerImportStep2Activity extends BaseActivity implements ServerAddBatchStep2Contract.View {

    @BindView(R.id.et_key)
    EditText mEtKey;
    @BindView(R.id.et_secret)
    EditText mEtSecret;

    private String key = "";
    private String secret = "";

    private ServerAddBatchStep2Presenter mPresenter;
    private int mCloudId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_server_add_batch_step2);
        initTitleBar(true, "批量添加云主机", "下一步", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        mPresenter = new ServerAddBatchStep2Presenter();
        mPresenter.attachView(this);

        mCloudId = getIntent().getIntExtra("cloudId", 0);

        initView();
    }

    private void initView() {
        mEtKey.setText(key);
        mEtSecret.setText(secret);
    }

    private void submit() {
        String key = mEtKey.getText().toString();
        String secret = mEtSecret.getText().toString();
        if (TextUtils.isEmpty(key)){
            showMessage("请填写Access Key");
            return;
        }
        if (TextUtils.isEmpty(secret)){
            showMessage("请填写Access Secret");
            return;
        }
        mPresenter.submitCredential(mCloudId, key, secret);
    }

    public void userDemoKey(View view) {
        key = "LTAIEouRscyh8evG";
        secret = "D6sGmGSJhG53ZGZl0ptXTPqkm18HA3";
        initView();
    }

    @Override
    public void showProviderServers(List<ServerBatchBean> servers) {
        for (ServerBatchBean server : servers) {
            server.setCloud_type(mCloudId);
        }
        String json = TenApp.getInstance().getGsonInstance().toJson(servers);
        Intent intent = new Intent(mContext, ServerImportStep3Activity.class);
        intent.putExtra("data", json);
        startActivity(intent);
    }


}
