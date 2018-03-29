package com.ten.tencloud.module.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.ImageBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxh@10.com on 2018/3/27.
 */
public class RvAppDetailImageAdapter1 extends CJSBaseRecyclerViewAdapter<ImageBean, RvAppDetailImageAdapter1.ViewHolder> {


    public RvAppDetailImageAdapter1(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_app_service_app_detail_image1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, int position) {
        holder.mTvName.setText(datas.get(position).getName());
        holder.mTvImageVersion.setText(datas.get(position).getVersion());
        holder.mTvUpdateDate.setText(datas.get(position).getUpdateDate());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_image_version)
        TextView mTvImageVersion;
        @BindView(R.id.tv_update_date)
        TextView mTvUpdateDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
