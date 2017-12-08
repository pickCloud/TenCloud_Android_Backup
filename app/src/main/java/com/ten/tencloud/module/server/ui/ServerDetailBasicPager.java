package com.ten.tencloud.module.server.ui;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BasePager;
import com.ten.tencloud.bean.ServerDetailBean;
import com.ten.tencloud.module.server.contract.ServerDetailContract;
import com.ten.tencloud.module.server.contract.ServerOperationContract;
import com.ten.tencloud.module.server.presenter.ServerDetailPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lxq on 2017/11/29.
 */

public class ServerDetailBasicPager extends BasePager implements ServerDetailContract.View, ServerOperationContract.View {

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

    private boolean isFirst = true;
    private ServerDetailPresenter mServerDetailPresenter;
    private String mId;

    public ServerDetailBasicPager(Context context) {
        super(context);
    }


    @OnClick({R.id.btn_restart, R.id.btn_del})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_restart:

                break;
            case R.id.btn_del:

                break;
        }
    }

    @Override
    public void init() {
        if (isFirst) {
            mId = getArgument("id");
            createView(R.layout.pager_server_detail_basic);
            mServerDetailPresenter = new ServerDetailPresenter();
            mServerDetailPresenter.attachView(this);
            mServerDetailPresenter.getServerDetail(mId);
        }
    }


    @Override
    public void showServerDetail(ServerDetailBean serverDetailBean) {
        isFirst = false;
        mTvName.setText(serverDetailBean.getBasic_info().getName());
        mTvProvider.setText(serverDetailBean.getBusiness_info().getProvider());
        mTvAddress.setText(serverDetailBean.getBasic_info().getAddress());
        mTvIP.setText(serverDetailBean.getBasic_info().getPublic_ip());
        String machine_status = serverDetailBean.getBasic_info().getMachine_status();
        if ("运行中".equals(machine_status)) {
            mTvStatus.setEnabled(true);
        } else if ("已停止".equals(machine_status)) {
            mTvStatus.setEnabled(false);
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
}
