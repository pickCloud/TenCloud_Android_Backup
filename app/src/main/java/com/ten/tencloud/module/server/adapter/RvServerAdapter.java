package com.ten.tencloud.module.server.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.ServerBean;
import com.ten.tencloud.utils.ToastUtils;
import com.ten.tencloud.utils.Utils;
import com.ten.tencloud.widget.SwipeMenuViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lxq on 2017/11/23.
 */

public class RvServerAdapter extends CJSBaseRecyclerViewAdapter<ServerBean, RvServerAdapter.ViewHolder> {
    public RvServerAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_server, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, int position) {
        ServerBean serverBean = datas.get(position);
        holder.tvName.setText(serverBean.getName());
        String status = serverBean.getMachine_status();
        holder.tvStatus.setText(serverBean.getMachine_status());
        holder.tvStatus.setEnabled(!"已关机".equals(status));
        if ("故障".equals(status) || ("异常").equals(status)) {
            holder.tvStatus.setEnabled(false);
        }
        holder.tvIp.setText(serverBean.getPublic_ip());
        float cpuPercent = Float.valueOf(serverBean.getCpu().getPercent() == null ? "0" : serverBean.getCpu().getPercent());
        holder.tvCPU.setText(cpuPercent + "");
        setProgress(cpuPercent, holder.pbCPU);
        float diskPercent = Float.valueOf(serverBean.getDisk().getPercent() == null ? "0" : serverBean.getDisk().getPercent());
        holder.tvDisk.setText(diskPercent + "");
        setProgress(diskPercent, holder.pbDisk);
        float memoryPercent = Float.valueOf(serverBean.getMemory().getPercent() == null ? "0" : serverBean.getMemory().getPercent());
        holder.tvMemory.setText(memoryPercent + "");
        setProgress(memoryPercent, holder.pbMemory);
        //net
        holder.tvNet.setText(Utils.handNetSpeed(serverBean.getNet()));
        String provider = serverBean.getProvider();
        if ("阿里云".equals(provider)) {
            holder.ivProviderIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_aliyun));
        } else if ("亚马逊云".equals(provider)) {
            holder.ivProviderIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_amazon));
        } else if ("微软云".equals(provider)) {
            holder.ivProviderIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_microyun));
        }
        holder.swipeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showLongToast("点击删除");
            }
        });
    }

    private boolean isAlarm(float value) {
        return value >= 80;
    }

    private void setProgress(float value, ProgressBar progressBar) {
        if (isAlarm(value)) {
            progressBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.layer_progress_bar_alarm));
        } else {
            progressBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.layer_progress_bar_normal));
        }
        progressBar.setProgress(Math.round(value));
    }

    public static class ViewHolder extends SwipeMenuViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.iv_provider_icon)
        ImageView ivProviderIcon;
        @BindView(R.id.tv_ip)
        TextView tvIp;
        @BindView(R.id.tv_cpu)
        TextView tvCPU;
        @BindView(R.id.pb_cpu)
        ProgressBar pbCPU;
        @BindView(R.id.tv_memory)
        TextView tvMemory;
        @BindView(R.id.pb_memory)
        ProgressBar pbMemory;
        @BindView(R.id.tv_disk)
        TextView tvDisk;
        @BindView(R.id.pb_disk)
        ProgressBar pbDisk;
        @BindView(R.id.tv_net)
        TextView tvNet;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
