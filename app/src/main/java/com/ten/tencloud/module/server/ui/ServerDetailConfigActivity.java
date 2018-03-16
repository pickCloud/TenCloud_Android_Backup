package com.ten.tencloud.module.server.ui;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.ServerDetailBean;
import com.ten.tencloud.module.server.contract.ServerDetailContract;
import com.ten.tencloud.module.server.presenter.ServerDetailPresenter;
import com.ten.tencloud.utils.UiUtils;

import butterknife.BindView;

public class ServerDetailConfigActivity extends BaseActivity implements ServerDetailContract.View {

    @BindView(R.id.tv_cpu)
    TextView mTvCpu;
    @BindView(R.id.tv_memory)
    TextView mTvMemory;
    @BindView(R.id.tv_os_name)
    TextView mTvOsName;
    @BindView(R.id.tv_image_id)
    TextView mTvImageId;
    @BindView(R.id.tv_disk_type)
    TextView mTvDiskType;
    @BindView(R.id.tv_disk)
    TextView mTvDisk;
    @BindView(R.id.tv_net_type)
    TextView mTvNetType;
    @BindView(R.id.tv_pay_type)
    TextView mTvPayType;
    @BindView(R.id.tv_net_in_max)
    TextView mTvNetInMax;
    @BindView(R.id.tv_net_out_max)
    TextView mTvNetOutMax;
    @BindView(R.id.ll_group_ids)
    LinearLayout mLlGroupIds;

    private ServerDetailPresenter mServerDetailPresenter;
    private String mServerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_server_detail_config);
        initTitleBar(true, "配置信息");
        mServerDetailPresenter = new ServerDetailPresenter();
        mServerDetailPresenter.attachView(this);
        mServerId = getIntent().getStringExtra("serverId");
        mServerDetailPresenter.getServerDetail(mServerId);
    }

    @Override
    public void showServerDetail(ServerDetailBean serverDetailBean) {
        if (serverDetailBean != null) {
            mTvCpu.setText(serverDetailBean.getSystem_info().getConfig().getCpu() + "");
            mTvMemory.setText(serverDetailBean.getSystem_info().getConfig().getMemory() / 1024 + "GB");
            mTvOsName.setText(serverDetailBean.getSystem_info().getConfig().getOs_name());
            mTvImageId.setText(serverDetailBean.getSystem_info().getConfig().getImage_name());
            mTvDiskType.setText(serverDetailBean.getSystem_info().getConfig().getSystem_disk_type());
            mTvDisk.setText(serverDetailBean.getSystem_info().getConfig().getSystem_disk_size());
            mTvNetType.setText(serverDetailBean.getSystem_info().getConfig().getInstance_network_type());
            mTvPayType.setText(serverDetailBean.getBusiness_info().getContract().getCharge_type());
            mTvNetInMax.setText(serverDetailBean.getSystem_info().getConfig().getInternet_max_bandwidth_in());
            mTvNetOutMax.setText(serverDetailBean.getSystem_info().getConfig().getInternet_max_bandwidth_out());
            //安全组ID
            String groupIds = serverDetailBean.getSystem_info().getConfig().getSecurity_group_ids();
            String[] ids = groupIds.split(",");
            for (String id : ids) {
                TextView textView = new TextView(mContext);
                textView.setTextSize(12);
                textView.setTextColor(getResources().getColor(R.color.text_color_899ab6));
                int padding = UiUtils.dip2px(mContext, 12);
                textView.setPadding(0, 0, 0, padding);
                textView.setText(id);
                mLlGroupIds.addView(textView);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mServerDetailPresenter.detachView();
    }
}
