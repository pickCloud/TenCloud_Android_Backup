package com.ten.tencloud.module.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.TaskBean;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.utils.UiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxh@10.com on 2018/3/27.
 */
public class RvAppDetailTaskAdapter extends CJSBaseRecyclerViewAdapter<TaskBean, RvAppDetailTaskAdapter.ViewHolder> {


    public RvAppDetailTaskAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_app_servcie_app_detail_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, int position) {
        holder.mTvName.setText(datas.get(position).getName());
        holder.mTvProgress.setText(String.valueOf(datas.get(position).getProgress()));
        holder.mTvStartTime.setText(datas.get(position).getCreateDate());
        holder.mTvEndTime.setText(datas.get(position).getEndDate());
        switch (datas.get(position).getStatus()) {
            case Constants.APP_STATUS_INIT:
                holder.mTvStatus.setBackgroundResource(R.drawable.shape_app_status_init);
                holder.mTvStatus.setTextColor(UiUtils.getColor(R.color.text_color_09bb07));
                holder.mTvStatus.setText("成功");
                break;
            case Constants.APP_STATUS_NORMAL:
                holder.mTvStatus.setBackgroundResource(R.drawable.shape_app_status_normal);
                holder.mTvStatus.setTextColor(UiUtils.getColor(R.color.text_color_48bbc0));
                holder.mTvStatus.setText("进行中");
                break;
            case Constants.APP_STATUS_ERROR:
                holder.mTvStatus.setBackgroundResource(R.drawable.shape_app_status_error);
                holder.mTvStatus.setTextColor(UiUtils.getColor(R.color.text_color_ef9a9a));
                holder.mTvStatus.setText("失败");
                break;
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_status)
        TextView mTvStatus;
        @BindView(R.id.tv_progress)
        TextView mTvProgress;
        @BindView(R.id.tv_start_time)
        TextView mTvStartTime;
        @BindView(R.id.tv_end_time)
        TextView mTvEndTime;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
