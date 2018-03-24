package com.ten.tencloud.module.server.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.ServerHeatBean;
import com.ten.tencloud.widget.HeatLayout;

/**
 * Created by lxq on 2018/3/14.
 */

public class RvServerHeatChartAdapter extends CJSBaseRecyclerViewAdapter<ServerHeatBean, RvServerHeatChartAdapter.ViewHolder> {

    public final static int TYPE_TWO = 0;
    public final static int TYPE_THREE = 1;
    public final static int TYPE_FOUR = 2;

    private int mType = TYPE_TWO;

    private int[] layouts = {R.layout.item_server_heat_type3,
            R.layout.item_server_heat_type2, R.layout.item_server_heat_type1};

    public RvServerHeatChartAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_server_heat_chart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, int position) {
        ServerHeatBean serverHeatBean = datas.get(position);
        holder.mHeatLayout.mContent.removeAllViews();
        View view = mLayoutInflater.inflate(layouts[mType], holder.mHeatLayout.mContent, true);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText(serverHeatBean.getName());
        if (mType == TYPE_TWO) {
            TextView tvCpu = view.findViewById(R.id.tv_cpu);
            TextView tvMemory = view.findViewById(R.id.tv_memory);
            TextView tvDisk = view.findViewById(R.id.tv_disk);
            TextView tvNet = view.findViewById(R.id.tv_net);
            TextView tvDiskIO = view.findViewById(R.id.tv_disk_io);
            tvCpu.setText(serverHeatBean.getCpuUsageRate() + "%");
            tvMemory.setText(serverHeatBean.getMemUsageRate() + "%");
            tvDisk.setText(serverHeatBean.getDiskUsageRate() + "%");
            tvNet.setText(serverHeatBean.getNetworkUsage());
            tvDiskIO.setText(serverHeatBean.getDiskIO());
        } else if (mType == TYPE_THREE) {
            TextView tvName1 = view.findViewById(R.id.tv_name_1);
            TextView tvValue1 = view.findViewById(R.id.tv_value_1);
            TextView tvName2 = view.findViewById(R.id.tv_name_2);
            TextView tvValue2 = view.findViewById(R.id.tv_value_2);
            if (serverHeatBean.getCpuUsageRate() > 80) {
                tvName1.setText("CPU使用率");
                tvValue1.setText(serverHeatBean.getCpuUsageRate() + "%");
                if (serverHeatBean.getDiskUsageRate() > 80) {
                    tvName2.setText("磁盘占用率");
                    tvValue2.setText(serverHeatBean.getDiskUsageRate() + "%");
                } else {
                    tvName2.setText("内存使用率");
                    tvValue2.setText(serverHeatBean.getMemUsageRate() + "%");
                }
            } else if (serverHeatBean.getMemUsageRate() > 80) {
                tvName1.setText("内存使用率");
                tvValue1.setText(serverHeatBean.getMemUsageRate() + "%");
                if (serverHeatBean.getDiskUsageRate() > 80) {
                    tvName2.setText("磁盘占用率");
                    tvValue2.setText(serverHeatBean.getDiskUsageRate() + "%");
                } else {
                    tvName2.setText("CPU使用率");
                    tvValue2.setText(serverHeatBean.getCpuUsageRate() + "%");
                }
            } else if (serverHeatBean.getDiskUsageRate() > 80) {
                tvName1.setText("磁盘占用率");
                tvValue1.setText(serverHeatBean.getDiskUsageRate() + "%");
                tvName2.setText("CPU使用率");
                tvValue2.setText(serverHeatBean.getCpuUsageRate() + "%");
            } else {
                tvName1.setText("CPU使用率");
                tvValue1.setText(serverHeatBean.getCpuUsageRate() + "%");
                tvName2.setText("内存使用率");
                tvValue2.setText(serverHeatBean.getMemUsageRate() + "%");
            }
        } else if (mType == TYPE_FOUR) {
            TextView tvName = view.findViewById(R.id.tv_name);
            TextView tvValue = view.findViewById(R.id.tv_value);
            if (serverHeatBean.getCpuUsageRate() > 80) {
                tvName.setText("CPU使用率");
                tvValue.setText(serverHeatBean.getCpuUsageRate() + "%");
            } else if (serverHeatBean.getMemUsageRate() > 80) {
                tvName.setText("内存使用率");
                tvValue.setText(serverHeatBean.getMemUsageRate() + "%");
            } else if (serverHeatBean.getDiskUsageRate() > 80) {
                tvName.setText("磁盘占用率");
                tvValue.setText(serverHeatBean.getDiskUsageRate() + "%");
            } else {
                tvName.setText("CPU使用率");
                tvValue.setText(serverHeatBean.getCpuUsageRate() + "%");
            }
        }
        holder.mHeatLayout.setHeatLevel(datas.get(position).getColorType());
    }

    public void setType(int type) {
        mType = type;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        HeatLayout mHeatLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            mHeatLayout = (HeatLayout) itemView;
        }
    }
}
