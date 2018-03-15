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

    public final static int LEVEL_1 = 1;//警告
    public final static int LEVEL_2 = 2;
    public final static int LEVEL_3 = 3;
    public final static int LEVEL_4 = 4;
    public final static int LEVEL_5 = 5;//最低

    private int mType = TYPE_TWO;

    private int[] layouts = {R.layout.item_server_heat_type3,
            R.layout.item_server_heat_type2, R.layout.item_server_heat_type1};

    public RvServerHeatChartAdapter(Context context, int type) {
        super(context);
        mType = type;
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_server_heat_chart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, int position) {
        ServerHeatBean serverHeatBean = datas.get(position);
        View view = mLayoutInflater.inflate(layouts[mType], holder.mHeatLayout, true);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText(serverHeatBean.getName());
        if (mType == TYPE_TWO) {
            TextView tvCpu = view.findViewById(R.id.tv_cpu);
            TextView tvMemory = view.findViewById(R.id.tv_memory);
            TextView tvDisk = view.findViewById(R.id.tv_disk);
            TextView tvNet = view.findViewById(R.id.tv_net);
            tvCpu.setText(serverHeatBean.getCpu() + "%");
            tvMemory.setText(serverHeatBean.getMemory() + "%");
            tvDisk.setText(serverHeatBean.getDisk() + "%");
            tvNet.setText(serverHeatBean.getNet());
        } else if (mType == TYPE_THREE) {
            TextView tvCpu = view.findViewById(R.id.tv_cpu);
            TextView tvMemory = view.findViewById(R.id.tv_memory);
            tvCpu.setText(serverHeatBean.getCpu() + "%");
            tvMemory.setText(serverHeatBean.getMemory() + "%");
        }
        tvTitle.setText(serverHeatBean.getName());
        setLevel(holder, datas.get(position).getLevel());
    }

    /**
     * 设置警告等级
     *
     * @param level
     */
    private void setLevel(ViewHolder holder, int level) {
        if (level == LEVEL_1) {
            holder.mHeatLayout.setBackgroundResource(R.drawable.fade_server_heat_red);
        } else if (level == LEVEL_2) {
            holder.mHeatLayout.setBackgroundResource(R.drawable.fade_server_heat_red);
            holder.mHeatLayout.setAlpha(15f / 100);
        } else if (level == LEVEL_3) {
            holder.mHeatLayout.setBackgroundResource(R.drawable.fade_server_heat_red);
            holder.mHeatLayout.setAlpha(30f / 100);
        } else if (level == LEVEL_4) {
            holder.mHeatLayout.setBackgroundResource(R.drawable.fade_server_heat_green);
            holder.mHeatLayout.setAlpha(30f / 100);
        } else if (level == LEVEL_5) {
            holder.mHeatLayout.setBackgroundResource(R.drawable.fade_server_heat_gray);
            holder.mHeatLayout.setAlpha(10f / 100);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        HeatLayout mHeatLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            mHeatLayout = (HeatLayout) itemView;
        }
    }
}
