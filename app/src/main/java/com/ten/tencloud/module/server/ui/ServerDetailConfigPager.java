package com.ten.tencloud.module.server.ui;

import android.content.Context;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BasePager;
import com.ten.tencloud.bean.ServerDetailBean;
import com.ten.tencloud.module.server.contract.ServerDetailContract;
import com.ten.tencloud.module.server.presenter.ServerDetailPresenter;

import butterknife.BindView;

/**
 * Created by lxq on 2017/11/29.
 */

public class ServerDetailConfigPager extends BasePager implements ServerDetailContract.View {

    @BindView(R.id.tv_os)
    TextView mTvOS;
    @BindView(R.id.tv_os_type)
    TextView mTvOSType;
    @BindView(R.id.tv_cpu)
    TextView mTvCPU;
    @BindView(R.id.tv_memory)
    TextView mTvMemory;

    private boolean isFirst = true;
    private ServerDetailPresenter mServerDetailPresenter;
    private String mId;

    public ServerDetailConfigPager(Context context) {
        super(context);
    }

    @Override
    public void init() {
        if (isFirst) {
            mId = getArgument("id");
            createView(R.layout.pager_server_detail_config);
            mServerDetailPresenter = new ServerDetailPresenter();
            mServerDetailPresenter.attachView(this);
            mServerDetailPresenter.getServerDetail(mId);
        }
    }

    @Override
    public void showServerDetail(ServerDetailBean serverDetailBean) {
        ServerDetailBean.SystemInfoBean.ConfigBean config = serverDetailBean.getSystem_info().getConfig();
        mTvCPU.setText(config.getCpu() + "");
        mTvOS.setText(config.getOs_name() + "");
        mTvOSType.setText(config.getOs_type() + "");
        mTvMemory.setText(config.getMemory() / 1024 + "GB");
        isFirst = false;
    }
}
