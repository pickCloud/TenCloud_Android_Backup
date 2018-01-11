package com.ten.tencloud.module.user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.CompanyBean;
import com.ten.tencloud.utils.DateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lxq on 2017/12/19.
 */

public class RvCompanyAdapter extends CJSBaseRecyclerViewAdapter<CompanyBean, RvCompanyAdapter.ViewHolder> {

    public RvCompanyAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_company, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, final int position) {
        CompanyBean companyBean = datas.get(position);
        holder.tvName.setText(companyBean.getCompany_name());
        holder.tvAdmin.setText(companyBean.getIs_admin() == 1 ? "管理员" : "员工");
        holder.tvAdmin.setSelected(companyBean.getIs_admin() == 1);
        holder.tvApplyTime.setText(DateUtils.dateToDefault(companyBean.getCreate_time()));
        holder.tvCheckTime.setText(DateUtils.dateToDefault(companyBean.getUpdate_time()));
        int status = companyBean.getStatus();
        if (status == -1) {
            holder.tvStatus.setText("审核不通过");
            holder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.text_color_f15532));
        } else if (status == 0) {
            holder.tvStatus.setText("待审核");
            holder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else if (status == 1) {
            holder.tvStatus.setText("审核通过");
            holder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.text_color_09bb07));
        } else if (status == 2) {
            holder.tvStatus.setText("创建人");
            holder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.text_color_09bb07));
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_company_name)
        TextView tvName;
        @BindView(R.id.tv_admin)
        TextView tvAdmin;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_apply_time)
        TextView tvApplyTime;
        @BindView(R.id.tv_check_time)
        TextView tvCheckTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
