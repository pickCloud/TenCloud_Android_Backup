package com.ten.tencloud.module.server.ui;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.broadcast.RefreshBroadCastHandler;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.constants.GlobalStatusManager;
import com.ten.tencloud.listener.OnRefreshListener;
import com.ten.tencloud.module.server.contract.ServerOperationContract;
import com.ten.tencloud.module.server.presenter.ServerOperationPresenter;
import com.ten.tencloud.utils.StatusBarUtils;
import com.ten.tencloud.utils.Utils;
import com.ten.tencloud.widget.blur.BlurBuilder;
import com.ten.tencloud.widget.dialog.CommonDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lxq on 2017/11/24.
 */

public class ServerToolBoxActivity extends BaseActivity implements ServerOperationContract.View {

    /**
     * 点击的状态
     */
    private final static int CLICK_STATE_REBOOT = 1;
    private final static int CLICK_STATE_START = 2;
    private final static int CLICK_STATE_STOP = 3;

    @BindView(R.id.iv_blur)
    ImageView mIvBlur;

    @BindView(R.id.btn_tool8)
    LinearLayout mBtnTool8;//关机开机
    @BindView(R.id.tv_start_or_stop)
    TextView mTvStartOrStop;
    @BindView(R.id.iv_tool8)
    ImageView mIvTool8;
    @BindView(R.id.btn_tool9)
    LinearLayout mBtnTool9;//从重启
    @BindView(R.id.tv_tool9)
    TextView mTvTool9;
    @BindView(R.id.iv_tool9)
    ImageView mIvTool9;


    private RefreshBroadCastHandler mServerHandler;
    private RefreshBroadCastHandler mServerInfoHandler;
    private ServerOperationPresenter mServerOperationPresenter;
    private RefreshBroadCastHandler mRefreshBroadCastHandler;

    private boolean mPermissionDelServer;
    private boolean mPermissionStartServer;
    private boolean mPermissionChangeServer;

    private CommonDialog mCommonDialog;

    private String mMachineStatus = "";
    //是否正在查询状态
    private boolean isQuerying = false;
    private int clickState = 0;
    private String mServerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_server_toolbox);
        hideToolBar();
        StatusBarUtils.setColor(this, Color.BLACK);
        applyBlur();

        mServerHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.SERVER_LIST_CHANGE_ACTION);
        mServerInfoHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.SERVER_INFO_CHANGE_ACTION);
        mServerOperationPresenter = new ServerOperationPresenter();
        mServerOperationPresenter.attachView(this);

        mServerId = getIntent().getStringExtra("serverId");

        mCommonDialog = new CommonDialog(mContext)
                .setPositiveButton("确定", new CommonDialog.OnButtonClickListener() {
                    @Override
                    public void onClick(Dialog dialog) {
                        switch (clickState) {
                            case CLICK_STATE_REBOOT:
                                mServerOperationPresenter.rebootServer(mServerId);
                                break;
                            case CLICK_STATE_START:
                                mServerOperationPresenter.startServer(mServerId);
                                break;
                            case CLICK_STATE_STOP:
                                mServerOperationPresenter.stopServer(mServerId);
                                break;
                        }
                        dialog.dismiss();
                    }
                });

        mRefreshBroadCastHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.PERMISSION_REFRESH_ACTION);
        mRefreshBroadCastHandler.registerReceiver(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                initPermission();
            }
        });
        initPermission();
        initData();
    }

    private void initPermission() {
        mPermissionStartServer = Utils.hasPermission("开机关机");
    }

    private void initData() {
        queryState();
    }

    @OnClick({R.id.btn_close, R.id.btn_tool1, R.id.btn_tool2, R.id.btn_tool3,
            R.id.btn_tool4, R.id.btn_tool5, R.id.btn_tool6, R.id.btn_tool7,
            R.id.btn_tool8, R.id.btn_tool9})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_close: {
                finish();
                break;
            }
            case R.id.btn_tool1: {

                break;
            }
            case R.id.btn_tool2: {

                break;
            }
            case R.id.btn_tool3: {

                break;
            }
            case R.id.btn_tool4: {

                break;
            }
            case R.id.btn_tool5: {

                break;
            }
            case R.id.btn_tool6: {

                break;
            }
            case R.id.btn_tool7: {

                break;
            }
            //关机，开机
            case R.id.btn_tool8: {
                String state = mTvStartOrStop.getText().toString();
                if ("开机".equals(state)) {
                    clickState = CLICK_STATE_START;
                    mCommonDialog.setMessage("您确定开机吗?");
                } else {
                    clickState = CLICK_STATE_STOP;
                    mCommonDialog.setMessage("您确定关机吗?");
                }
                mCommonDialog.show();
                break;
            }
            //重启
            case R.id.btn_tool9: {
                clickState = CLICK_STATE_REBOOT;
                mCommonDialog.setMessage("您确定要重启该机器吗？");
                mCommonDialog.show();
                break;
            }

        }
    }

    private void applyBlur() {
        mIvBlur.setImageBitmap(BlurBuilder.blur(mIvBlur));
        if (BlurBuilder.isBlurFlag()) {
            mIvBlur.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 根据状态设置按钮状态
     *
     * @param state
     */

    private void setState(String state) {
        //状态发生变化
        if (!mMachineStatus.equals(state)) {
            mServerInfoHandler.sendBroadCast();
            mServerHandler.sendBroadCast();
        }
        mMachineStatus = state;
        if (mPermissionStartServer) {
            if ("已关机".equals(state) || ("已停止").equals(state)) {
                setEnabledWithTool8(true);
                mTvStartOrStop.setText("开机");
                setEnabledWithTool9(false);
            } else if ("运行中".equals(state) || "故障".equals(state) || ("异常").equals(state)) {
                setEnabledWithTool8(true);
                mTvStartOrStop.setText("关机");
                setEnabledWithTool9(true);
            } else {
                setEnabledWithTool8(false);
                setEnabledWithTool9(false);
            }
        }
    }

    private void setEnabledWithTool8(boolean enabled) {
        mBtnTool8.setEnabled(enabled);
        mTvStartOrStop.setEnabled(enabled);
        mIvTool8.setEnabled(enabled);
    }

    private void setEnabledWithTool9(boolean enabled) {
        mBtnTool9.setEnabled(enabled);
        mTvTool9.setEnabled(enabled);
        mIvTool9.setEnabled(enabled);
    }

    private void queryState() {
        if (!isQuerying && !TextUtils.isEmpty(mServerId)) {
            mServerOperationPresenter.queryServerState(mServerId);
            isQuerying = true;
        }
    }

    @Override
    public void rebootSuccess() {
        showMessage("重启中");
        setState("重启中");
    }

    @Override
    public void delSuccess() {
        showMessage("删除成功");
        Activity activity = (Activity) mContext;
        activity.setResult(Constants.ACTIVITY_RESULT_CODE_FINISH);
        activity.finish();
        mServerHandler.sendBroadCast();
        GlobalStatusManager.getInstance().setServerListNeedRefresh(true);
    }

    @Override
    public void startSuccess() {
        showMessage("正在开机");
        setState("开机中");
    }

    @Override
    public void stopSuccess() {
        showMessage("正在关机");
        setState("关机中");
    }

    @Override
    public void showState(String state) {
        setState(state);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BlurBuilder.recycle();
        mServerOperationPresenter.unSubscribe();
        mRefreshBroadCastHandler.unregisterReceiver();
    }
}
