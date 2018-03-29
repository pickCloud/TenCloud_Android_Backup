package com.ten.tencloud.module.server.adapter;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.ServerHeatBean;
import com.ten.tencloud.bean.ServerThresholdBean;
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.utils.UiUtils;
import com.ten.tencloud.widget.HeatLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxq on 2018/3/14.
 */

public class RvServerHeatChartAdapter extends CJSBaseRecyclerViewAdapter<ServerHeatBean, RvServerHeatChartAdapter.ViewHolder> {

    public final static int TYPE_TWO = 0;
    public final static int TYPE_THREE = 1;
    public final static int TYPE_FOUR = 2;

    private int mType = TYPE_TWO;

    private ServerThresholdBean mServerThreshold;

    public RvServerHeatChartAdapter(Context context) {
        super(context);
        mServerThreshold = AppBaseCache.getInstance().getServerThreshold();
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
        View view = mLayoutInflater.inflate(R.layout.item_server_heat_type, holder.mHeatLayout.mContent, true);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        LinearLayout llValue = view.findViewById(R.id.ll_value);
        tvTitle.setText(serverHeatBean.getName());
        List<String> sortWithThreshold = sortWithThreshold(serverHeatBean);
        if (mType == TYPE_TWO) {
            llValue.addView(createTextView("CPU使用率  " + serverHeatBean.getCpuUsageRate() + "%", 2, 10, R.color.text_color_66ffffff));
            llValue.addView(createTextView("内存使用率  " + serverHeatBean.getMemUsageRate() + "%", 2, 10, R.color.text_color_66ffffff));
            llValue.addView(createTextView("磁盘利用率  " + serverHeatBean.getDiskUtilize() + "%", 2, 10, R.color.text_color_66ffffff));
            llValue.addView(createTextView("磁盘占用率  " + serverHeatBean.getDiskUsageRate() + "%", 2, 10, R.color.text_color_66ffffff));
            llValue.addView(createTextView("网络I  " + serverHeatBean.getNetDownload() + "    " + "网络O  " + serverHeatBean.getNetUpload(), 2, 10, R.color.text_color_66ffffff));
        } else if (mType == TYPE_THREE) {
            for (int i = 0; i < 2; i++) {
                llValue.addView(createTextView(sortWithThreshold.get(i), 3, 10, R.color.text_color_66ffffff));
            }
        } else if (mType == TYPE_FOUR) {
            String[] strs = sortWithThreshold.get(0).split("  ");
            llValue.addView(createTextView(strs[0], 1, 10, R.color.text_color_66ffffff));
            llValue.addView(createTextView(strs[1], 1, 10, R.color.text_color_66ffffff));
        }
        holder.mHeatLayout.setHeatLevel(datas.get(position).getColorType());
    }

    private TextView createTextView(String text, int padding, float textSize, @ColorRes int color) {
        TextView textView = new TextView(mContext);
        textView.setText(text);
        textView.setTextSize(textSize);
        textView.setTextColor(mContext.getResources().getColor(color));
        int px = UiUtils.dip2px(mContext, padding);
        textView.setPadding(px, px, px, px);
        return textView;
    }

    /**
     * 排序
     * @param serverHeatBean
     * @return
     */
    private List<String> sortWithThreshold(ServerHeatBean serverHeatBean) {
        List<String> result = new ArrayList<>();
        List<String> tempList = new ArrayList<>();
        String[] net = serverHeatBean.getNetUsageRate().split("/");
        float netInputThreshold = Float.parseFloat(net[0]);
        float netOutputThreshold = Float.parseFloat(net[1]);
        String cpu = "CPU使用率  " + serverHeatBean.getCpuUsageRate() + "%";
        String memory = "内存使用率  " + serverHeatBean.getMemUsageRate() + "%";
        String diskUsage = "磁盘利用率  " + serverHeatBean.getDiskUtilize() + "%";
        String disk = "磁盘占用率  " + serverHeatBean.getDiskUsageRate() + "%";
        String netInput = "网络I  " + serverHeatBean.getNetDownload();
        String netOutput = "网络O  " + serverHeatBean.getNetUpload();
        tempList.add(cpu);
        tempList.add(memory);
        tempList.add(diskUsage);
        tempList.add(disk);
        tempList.add(netInput);
        tempList.add(netOutput);

        if (serverHeatBean.getCpuUsageRate() > mServerThreshold.getCpu_threshold()) {
            result.add(cpu);
            tempList.remove(cpu);
        }
        if (serverHeatBean.getMemUsageRate() > mServerThreshold.getMemory_threshold()) {
            result.add(memory);
            tempList.remove(memory);
        }
        if (serverHeatBean.getDiskUtilize() > mServerThreshold.getBlock_threshold()) {
            result.add(diskUsage);
            tempList.remove(diskUsage);
        }
        if (serverHeatBean.getDiskUsageRate() > mServerThreshold.getDisk_threshold()) {
            result.add(disk);
            tempList.remove(disk);
        }
        if (netInputThreshold > mServerThreshold.getNet_threshold()) {
            result.add(netInput);
            tempList.remove(netInput);
        }
        if (netOutputThreshold > mServerThreshold.getNet_threshold()) {
            result.add(netOutput);
            tempList.remove(netOutput);
        }

        result.addAll(tempList);

        return result;
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
