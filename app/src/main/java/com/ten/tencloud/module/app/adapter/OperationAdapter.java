package com.ten.tencloud.module.app.adapter;

import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ten.tencloud.R;
import com.ten.tencloud.bean.DeploymentInfoBean;
import com.ten.tencloud.bean.LabelBean;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OperationAdapter extends BaseQuickAdapter<DeploymentInfoBean, BaseViewHolder> {
    public OperationAdapter() {
        super(R.layout.adapter_operation);
    }

    @Override
    protected void convert(BaseViewHolder helper, DeploymentInfoBean item) {

        helper.setText(R.id.tv_name, item.name);
        helper.setText(R.id.tv_id, item.deployment_id + "");
        helper.setText(R.id.tv_status, item.podStatus);
        helper.setText(R.id.tv_ready, item.readyStatus);
        helper.setText(R.id.tv_restart_count, item.restartStatus + "");
        helper.setText(R.id.tv_running_time, "");
        helper.setText(R.id.tv_create_date, item.create_time);

        helper.setText(R.id.tv_label, item.labels.get("app") + "=" + item.labels.get("pod-template-hash"));

    }

}
