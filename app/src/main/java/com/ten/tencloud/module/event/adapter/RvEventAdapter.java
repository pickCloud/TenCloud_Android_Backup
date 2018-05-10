package com.ten.tencloud.module.event.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.EventBean;

import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lxq on 2018/5/10.
 */

public class RvEventAdapter extends CJSBaseRecyclerViewAdapter<EventBean, RvEventAdapter.ViewHolder> {

    public RvEventAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, int position) {
        EventBean eventBean = datas.get(position);
        holder.tvName.setText(eventBean.getName());
        holder.tvSource.setText(eventBean.getSource());
        switch (eventBean.getLevel()) {
            case 0:
                holder.tvLevel.setText("常规");
                holder.tvLevel.setTextColor(holder.textColor899ab6);
                break;
            case 1:
                holder.tvLevel.setText("提醒");
                holder.tvLevel.setTextColor(holder.textColor48bbc0);
                break;
            case 2:
                holder.tvLevel.setText("紧急");
                holder.tvLevel.setTextColor(holder.textColoref9a9a);
                break;
        }
        holder.llTime.removeAllViews();
        holder.llEffect.removeAllViews();
        holder.llDes.removeAllViews();
        createChildView(holder.llTime, eventBean.getEventTime());
        createChildView(holder.llEffect, eventBean.getEffectObj());
        createChildView(holder.llDes, eventBean.getDes());
    }

    private void createChildView(ViewGroup parent, LinkedHashMap<String, String> data) {
        if (data == null) {
            return;
        }
        for (Map.Entry<String, String> entity : data.entrySet()) {
            createKeyValueView(parent, entity.getKey(), entity.getValue());
        }
    }

    private void createKeyValueView(ViewGroup parent, String key, String value) {
        View view = mLayoutInflater.inflate(R.layout.layout_key_value, parent, false);
        TextView tvKey = view.findViewById(R.id.tv_key);
        TextView tvValue = view.findViewById(R.id.tv_value);
        if (TextUtils.isEmpty(key)) {
            tvKey.setVisibility(View.GONE);
        }
        tvKey.setText(key);
        tvValue.setText(value);
        parent.addView(view);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindColor(R.color.text_color_899ab6)
        int textColor899ab6;
        @BindColor(R.color.text_color_48bbc0)
        int textColor48bbc0;
        @BindColor(R.color.text_color_ef9a9a)
        int textColoref9a9a;

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_level)
        TextView tvLevel;
        @BindView(R.id.tv_source)
        TextView tvSource;
        @BindView(R.id.ll_time)
        LinearLayout llTime;
        @BindView(R.id.ll_effect)
        LinearLayout llEffect;
        @BindView(R.id.ll_des)
        LinearLayout llDes;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
