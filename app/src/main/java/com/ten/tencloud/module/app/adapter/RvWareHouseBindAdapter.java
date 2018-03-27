package com.ten.tencloud.module.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.WareHouseBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxh@10.com on 2018/3/27.
 */
public class RvWareHouseBindAdapter extends CJSBaseRecyclerViewAdapter<WareHouseBean, RvWareHouseBindAdapter.ViewHolder> {

    private int selectPos = -1;

    public RvWareHouseBindAdapter(Context context) {
        super(context);
    }

    public void setSelectPos(int pos) {
        selectPos = pos;
        notifyDataSetChanged();
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_app_service_warehouse, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, int position) {
        holder.mTvWarehouseName.setText(TextUtils.isEmpty(datas.get(position).getName()) ? "" : datas.get(position).getName());
        holder.mTvWarehouseUrl.setText(TextUtils.isEmpty(datas.get(position).getUrl()) ? "" : datas.get(position).getUrl());
        holder.mIvSelect.setVisibility(position == selectPos ? View.VISIBLE : View.INVISIBLE);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_warehouse_name)
        TextView mTvWarehouseName;
        @BindView(R.id.tv_warehouse_url)
        TextView mTvWarehouseUrl;
        @BindView(R.id.iv_select)
        ImageView mIvSelect;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
