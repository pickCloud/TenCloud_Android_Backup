package com.ten.tencloud.module.server.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.ServerDetailBean;
import com.ten.tencloud.module.server.contract.ServerDetailContract;
import com.ten.tencloud.module.server.presenter.ServerDetailPresenter;
import com.ten.tencloud.utils.UiUtils;
import com.ten.tencloud.utils.Utils;

import java.util.List;

import butterknife.BindView;

public class ServerDetailConfigActivity extends BaseActivity implements ServerDetailContract.View {

    @BindView(R.id.tv_cpu)
    TextView mTvCpu;
    @BindView(R.id.tv_memory)
    TextView mTvMemory;
    @BindView(R.id.tv_os_name)
    TextView mTvOsName;
    @BindView(R.id.ll_disk_info)
    LinearLayout mLlDiskInfo;
    @BindView(R.id.ll_image_info)
    LinearLayout mLlImageInfo;
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
            ServerDetailBean.SystemInfoBean.ConfigBean config = serverDetailBean.getSystem_info().getConfig();
            mTvCpu.setText(Utils.strIsEmptyForDefault(config.getCpu() + "", "无"));
            mTvMemory.setText(Utils.strIsEmptyForDefault(config.getMemory() / 1024 + "GB", "无"));
            mTvOsName.setText(Utils.strIsEmptyForDefault(config.getOs_name(), "无"));
            mTvNetType.setText(Utils.strIsEmptyForDefault(config.getInstance_network_type(), "无"));
            mTvPayType.setText(Utils.strIsEmptyForDefault(serverDetailBean.getBusiness_info().getContract().getInstance_internet_charge_type(), "无"));
            mTvNetInMax.setText(Utils.strIsEmptyForDefault(config.getInternet_max_bandwidth_in(), "无"));
            mTvNetOutMax.setText(Utils.strIsEmptyForDefault(config.getInternet_max_bandwidth_out(), "无"));
            addDiskInfoUI(serverDetailBean.getSystem_info().getConfig().getDisk_info());
            addImageInfoUI(serverDetailBean.getSystem_info().getConfig().getImage_info());
            addGroupIdUI(serverDetailBean.getSystem_info().getConfig().getSecurity_group_ids());
        }
    }

    private void addDiskInfoUI(List<ServerDetailBean.BusinessInfoBean.DiskInfo> disk_info) {
        mLlDiskInfo.removeAllViews();
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < disk_info.size(); i++) {
            View view = inflater.inflate(R.layout.item_server_monitor_disk_info, null);
            TextView tvName = view.findViewById(R.id.tv_disk_name);
            TextView tvName_ = view.findViewById(R.id.tv_disk_name_);
            TextView tvType = view.findViewById(R.id.tv_disk_type);
            TextView tvSize = view.findViewById(R.id.tv_disk_size);
            if (disk_info.size() == 1) {
                tvName.setText("存储");
                tvName_.setText("存储");
            } else {
                tvName.setText("存储" + (i + 1));
                tvName_.setText("存储" + (i + 1));
            }
            tvType.setText(disk_info.get(i).getSystem_disk_type());
            tvSize.setText(disk_info.get(i).getSystem_disk_size() + "");
            mLlDiskInfo.addView(view);
            return;
        }
        TextView textView = new TextView(mContext);
        textView.setTextSize(12);
        textView.setTextColor(getResources().getColor(R.color.text_color_899ab6));
        int padding = UiUtils.dip2px(mContext, 16);
        textView.setPadding(0, padding, 0, 0);
        textView.setText("无");
        mLlDiskInfo.addView(textView);
    }

    private void addImageInfoUI(List<ServerDetailBean.BusinessInfoBean.ImageInfo> imageInfos) {
        mLlImageInfo.removeAllViews();
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < imageInfos.size(); i++) {
            View view = inflater.inflate(R.layout.item_server_monitor_image_info, null);
            TextView tvId = view.findViewById(R.id.tv_image_id);
            TextView tvId_ = view.findViewById(R.id.tv_image_id_);
            TextView tvName = view.findViewById(R.id.tv_image_name);
            TextView tvVersion = view.findViewById(R.id.tv_image_version);
            if (imageInfos.size() == 1) {
                tvId.setText("镜像");
                tvId_.setText("镜像");
            } else {
                tvId.setText("镜像" + (i + 1));
                tvId_.setText("镜像" + (i + 1));
            }
            tvName.setText(imageInfos.get(i).getImage_name());
            tvVersion.setText(Utils.strIsEmptyForDefault(imageInfos.get(i).getImage_version() + "", "无"));
            mLlImageInfo.addView(view);
            return;
        }
        TextView textView = new TextView(mContext);
        textView.setTextSize(12);
        textView.setTextColor(getResources().getColor(R.color.text_color_899ab6));
        int padding = UiUtils.dip2px(mContext, 16);
        textView.setPadding(0, padding, 0, 0);
        textView.setText("无");
        mLlImageInfo.addView(textView);
    }

    private void addGroupIdUI(String groupIds) {
        mLlGroupIds.removeAllViews();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mServerDetailPresenter.detachView();
    }
}
