package com.ten.tencloud.module.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.MessageBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lxq on 2018/1/10.
 */

public class RvMsgAdapter extends CJSBaseRecyclerViewAdapter<MessageBean, RvMsgAdapter.ViewHolder> {

    public RvMsgAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_msg, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, int position) {
        MessageBean messageBean = datas.get(position);
        holder.tvTime.setText(messageBean.getUpdate_time());
        holder.tvContent.setText(messageBean.getContent());
        if (messageBean.getMode() == 1) {
            holder.tvMode.setText("加入企业");
        } else if (messageBean.getMode() == 2) {
            holder.tvMode.setText("企业变更");
        }
        int subMode = messageBean.getSub_mode();
        if (subMode == 0) {
            holder.tvSubMode.setText("马上审核  >");
        } else if (subMode == 1) {
            holder.tvSubMode.setText("重新提交  >");
        } else if (subMode == 2) {
            holder.tvSubMode.setText("进入企业  >");
        } else if (subMode == 3) {
            holder.tvSubMode.setText("马上查看  >");
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_mode)
        TextView tvMode;
        @BindView(R.id.tv_sub_mode)
        TextView tvSubMode;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_content)
        TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
