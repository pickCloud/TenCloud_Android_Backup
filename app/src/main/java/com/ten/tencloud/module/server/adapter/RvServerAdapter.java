package com.ten.tencloud.module.server.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.ServerBean;
import com.ten.tencloud.utils.Utils;

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
        holder.tvStatus.setText(serverBean.getMachine_status());
        holder.tvIp.setText(serverBean.getPublic_ip());
        float cpuPercent = Float.valueOf(serverBean.getCpu_content().getPercent() == null ? "0" : serverBean.getCpu_content().getPercent());
        holder.tvCPU.setText(cpuPercent + "");
        setProgress(cpuPercent, holder.pbCPU);
        float diskPercent = Float.valueOf(serverBean.getDisk_content().getPercent() == null ? "0" : serverBean.getDisk_content().getPercent());
        holder.tvDisk.setText(diskPercent + "");
        setProgress(diskPercent, holder.pbDisk);
        float memoryPercent = Float.valueOf(serverBean.getMemory_content().getPercent() == null ? "0" : serverBean.getMemory_content().getPercent());
        holder.tvMemory.setText(memoryPercent + "");
        setProgress(memoryPercent, holder.pbMemory);
        //net
        holder.tvNet.setText(Utils.handNetSpeed(serverBean.getNet_content()));
        String provider = serverBean.getProvider();
        if (provider.contains("阿里云")) {
            holder.ivProviderIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_aliyun));
        } else if (provider.contains("亚马逊云")) {
            holder.ivProviderIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_amazon));
        } else if (provider.contains("微软云")) {
            holder.ivProviderIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_microyun));
        }
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

    public static class ViewHolder extends RecyclerView.ViewHolder {

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
