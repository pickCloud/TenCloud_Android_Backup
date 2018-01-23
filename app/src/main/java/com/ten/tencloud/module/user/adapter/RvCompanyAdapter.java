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
import com.ten.tencloud.constants.Constants;
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
        int status = companyBean.getStatus();
        holder.llApply.setVisibility(View.VISIBLE);
        holder.llCheck.setVisibility(View.VISIBLE);
        holder.tvApplyTimeText.setText("申请时间");
        //创建人和审核中的不需要审核时间
        if (status == Constants.EMPLOYEE_STATUS_CODE_CHECKING || status == Constants.EMPLOYEE_STATUS_CODE_CREATE) {
            holder.llCheck.setVisibility(View.INVISIBLE);
        }
        //创建人
        if (status == Constants.EMPLOYEE_STATUS_CODE_CREATE) {
            holder.tvApplyTimeText.setText("创建时间");
        }
        //待加入状态
        if (status == Constants.EMPLOYEE_STATUS_CODE_WAITING) {
            holder.llApply.setVisibility(View.INVISIBLE);
            holder.llCheck.setVisibility(View.INVISIBLE);
        }

        holder.tvApplyTime.setText(status == Constants.EMPLOYEE_STATUS_CODE_WAITING ? "-" : DateUtils.dateToDefault(companyBean.getCreate_time()));
        holder.tvCheckTime.setText(status == Constants.EMPLOYEE_STATUS_CODE_WAITING ? "-" : DateUtils.dateToDefault(companyBean.getUpdate_time()));
        if (status == Constants.EMPLOYEE_STATUS_CODE_NO_PASS) {
            holder.tvStatus.setText("审核不通过");
            holder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.text_color_f15532));
        } else if (status == Constants.EMPLOYEE_STATUS_CODE_CHECKING) {
            holder.tvStatus.setText("待审核");
            holder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else if (status == Constants.EMPLOYEE_STATUS_CODE_PASS) {
            holder.tvStatus.setText("审核通过");
            holder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.text_color_09bb07));
        } else if (status == Constants.EMPLOYEE_STATUS_CODE_CREATE) {
            holder.tvStatus.setText("创建人");
            holder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.text_color_09bb07));
        } else if (status == Constants.EMPLOYEE_STATUS_CODE_WAITING) {
            holder.tvStatus.setText("待加入");
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_apply)
        View llApply;
        @BindView(R.id.ll_check)
        View llCheck;

        @BindView(R.id.tv_company_name)
        TextView tvName;
        @BindView(R.id.tv_admin)
        TextView tvAdmin;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_apply_time)
        TextView tvApplyTime;
        @BindView(R.id.tv_apply_time_text)
        TextView tvApplyTimeText;
        @BindView(R.id.tv_check_time)
        TextView tvCheckTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
