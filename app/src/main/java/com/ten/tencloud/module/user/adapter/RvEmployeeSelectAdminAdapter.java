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
import com.ten.tencloud.model.AppBaseCache;
import com.ten.tencloud.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lxq on 2018/1/2.
 */

public class RvEmployeeSelectAdminAdapter extends CJSBaseRecyclerViewAdapter<EmployeeBean, RvEmployeeSelectAdminAdapter.ViewHolder> {


    private int selectPos = -1;

    public RvEmployeeSelectAdminAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_employee_select_admin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, final int position) {
        if (selectPos == position) {
            holder.ivSelect.setVisibility(View.VISIBLE);
        } else {
            holder.ivSelect.setVisibility(View.INVISIBLE);
        }
        EmployeeBean employee = datas.get(position);
        if (position + 1 == datas.size()) {
            holder.line.setVisibility(View.INVISIBLE);
        } else {
            holder.line.setVisibility(View.VISIBLE);
        }
        holder.tvName.setText(employee.getName());
        holder.tvPhone.setText(Utils.hide4Phone(employee.getMobile()));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPos = position;
                notifyDataSetChanged();
            }
        });
    }

    /**
     * @return
     */
    public EmployeeBean getSelectObject() {
        if (selectPos == -1) {
            return null;
        }
        return datas.get(selectPos);
    }

    public void setSelectPos() {
        int id = (int) AppBaseCache.getInstance().getUserInfo().getId();
        for (int i = 0; i < datas.size(); i++) {
            EmployeeBean employeeBean = datas.get(i);
            if (employeeBean.getId() == id) {
                selectPos = i;
                notifyDataSetChanged();
                return;
            }
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
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
