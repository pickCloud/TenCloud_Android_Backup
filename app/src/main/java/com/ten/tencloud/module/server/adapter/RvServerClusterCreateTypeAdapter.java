package com.ten.tencloud.module.server.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lxq on 2018/1/2.
 */

public class RvServerClusterCreateTypeAdapter extends CJSBaseRecyclerViewAdapter<String, RvServerClusterCreateTypeAdapter.ViewHolder> {

    private int selectPos = -1;

    public RvServerClusterCreateTypeAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_server_cluster_create_type, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(final ViewHolder holder, final int position) {
        final String name = datas.get(position);
        if (position + 1 == datas.size()) {
            holder.line.setVisibility(View.INVISIBLE);
        } else {
            holder.line.setVisibility(View.VISIBLE);
        }
        holder.tvName.setText(name);
        holder.ivSelect.setSelected(selectPos == position);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectPos(position);
            }
        });

    }

    public void setSelectPos(int pos) {
        selectPos = pos;
        notifyDataSetChanged();
    }

    public String getSelectObj() {
        if (selectPos == -1) {
            return "";
        }
        return datas.get(selectPos);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_select)
        ImageView ivSelect;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.layout)
        View layout;
        @BindView(R.id.line)
        View line;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
