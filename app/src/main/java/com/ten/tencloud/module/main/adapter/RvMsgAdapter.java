package com.ten.tencloud.module.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
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

    private SpannableStringBuilder buildTextColorSpan(String content) {
        SpannableStringBuilder style = new SpannableStringBuilder();
        int count = content.split("【").length - 1;
        int startTemp = 0;
        int endTemp = 0;
        String text = content.replaceAll("【", " ").replaceAll("】", " ");
        style.append(text);
        for (int i = 0; i < count; i++) {
            int start = content.indexOf("【", startTemp);
            int end = content.indexOf("】", endTemp) + 1;
            startTemp = start + 1;
            endTemp = end + 1;
            int color = mContext.getResources().getColor(R.color.colorPrimary);
            style.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return style;
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, int position) {
        MessageBean messageBean = datas.get(position);
        holder.tvTime.setText(messageBean.getUpdate_time());

        String content = messageBean.getContent();
        holder.tvContent.setText(buildTextColorSpan(content));

        if (messageBean.getMode() == 1) {
            holder.tvMode.setText("加入企业");
        } else if (messageBean.getMode() == 2) {
            holder.tvMode.setText("企业变更");
        } else if (messageBean.getMode() == 3) {
            holder.tvMode.setText("离开企业");
        } else if (messageBean.getMode() == 4) {
            holder.tvMode.setText("添加主机");
        } else if (messageBean.getMode() == 5) {
            holder.tvMode.setText("构建镜像");
        }

        final int subMode = messageBean.getSub_mode();
        if (subMode == 0) {
            holder.tvSubMode.setText("马上审核  >");
        } else if (subMode == 1) {
            holder.tvSubMode.setText("重新提交  >");
        } else if (subMode == 2) {
            holder.tvSubMode.setText("进入企业  >");
        } else if (subMode == 3) {
            holder.tvSubMode.setText("马上查看  >");
        } else if (subMode == 4) {
            holder.tvSubMode.setText("查看主机  >");
        } else if (subMode == 5) {
            holder.tvSubMode.setText("添加主机  >");
        }


        if (messageBean.getMode() == 3) {
            holder.tvSubMode.setVisibility(View.GONE);
        } else {
            holder.tvSubMode.setVisibility(View.VISIBLE);
        }
        final String tip = messageBean.getTip();

        holder.tvSubMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBtnClickListener != null) {
                    onBtnClickListener.onClick(subMode, tip);
                }
            }
        });
        holder.viewStatus.setVisibility(messageBean.getStatus() == 0 ? View.VISIBLE : View.INVISIBLE);
    }

    public interface OnBtnClickListener {
        void onClick(int subMode, String tip);
    }

    private OnBtnClickListener onBtnClickListener;

    public void setOnBtnClickListener(RvMsgAdapter.OnBtnClickListener onBtnClickListener) {
        this.onBtnClickListener = onBtnClickListener;
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
        @BindView(R.id.view_status)
        View viewStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
