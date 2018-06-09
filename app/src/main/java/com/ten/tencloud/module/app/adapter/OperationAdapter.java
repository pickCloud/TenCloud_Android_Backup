package com.ten.tencloud.module.app.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ten.tencloud.R;
import com.ten.tencloud.bean.DeploymentInfoBean;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OperationAdapter extends BaseQuickAdapter<DeploymentInfoBean, BaseViewHolder> {
    public OperationAdapter() {
        super(R.layout.adapter_operation);
    }

    @Override
    protected void convert(BaseViewHolder helper, DeploymentInfoBean item) {

        helper.setText(R.id.tv_name, item.name);
        helper.setText(R.id.tv_id, item.id);
        helper.setText(R.id.tv_status, item.podStatus + "");
        helper.setText(R.id.tv_ready, item.readyStatus);
        helper.setText(R.id.tv_restart_count, item.restartStatus + "");
        helper.setText(R.id.tv_date, item.create_time);
//        helper.setText(R.id.tv_label, item.create_time);

    }

    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_id)
        TextView mTvId;
        @BindView(R.id.tv_status)
        TextView mTvStatus;
        @BindView(R.id.tv_ready)
        TextView mTvReady;
        @BindView(R.id.tv_restart_count)
        TextView mTvRestartCount;
        @BindView(R.id.tv_date)
        TextView mTvDate;
        @BindView(R.id.tv_create_date)
        TextView mTvCreateDate;
        @BindView(R.id.tv_label)
        TextView mTvLabel;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}