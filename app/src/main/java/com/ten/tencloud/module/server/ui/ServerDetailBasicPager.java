package com.ten.tencloud.module.server.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BasePager;
import com.ten.tencloud.bean.ServerDetailBean;
import com.ten.tencloud.broadcast.RefreshBroadCastHandler;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.constants.GlobalStatusManager;
import com.ten.tencloud.listener.OnRefreshListener;
import com.ten.tencloud.module.server.contract.ServerDetailContract;
import com.ten.tencloud.module.server.contract.ServerOperationContract;
import com.ten.tencloud.module.server.presenter.ServerDetailPresenter;
import com.ten.tencloud.module.server.presenter.ServerOperationPresenter;
import com.ten.tencloud.utils.Utils;
import com.ten.tencloud.widget.dialog.CommonDialog;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscription;

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
    @BindView(R.id.iv_arrow)
    ImageView mIvArrow;
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
    @BindView(R.id.btn_del)
    Button mBtnDel;

    private ServerDetailPresenter mServerDetailPresenter;
    private ServerOperationPresenter mServerOperationPresenter;

    private boolean isFirst = true;
    private String mId;
    private CommonDialog mCommonDialog;

    private int clickState = 0;
    private String mName;

    //是否正在查询状态
    private boolean isQuerying = false;
    private String mInstanceId;
    private Subscription mAnimSubscribe;
    private boolean mPermissionDelServer;
    private boolean mPermissionStartServer;
    private boolean mPermissionChangeServer;
    private final RefreshBroadCastHandler mRefreshBroadCastHandler;
    private RefreshBroadCastHandler mServerHandler;
    private final RefreshBroadCastHandler mServerInfoHandler;
    private String mMachineStatus = "";


    public ServerDetailBasicPager(Context context) {
        super(context);
        mServerHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.SERVER_LIST_CHANGE_ACTION);
        mServerInfoHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.SERVER_INFO_CHANGE_ACTION);
        mServerDetailPresenter = new ServerDetailPresenter();
        mServerOperationPresenter = new ServerOperationPresenter();
        mServerDetailPresenter.attachView(this);
        mServerOperationPresenter.attachView(this);

        mCommonDialog = new CommonDialog(mContext)
                .setPositiveButton("确定", new CommonDialog.OnButtonClickListener() {
                    @Override
                    public void onClick(Dialog dialog) {
                        switch (clickState) {
                            case CLICK_STATE_REBOOT:
                                mServerOperationPresenter.rebootServer(mId);
                                setState("停止中");
                                break;
                            case CLICK_STATE_START:
                                mServerOperationPresenter.startServer(mId);
                                setState("启动中");
                                break;
                            case CLICK_STATE_STOP:
                                mServerOperationPresenter.stopServer(mId);
                                setState("停止中");
                                break;
                            case CLICK_STATE_DEL:
                                mServerOperationPresenter.delServer(mId);
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
                mServerDetailPresenter.getServerDetail(mId);
            }
        });

        initPermission();
    }

    private void initPermission() {
        mPermissionDelServer = Utils.hasPermission("删除主机");
        mPermissionStartServer = Utils.hasPermission("开机关机");
        mPermissionChangeServer = Utils.hasPermission("修改主机信息");
    }


    public final static int CODE_CHANGE_NAME = 1000;

    @OnClick({R.id.ll_name, R.id.btn_restart, R.id.btn_del, R.id.btn_start, R.id.btn_stop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_name:
                if (mPermissionChangeServer) {
                    Intent intent = new Intent(mContext, ServerChangeNameActivity.class);
                    intent.putExtra("id", mId);
                    intent.putExtra("name", mName);
                    ((Activity) mContext).startActivityForResult(intent, CODE_CHANGE_NAME);
                }
                break;
            case R.id.btn_restart:
                clickState = CLICK_STATE_REBOOT;
                mCommonDialog.setMessage("您确定要重启该机器吗？");
                mCommonDialog.show();
                break;
            case R.id.btn_del:
                clickState = CLICK_STATE_DEL;
                mCommonDialog.setMessage("您确定删除 " + mName + " 吗?");
                mCommonDialog.show();
                break;
            case R.id.btn_start:
                clickState = CLICK_STATE_START;
                mCommonDialog.setMessage("您确定开机吗?");
                mCommonDialog.show();
                break;
            case R.id.btn_stop:
                clickState = CLICK_STATE_STOP;
                mCommonDialog.setMessage("您确定关机吗?");
                mCommonDialog.show();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == CODE_CHANGE_NAME) {
            mName = data.getStringExtra("name");
            mTvName.setText(mName);
        }
    }

    @Override
    public void init() {
        if (isFirst) {
            mId = getArgument("id");
            createView(R.layout.pager_server_detail_basic);
            mIvArrow.setVisibility(mPermissionChangeServer ? VISIBLE : INVISIBLE);
            mServerDetailPresenter.getServerDetail(mId);
        }
    }

    @Override
    public void showServerDetail(ServerDetailBean serverDetailBean) {
        isFirst = false;
        mInstanceId = serverDetailBean.getBasic_info().getInstance_id();
        mName = serverDetailBean.getBasic_info().getName();
        mTvName.setText(mName);
        mTvProvider.setText(serverDetailBean.getBusiness_info().getProvider());
        mTvAddress.setText(serverDetailBean.getBasic_info().getAddress());
        mTvIP.setText(serverDetailBean.getBasic_info().getPublic_ip());
        mMachineStatus = serverDetailBean.getBasic_info().getMachine_status();
        mTvAddTime.setText(serverDetailBean.getBasic_info().getCreated_time());
        setState(mMachineStatus);
    }

    /**
     * 根据状态设置按钮状态
     *
     * @param state
     */
    private void setState(final String state) {
        //状态发生变化
        if (!mMachineStatus.equals(state)) {
            mServerInfoHandler.sendBroadCast();
            mServerHandler.sendBroadCast();
        }
        mTvStatus.setText(state);
        mTvStatus.setSelected(true);
        mTvStatus.setEnabled(!"已关机".equals(state));
        if ("故障".equals(state) || ("异常").equals(state)) {
            mTvStatus.setSelected(false);
        }
//        if (mPermissionStartServer) {
//            if ("已关机".equals(state) || ("已停止").equals(state)) {
//                mBtnStop.setVisibility(GONE);
//                mBtnRestart.setVisibility(GONE);
//                mBtnStart.setVisibility(VISIBLE);
//            } else if ("运行中".equals(state) || "故障".equals(state) || ("异常").equals(state)) {
//                mBtnStop.setVisibility(VISIBLE);
//                mBtnRestart.setVisibility(VISIBLE);
//                mBtnStart.setVisibility(GONE);
//            } else {
//                mBtnStop.setVisibility(GONE);
//                mBtnRestart.setVisibility(GONE);
//                mBtnStart.setVisibility(GONE);
//            }
//        }
        mBtnDel.setVisibility(mPermissionDelServer ? VISIBLE : GONE);
//        //开启打点动画
//        i = 0;
//        if (mAnimSubscribe != null && !mAnimSubscribe.isUnsubscribed()) {
//            mAnimSubscribe.unsubscribe();
//        }
//        if ("关机中".equals(state) || "开机中".equals(state)
//                || ("停止中").equals(state) || ("启动中").equals(state)) {
//            mAnimSubscribe = Observable.interval(0, 500, TimeUnit.MILLISECONDS)
//                    .map(new Func1<Long, Integer>() {
//                        @Override
//                        public Integer call(Long aLong) {
//                            return i++ % 3;
//                        }
//                    })
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Action1<Integer>() {
//                        @Override
//                        public void call(Integer integer) {
//                            if (integer == 0) {
//                                mTvStatus.setText(state + ".  ");
//                            } else if (integer == 1) {
//                                mTvStatus.setText(state + ".. ");
//                            } else {
//                                mTvStatus.setText(state + "...");
//                            }
//                        }
//                    });
//        }

    }

    int i = 0;

    private void queryState() {
        if (!isQuerying && !TextUtils.isEmpty(mInstanceId)) {
            mServerOperationPresenter.queryServerState(mInstanceId);
            isQuerying = true;
        }
    }

    @Override
    public void rebootSuccess() {
        showMessage("重启中");
        queryState();
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
        queryState();
    }

    @Override
    public void stopSuccess() {
        showMessage("正在关机");
        queryState();
    }

    @Override
    public void showState(String state) {
        setState(state);
        mTvStatus.setText(state);
    }

    @Override
    public void onActivityDestroy() {
        super.onActivityDestroy();
        mServerDetailPresenter.detachView();
        mServerOperationPresenter.detachView();
        mRefreshBroadCastHandler.unregisterReceiver();
        if (mAnimSubscribe != null && !mAnimSubscribe.isUnsubscribed()) {
            mAnimSubscribe.unsubscribe();
        }
    }
}
