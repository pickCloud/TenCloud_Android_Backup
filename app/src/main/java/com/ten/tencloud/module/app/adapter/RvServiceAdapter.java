package com.ten.tencloud.module.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.ServiceBean;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.utils.UiUtils;

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
        holder.mTvType.setText(datas.get(position).getType());
        holder.mTvIp.setText(datas.get(position).getIp());
        holder.mTvOutIp.setText(datas.get(position).getOutIp());
        holder.mTvBurden.setText(datas.get(position).getBurden());
        holder.mTvPort.setText(datas.get(position).getPort());
        holder.mTvCreateDate.setText(datas.get(position).getCreateDate());

        switch (datas.get(position).getStatus()) {
            case Constants.APP_STATUS_ERROR:
                holder.mTvStatus.setBackgroundResource(R.drawable.shape_app_status_error_round);
                holder.mTvStatus.setCompoundDrawablesWithIntrinsicBounds(UiUtils.getDrawable( R.mipmap.icon_detail_pink), null, null, null);
                holder.mTvStatus.setTextColor(UiUtils.getColor(R.color.text_color_ef9a9a));
                holder.mTvStatus.setText("失败");
                break;
            case Constants.APP_STATUS_INIT:
                holder.mTvStatus.setBackgroundResource(R.drawable.shape_app_status_init_round);
                holder.mTvStatus.setCompoundDrawablesWithIntrinsicBounds(UiUtils.getDrawable( R.mipmap.icon_detail_green), null, null, null);
                holder.mTvStatus.setTextColor(UiUtils.getColor(R.color.text_color_09bb07));
                holder.mTvStatus.setText("成功");
                break;
            case Constants.APP_STATUS_NORMAL:
                holder.mTvStatus.setBackgroundResource(R.drawable.shape_app_status_normal_round);
                holder.mTvStatus.setCompoundDrawablesWithIntrinsicBounds(UiUtils.getDrawable(R.mipmap.icon_detail), null, null, null);
                holder.mTvStatus.setTextColor(UiUtils.getColor(R.color.text_color_48bbc0));
                holder.mTvStatus.setText("运行中");
                break;
        }
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
