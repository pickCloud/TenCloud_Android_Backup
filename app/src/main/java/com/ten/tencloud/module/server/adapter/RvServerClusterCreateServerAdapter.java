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
import com.ten.tencloud.bean.ServerBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lxq on 2018/1/2.
 */

public class RvServerClusterCreateServerAdapter extends CJSBaseRecyclerViewAdapter<ServerBean, RvServerClusterCreateServerAdapter.ViewHolder> {

    public RvServerClusterCreateServerAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_server_cluster_create_server, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(final ViewHolder holder, final int position) {
        final ServerBean bean = datas.get(position);
        if (position + 1 == datas.size()) {
            holder.line.setVisibility(View.INVISIBLE);
        } else {
            holder.line.setVisibility(View.VISIBLE);
        }
        holder.tvName.setText(bean.getName());
        holder.tvIp.setText(bean.getPublic_ip());
        holder.ivSelect.setSelected(bean.isSelect());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean.setSelect(!bean.isSelect());
                notifyItemChanged(position);
            }
        });

        String provider = bean.getProvider();
        if ("阿里云".equals(provider)) {
            holder.ivIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_aliyun));
        } else if ("亚马逊云".equals(provider)) {
            holder.ivIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_amazon));
        } else if ("微软云".equals(provider)) {
            holder.ivIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_microyun));
        }

    }

    public List<ServerBean> getSelectObj() {
        List<ServerBean> selects = new ArrayList<>();
        for (ServerBean data : datas) {
            if (data.isSelect()) {
                selects.add(data);
            }
        }
        return selects;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_select)
        ImageView ivSelect;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_ip)
        TextView tvIp;
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
