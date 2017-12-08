package com.ten.tencloud.module.server.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.NetSpeedBean;
import com.ten.tencloud.bean.ServerHistoryBean;
import com.ten.tencloud.utils.DateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lxq on 2017/11/23.
 */

public class RvServerHistoryAdapter extends CJSBaseRecyclerViewAdapter<ServerHistoryBean, RvServerHistoryAdapter.ViewHolder> {
    public RvServerHistoryAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_server_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, int position) {
        ServerHistoryBean serverBean = datas.get(position);
        holder.tvTime.setText(DateUtils.timestampToString(serverBean.getCreated_time(), "yyyy-MM-dd HH:mm:ss"));
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
        NetSpeedBean netInfo = serverBean.getNet();
        String net = netInfo.getInput() + "/" + netInfo.getOutput();
        holder.tvNet.setText(net);
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

        @BindView(R.id.tv_time)
        TextView tvTime;
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
