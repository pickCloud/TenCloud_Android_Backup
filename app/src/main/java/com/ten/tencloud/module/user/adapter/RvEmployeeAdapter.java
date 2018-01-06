package com.ten.tencloud.module.user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.EmployeeBean;
import com.ten.tencloud.utils.Utils;
import com.ten.tencloud.utils.glide.GlideUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lxq on 2018/1/2.
 */

public class RvEmployeeAdapter extends CJSBaseRecyclerViewAdapter<EmployeeBean, RvEmployeeAdapter.ViewHolder> {

    public RvEmployeeAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_employee, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, int position) {
        EmployeeBean employee = datas.get(position);
        if (position + 1 == datas.size()) {
            holder.line.setVisibility(View.INVISIBLE);
        } else {
            holder.line.setVisibility(View.VISIBLE);
        }
        holder.tvName.setText(employee.getName());
        holder.tvPhone.setText(Utils.hide4Phone(employee.getMobile()));
        holder.tvAdmin.setVisibility(employee.getIs_admin() != 0 ? View.VISIBLE : View.INVISIBLE);
        int status = employee.getStatus();
        if (status == -1) {
            holder.tvStatus.setText("审核不通过");
            holder.tvStatus.setEnabled(false);
        } else if (status == 0) {
            holder.tvStatus.setText("待审核");
            holder.tvStatus.setEnabled(true);
            holder.tvStatus.setSelected(false);
        } else {
            holder.tvStatus.setText("审核通过");
            holder.tvStatus.setEnabled(true);
            holder.tvStatus.setSelected(true);
        }
        GlideUtils.getInstance().loadCircleImage(mContext, holder.ivAvatar, employee.getImage_url(), R.mipmap.icon_userphoto);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_avatar)
        ImageView ivAvatar;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_admin)
        TextView tvAdmin;
        @BindView(R.id.line)
        View line;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
