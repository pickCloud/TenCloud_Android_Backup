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
import com.ten.tencloud.bean.ServerProviderBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lxq on 2018/1/2.
 */

public class RvServerSelectProviderAdapter extends CJSBaseRecyclerViewAdapter<ServerProviderBean, RvServerSelectProviderAdapter.ViewHolder> {


    private int selectPos = -1;

    public RvServerSelectProviderAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_server_select_provider, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, final int position) {
        if (selectPos == position) {
            holder.ivSelect.setVisibility(View.VISIBLE);
        } else {
            holder.ivSelect.setVisibility(View.INVISIBLE);
        }
        ServerProviderBean employee = datas.get(position);
        if (position + 1 == datas.size()) {
            holder.line.setVisibility(View.INVISIBLE);
        } else {
            holder.line.setVisibility(View.VISIBLE);
        }
        holder.tvProvider.setText(employee.getName());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectPos(position);
            }
        });
    }

    /**
     * @return
     */
    public ServerProviderBean getSelectObject() {
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

        @BindView(R.id.iv_provider)
        ImageView ivProvider;
        @BindView(R.id.tv_provider)
        TextView tvProvider;
        @BindView(R.id.iv_select)
        ImageView ivSelect;
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
