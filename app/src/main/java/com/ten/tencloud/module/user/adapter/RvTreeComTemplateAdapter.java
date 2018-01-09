package com.ten.tencloud.module.user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.PermissionTemplateBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lxq on 2018/1/9.
 */

public class RvTreeComTemplateAdapter extends CJSBaseRecyclerViewAdapter<PermissionTemplateBean, RvTreeComTemplateAdapter.ViewHolder> {

    private int selectPos = -1;

    public RvTreeComTemplateAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_template_tree, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, final int position) {
        PermissionTemplateBean templateBean = datas.get(position);
        holder.tvName.setText(templateBean.getName());
        holder.flItem.setSelected(selectPos == position);
        holder.ivStatus.setVisibility(selectPos == position ? View.VISIBLE : View.INVISIBLE);
        holder.flItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPos = position;
                notifyDataSetChanged();
                if (mOnSelectListener != null) {
                    mOnSelectListener.onSelect(datas.get(position));
                }
            }
        });
    }

    public interface OnSelectListener {
        void onSelect(PermissionTemplateBean bean);
    }

    private OnSelectListener mOnSelectListener;

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.fl_item)
        FrameLayout flItem;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.iv_status)
        ImageView ivStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
