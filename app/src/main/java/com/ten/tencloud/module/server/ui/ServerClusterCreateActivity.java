package com.ten.tencloud.module.server.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.widget.dialog.ServerClusterCreateSuccessDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class ServerClusterCreateActivity extends BaseActivity {

    public static final int REQUEST_CODE_TYPE = 100;
    public static final int REQUEST_CODE_SERVER = 101;

    @BindView(R.id.tv_type)
    TextView mTvType;
    private ServerClusterCreateSuccessDialog mServerClusterCreateSuccessDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_server_cluster_create);
        initTitleBar(true, "创建集群");
        initView();
    }

    private void initView() {
        mServerClusterCreateSuccessDialog = new ServerClusterCreateSuccessDialog(this, new ServerClusterCreateSuccessDialog.OnBtnListener() {
            @Override
            public void onAdd() {
                mServerClusterCreateSuccessDialog.cancel();
            }

            @Override
            public void onView() {
                mServerClusterCreateSuccessDialog.cancel();
                finish();
            }
        });
    }

    @OnClick({R.id.ll_type, R.id.ll_server, R.id.btn_create})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_type: {
                Intent intent = new Intent(this, ServerClusterCreateTypeActivity.class);
                startActivityForResult(intent, REQUEST_CODE_TYPE);
                break;
            }
            case R.id.ll_server: {
                Intent intent = new Intent(this, ServerClusterCreateServerActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SERVER);
                break;
            }
            case R.id.btn_create:
                mServerClusterCreateSuccessDialog.show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Constants.ACTIVITY_RESULT_CODE_FINISH) {
            if (requestCode == REQUEST_CODE_TYPE) {
                mTvType.setText(data.getStringExtra("type"));
                mTvType.setTextColor(getResources().getColor(R.color.text_color_899ab6));
            }
            if (requestCode == REQUEST_CODE_SERVER) {
                // TODO: 2018/4/27
            }
        }
    }
}
