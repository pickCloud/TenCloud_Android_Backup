package com.ten.tencloud.module.server.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.module.server.model.ServerAddModel;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.OkHttpClient;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class ServerAddActivity extends BaseActivity {

    public final static String addUrl = "/api/server/new";

    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_ip)
    EditText mEtIp;
    @BindView(R.id.et_user)
    EditText mEtUser;
    @BindView(R.id.et_password)
    EditText mEtPassword;

    private WebSocketListener mWebSocketListener;
    private OkHttpClient mClient;
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
        mServerAddModel = new ServerAddModel(new ServerAddModel.onServerAddListener() {
            @Override
            public void setData(WebSocket webSocket) {
                sendData(webSocket);
            }
        });
    }

    private void sendData(WebSocket webSocket) {
        Map<String, String> map = new HashMap<>();
        map.put("cluster_id", "0");
        map.put("name", mName);
        map.put("public_ip", mIp);
        map.put("username", mUser);
        map.put("passwd", mPasswd);
        String json = TenApp.getInstance().getGsonInstance().toJson(map);
        webSocket.send(json);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showMessage("已提交请等待");
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
        mServerAddModel.connect();
    }
}
