package com.ten.tencloud.module.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.AppServiceHeaderBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxh@10.com on 2018/3/26.
 */
public class RvAppServiceHeaderAdapter extends CJSBaseRecyclerViewAdapter<AppServiceHeaderBean, RvAppServiceHeaderAdapter.ViewHolder> {


    public RvAppServiceHeaderAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_app_service_header, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, int position) {
        AppServiceHeaderBean appServiceHeaderBean = datas.get(position);
        holder.mTvCount.setText(String.valueOf(appServiceHeaderBean.getCount()));
        holder.mTvName.setText(appServiceHeaderBean.getDesc());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_count)
        TextView mTvCount;
        @BindView(R.id.tv_name)
        TextView mTvName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
