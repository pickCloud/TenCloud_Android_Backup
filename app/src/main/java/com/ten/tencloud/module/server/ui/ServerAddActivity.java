package com.ten.tencloud.module.server.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.module.server.model.ServerAddModel;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.WebSocket;

public class ServerAddActivity extends BaseActivity {

    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_ip)
    EditText mEtIp;
    @BindView(R.id.et_user)
    EditText mEtUser;
    @BindView(R.id.et_password)
    EditText mEtPassword;

    private String mName;
    private String mIp;
    private String mUser;
    private String mPasswd;
    private ServerAddModel mServerAddModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_server_add);
        initTitleBar(true, "添加主机");
        // TODO: 2017/12/19 Service
        mServerAddModel = new ServerAddModel(new ServerAddModel.onServerAddListener() {
            @Override
            public void setData(WebSocket webSocket) {
                sendData(webSocket);
            }
        });
    }

    private void sendData(WebSocket webSocket) {
        Map<String, String> map = new HashMap<>();
        map.put("cluster_id", "1");
        map.put("name", mName);
        map.put("public_ip", mIp);
        map.put("username", mUser);
        map.put("passwd", mPasswd);
        String json = TenApp.getInstance().getGsonInstance().toJson(map);
        webSocket.send(json);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showMessage("等待连接，大约需要一分钟");
            }
        });
    }

    /**
     * 添加服务器
     *
     * @param view
     */
    public void addServer(View view) {
        mName = mEtName.getText().toString().trim();
        mIp = mEtIp.getText().toString().trim();
        mUser = mEtUser.getText().toString().trim();
        mPasswd = mEtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(mName)) {
            showMessage(R.string.tips_verify_server_empty);
            return;
        }
        if (TextUtils.isEmpty(mIp)) {
            showMessage(R.string.tips_verify_ip_empty);
            return;
        }
        if (TextUtils.isEmpty(mUser)) {
            showMessage(R.string.tips_verify_user_empty);
            return;
        }
        if (TextUtils.isEmpty(mPasswd)) {
            showMessage(R.string.tips_verify_password_empty);
            return;
        }
        mServerAddModel.connect();
    }
}
