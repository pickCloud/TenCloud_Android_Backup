package com.ten.tencloud.module.server.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.ServerLogBean;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lxq on 2017/11/30.
 */

public class RvServerLogAdapter extends CJSBaseRecyclerViewAdapter<ServerLogBean.LogInfo, RvServerLogAdapter.ViewHolder> implements StickyRecyclerHeadersAdapter<RvServerLogAdapter.HeaderHolder> {

    public RvServerLogAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_server_log, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, int position) {
        ServerLogBean.LogInfo logInfo = datas.get(position);
        holder.tvUser.setText(logInfo.getUser());
        holder.tvTime.setText(logInfo.getCreated_time());
        int operation = logInfo.getOperation();
        switch (operation) {
            case 0:
                holder.tvOperation.setText("开机");
                break;
            case 1:
                holder.tvOperation.setText("关机");
                break;
            case 2:
                holder.tvOperation.setText("重启");
                break;
        }
        int status = logInfo.getOperation_status();
        holder.tvStatus.setText(status == 0 ? "成功" : "失败");
    }

    Map<Integer, String> headerMap = new HashMap<>();

    @Override
    public long getHeaderId(int position) {
        String headerText = getHeaderText(position);
        if (headerMap.containsValue(headerText)) {
            for (int key : headerMap.keySet()) {
                if (headerText.equals(headerMap.get(key))) {
                    return key;
                }
            }
        }
        headerMap.put(position, headerText);
        return position;
    }

    private String getHeaderText(int position) {
        String createdTime = datas.get(position).getCreated_time();
        String substring = createdTime.substring(0, 7);
        return substring.replace("-", "年") + "月";
    }

    @Override
    public HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.header_server_log, parent, false);
        return new HeaderHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderHolder holder, int position) {
        holder.tvHeader.setText(getHeaderText(position));
    }

    public static class HeaderHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_header)
        TextView tvHeader;

        public HeaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_user)
        TextView tvUser;
        @BindView(R.id.tv_operation)
        TextView tvOperation;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_status)
        TextView tvStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
