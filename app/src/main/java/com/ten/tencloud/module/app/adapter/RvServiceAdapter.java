package com.ten.tencloud.module.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.ServiceBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxh@10.com on 2018/3/27.
 */
public class RvServiceAdapter extends CJSBaseRecyclerViewAdapter<ServiceBean, RvServiceAdapter.ViewHolder> {


    public RvServiceAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_app_servcie_service, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, int position) {
        holder.mTvName.setText(datas.get(position).getName());
        holder.mTvStatus.setVisibility(View.GONE);
        holder.mTvType.setText(datas.get(position).getType());
        holder.mTvIp.setText(datas.get(position).getIp());
        holder.mTvOutIp.setText(datas.get(position).getOutIp());
        holder.mTvBurden.setText(datas.get(position).getBurden());
        holder.mTvPort.setText(datas.get(position).getPort());
        holder.mTvCreateDate.setText(datas.get(position).getCreateDate());
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_label)
        TextView mTvLabel;
        @BindView(R.id.tv_status)
        TextView mTvStatus;
        @BindView(R.id.tv_check_hint)
        TextView mTvCheckHint;
        @BindView(R.id.tv_type)
        TextView mTvType;
        @BindView(R.id.tv_ip)
        TextView mTvIp;
        @BindView(R.id.tv_out_ip)
        TextView mTvOutIp;
        @BindView(R.id.tv_burden)
        TextView mTvBurden;
        @BindView(R.id.tv_port)
        TextView mTvPort;
        @BindView(R.id.tv_create_date)
        TextView mTvCreateDate;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
