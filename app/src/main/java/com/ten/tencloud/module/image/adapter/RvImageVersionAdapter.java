package com.ten.tencloud.module.image.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.ImageVersionBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lxq on 2018/5/3.
 */

public class RvImageVersionAdapter extends CJSBaseRecyclerViewAdapter<ImageVersionBean, RvImageVersionAdapter.ViewHolder> {

    private int type;

    public RvImageVersionAdapter(Context context, int type) {
        super(context);
        this.type = type;
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_image_version, parent, false));
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, final int position) {
        ImageVersionBean bean = datas.get(position);
        holder.btnMake.setVisibility(type == 0 ? View.GONE : View.VISIBLE);
        holder.line.setVisibility(type == 0 ? View.GONE : View.VISIBLE);
        holder.tvImageVersion.setText(bean.getVersion());
        holder.tvUpdateDate.setText(bean.getUpdateTime());
        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.btnMake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.btnDeploy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_image_version)
        TextView tvImageVersion;
        @BindView(R.id.tv_update_date)
        TextView tvUpdateDate;
        @BindView(R.id.btn_del)
        Button btnDel;
        @BindView(R.id.btn_make)
        Button btnMake;
        @BindView(R.id.btn_deploy)
        Button btnDeploy;
        @BindView(R.id.line)
        View line;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
