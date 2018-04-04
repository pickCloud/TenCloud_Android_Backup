package com.ten.tencloud.module.server.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.ServerBatchBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lxq on 2018/1/2.
 */

public class RvServerSelectServerAdapter extends CJSBaseRecyclerViewAdapter<ServerBatchBean, RvServerSelectServerAdapter.ViewHolder> {

    private int selectPos = -1;

    public RvServerSelectServerAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_server_select_server, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(final ViewHolder holder, final int position) {
        ServerBatchBean bean = datas.get(position);
        if (position + 1 == datas.size()) {
            holder.line.setVisibility(View.INVISIBLE);
        } else {
            holder.line.setVisibility(View.VISIBLE);
        }
        holder.tvName.setText(bean.getInstance_id());
        holder.tvExist.setVisibility(bean.isIs_add() ? View.VISIBLE : View.INVISIBLE);
        if (!TextUtils.isEmpty(bean.getInner_ip())) {
            holder.tvPrivateIp.setText(bean.getInner_ip() + "（内）");
        } else {
            holder.tvPrivateIp.setText("");
        }
        if (!TextUtils.isEmpty(bean.getPublic_ip())) {
            holder.tvPublicIp.setText(bean.getPublic_ip() + "（外）");
        } else {
            holder.tvPublicIp.setText("");
        }
        holder.tvDes.setText(bean.getRegion_id());
        holder.tvName.setEnabled(!bean.isIs_add());
        holder.tvDes.setEnabled(!bean.isIs_add());
        if (bean.isIs_add()) {
            holder.ivSelect.setSelected(true);
            holder.ivSelect.setAlpha(0.5f);
        } else {
            holder.ivSelect.setSelected(false);
            holder.ivSelect.setAlpha(0f);
        }

        if (!bean.isIs_add()) {
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.ivSelect.setSelected(!holder.ivSelect.isSelected());
                }
            });
        }
    }

    /**
     * @return
     */
    public ServerBatchBean getSelectObject() {
        if (selectPos == -1) {
            return null;
        }
        return datas.get(selectPos);
    }

    public void setSelectPos(int pos) {
        selectPos = pos;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_select)
        ImageView ivSelect;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_exist)
        TextView tvExist;
        @BindView(R.id.ll_status)
        LinearLayout llStatus;
        @BindView(R.id.iv_status)
        ImageView ivStatus;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_des)
        TextView tvDes;
        @BindView(R.id.tv_private_ip)
        TextView tvPrivateIp;
        @BindView(R.id.tv_public_ip)
        TextView tvPublicIp;
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
