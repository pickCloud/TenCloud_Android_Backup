package com.ten.tencloud.module.server.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lxq on 2017/11/30.
 */

public class RvServerDetailDockerAdapter extends CJSBaseRecyclerViewAdapter<List<String>, RvServerDetailDockerAdapter.ViewHolder> {
    public RvServerDetailDockerAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_server_docker, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, int position) {
        List<String> list = datas.get(position);
        holder.tvDockerName.setText(list.get(1));
        holder.tvId.setText(list.get(0));
        holder.tvStatus.setText(list.get(2));
        holder.tvUpdateDate.setText(list.get(3));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_docker_name)
        TextView tvDockerName;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_id)
        TextView tvId;
        @BindView(R.id.tv_update_date)
        TextView tvUpdateDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
