package com.ten.tencloud.module.server.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.module.server.model.ServerAddModel;
import com.ten.tencloud.widget.dialog.ServerAddLogDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class ServerAddActivity extends BaseActivity {

    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_ip)
    EditText mEtIp;
    @BindView(R.id.et_user)
    EditText mEtUser;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.btn_add)
    Button mBtnAdd;

    private String mName;
    private String mIp;
    private String mUser;
    private String mPasswd;
    private ServerAddModel mServerAddModel;
    private ServerAddLogDialog mServerAddLogDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_server_add);
        initTitleBar(true, "添加主机");
        mServerAddModel = new ServerAddModel(new ServerAddModel.onServerAddListener() {

            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBtnAdd.setText("确认添加");
                        mBtnAdd.setEnabled(true);
                    }
                });
                showLogDialog("主机添加成功", true);
            }

            @Override
            public void onFailure(String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBtnAdd.setText("确认添加");
                        mBtnAdd.setEnabled(true);
                    }
                });
                showLogDialog(message, true);
            }

            @Override
            public void onMessage(String text) {
                showLogDialog(text, true);
            }
        });
        mServerAddModel.connect();
    }

    private void sendData() {
        Map<String, String> map = new HashMap<>();
        map.put("cluster_id", "1");
        map.put("name", mName);
        map.put("public_ip", mIp);
        map.put("username", mUser);
        map.put("passwd", mPasswd);
        String json = TenApp.getInstance().getGsonInstance().toJson(map);
        mServerAddModel.send(json);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mBtnAdd.setText("正在添加主机...请稍候...");
                mBtnAdd.setEnabled(false);
            }
        });
        showLogDialog("正在添加主机...请稍候...", true);
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
        sendData();
    }

    /**
     * 查看日志信息
     *
     * @param view
     */
    public void viewLog(View view) {
        showLogDialog("", false);
    }

    private void showLogDialog(final String msg, final boolean isAdd) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mServerAddLogDialog == null) {
                    mServerAddLogDialog = new ServerAddLogDialog(mContext);
                }
                if (!mServerAddLogDialog.isShowing()) {
                    mServerAddLogDialog.show();
                }
                mServerAddLogDialog.setLog(msg, isAdd);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mServerAddModel.close();
        mServerAddModel = null;
    }

}
