package com.ten.tencloud.module.server.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BasePager;
import com.ten.tencloud.bean.ServerDetailBean;
import com.ten.tencloud.module.server.contract.ServerDetailContract;
import com.ten.tencloud.module.server.contract.ServerOperationContract;
import com.ten.tencloud.module.server.presenter.ServerDetailPresenter;
import com.ten.tencloud.module.server.presenter.ServerOperationPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lxq on 2017/11/29.
 */

public class ServerDetailBasicPager extends BasePager implements ServerDetailContract.View, ServerOperationContract.View {

    /**
     * 点击的状态
     */
    private final static int CLICK_STATE_REBOOT = 1;
    private final static int CLICK_STATE_START = 2;
    private final static int CLICK_STATE_STOP = 3;
    private final static int CLICK_STATE_DEL = 4;

    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_provider)
    TextView mTvProvider;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tv_ip)
    TextView mTvIP;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.tv_add_time)
    TextView mTvAddTime;
    @BindView(R.id.btn_restart)
    Button mBtnRestart;
    @BindView(R.id.btn_start)
    Button mBtnStart;
    @BindView(R.id.btn_stop)
    Button mBtnStop;

    private boolean isFirst = true;
    private ServerDetailPresenter mServerDetailPresenter;
    private String mId;
    private ServerOperationPresenter mServerOperationPresenter;
    private AlertDialog mDialog;

    private int clickState = 0;
    private String mName;

    public ServerDetailBasicPager(Context context) {
        super(context);
        mServerDetailPresenter = new ServerDetailPresenter();
        mServerOperationPresenter = new ServerOperationPresenter();
        mServerDetailPresenter.attachView(this);
        mServerOperationPresenter.attachView(this);
        mDialog = new AlertDialog.Builder(mContext)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (clickState) {
                            case CLICK_STATE_REBOOT:
                                mServerOperationPresenter.rebootServer(mId);
                                break;
                            case CLICK_STATE_START:
                                mServerOperationPresenter.startServer(mId);
                                break;
                            case CLICK_STATE_STOP:
                                mServerOperationPresenter.stopServer(mId);
                                break;
                            case CLICK_STATE_DEL:
                                mServerOperationPresenter.delServer(mId);
                                break;
                        }
                        mServerOperationPresenter.rebootServer(mId);
                    }
                }).create();
    }


    @OnClick({R.id.ll_name, R.id.btn_restart, R.id.btn_del, R.id.btn_start, R.id.btn_stop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_name:
                Intent intent = new Intent(mContext, ServerChangeNameActivity.class);
                intent.putExtra("id", mId);
                intent.putExtra("name", mName);
                mContext.startActivity(intent);
                break;
            case R.id.btn_restart:
                clickState = CLICK_STATE_REBOOT;
                mDialog.setMessage("您确定要重启该机器吗？");
                mDialog.show();
                break;
            case R.id.btn_del:
                clickState = CLICK_STATE_DEL;
                mDialog.setMessage("您确定删除 " + mName + " 吗?");
                mDialog.show();
                break;
            case R.id.btn_start:
                clickState = CLICK_STATE_START;
                mDialog.setMessage("您确定开机吗？");
                mDialog.show();
                break;
            case R.id.btn_stop:
                clickState = CLICK_STATE_STOP;
                mDialog.setMessage("您确定关机吗？");
                mDialog.show();
                break;
        }
    }

    @Override
    public void init() {
        if (isFirst) {
            mId = getArgument("id");
            createView(R.layout.pager_server_detail_basic);
            mServerDetailPresenter.getServerDetail(mId);
        }
    }

    @Override
    public void showServerDetail(ServerDetailBean serverDetailBean) {
        isFirst = false;
        mName = serverDetailBean.getBasic_info().getName();
        mTvName.setText(mName);
        mTvProvider.setText(serverDetailBean.getBusiness_info().getProvider());
        mTvAddress.setText(serverDetailBean.getBasic_info().getAddress());
        mTvIP.setText(serverDetailBean.getBasic_info().getPublic_ip());
        String machine_status = serverDetailBean.getBasic_info().getMachine_status();
        if ("运行中".equals(machine_status)) {
            mTvStatus.setEnabled(true);
        } else {
            mTvStatus.setEnabled(false); //已停止和异常
        }
        if ("已停止".equals(machine_status)) {
            mBtnStop.setVisibility(GONE);
            mBtnRestart.setVisibility(GONE);
            mBtnStart.setVisibility(VISIBLE);
        } else {
            mBtnStop.setVisibility(VISIBLE);
            mBtnRestart.setVisibility(VISIBLE);
            mBtnStart.setVisibility(GONE);
        }
        mTvStatus.setText(machine_status);
        mTvAddTime.setText(serverDetailBean.getBasic_info().getCreated_time());
    }

    @Override
    public void rebootSuccess() {
        showMessage("重启成功");
    }

    @Override
    public void delSuccess() {
        showMessage("删除成功");
    }

    @Override
    public void startSuccess() {
        showMessage("开机成功");
    }

    @Override
    public void stopSuccess() {
        showMessage("关机成功");
    }
}
