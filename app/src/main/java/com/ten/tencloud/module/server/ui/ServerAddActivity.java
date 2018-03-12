package com.ten.tencloud.module.server.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ten.tencloud.BuildConfig;
import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.broadcast.RefreshBroadCastHandler;
import com.ten.tencloud.module.server.model.ServerAddModel;
import com.ten.tencloud.widget.dialog.ServerAddFailureDialog;
import com.ten.tencloud.widget.dialog.ServerAddLogDialog;
import com.ten.tencloud.widget.dialog.ServerAddSuccessDialog;

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
    private RefreshBroadCastHandler mServerHandler;

//    private String name = "测试";
//    private String ip = "47.96.129.231";
//    private String user = "root";
//    private String pw = "Test1234";

    private String name = "";
    private String ip = "";
    private String user = "";
    private String pw = "";

    private String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_server_add);
        initTitleBar(true, "添加主机");

        if (BuildConfig.DEBUG) {
            mEtIp.setText(ip);
            mEtName.setText(name);
            mEtPassword.setText(pw);
            mEtUser.setText(user);
        }

        mServerHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.SERVER_LIST_CHANGE_ACTION);
        mServerAddModel = new ServerAddModel(new ServerAddModel.onServerAddListener() {

            @Override
            public void onSuccess() {
                showLogDialog("主机添加成功", true);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBtnAdd.setText("确认添加");
                        mBtnAdd.setEnabled(true);
                        mServerHandler.sendBroadCast();
                        mServerAddLogDialog.cancel();
                        new ServerAddSuccessDialog(mContext, new ServerAddSuccessDialog.OnBtnListener() {
                            @Override
                            public void onAdd() {
                                resetParams();
                            }

                            @Override
                            public void onView() {
                                finish();
                                startActivityNoValue(mContext, ServerListActivity.class);
                            }
                        }).show();
                    }
                });
            }

            @Override
            public void onFailure(final String message) {
                showLogDialog(message, true);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBtnAdd.setText("确认添加");
                        mBtnAdd.setEnabled(true);
                        mServerAddLogDialog.cancel();
                        ServerAddFailureDialog serverAddFailureDialog = new ServerAddFailureDialog(mContext);
                        serverAddFailureDialog.show();
                        serverAddFailureDialog.setCause(msg);
                    }
                });
            }

            @Override
            public void onMessage(String text) {
                msg = text;
                showLogDialog(text, true);
            }
        });
//        mServerAddModel.connect();
    }

    private void resetParams() {
        mEtIp.setText("");
        mEtName.setText("");
        mEtPassword.setText("");
        mEtUser.setText("");
    }

    private void sendData() {
        Map<String, String> map = new HashMap<>();
        map.put("cluster_id", "1");
        map.put("name", mName);
        map.put("public_ip", mIp);
        map.put("username", mUser);
        map.put("passwd", mPasswd);
        String json = TenApp.getInstance().getGsonInstance().toJson(map);
        mServerAddModel.connect();
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
        mServerAddModel.onDestroy();
        mServerAddModel = null;
    }

}
