package com.ten.tencloud.module.user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.PermissionTemplateBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lxq on 2017/12/25.
 */


public class RvTemplateAdapter extends CJSBaseRecyclerViewAdapter<PermissionTemplateBean, RvTemplateAdapter.ViewHolder> {

    public RvTemplateAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_template, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, int position) {
        PermissionTemplateBean templateBean = datas.get(position);
        holder.tvName.setText(templateBean.getName());
        holder.tvFuncCount.setText("" + handCount(templateBean.getPermissions()));
        int dataCount = handCount(templateBean.getAccess_filehub()) +
                handCount(templateBean.getAccess_projects()) +
                handCount(templateBean.getAccess_servers());
        holder.tvDataCount.setText(dataCount + "");
        if ("管理员".equals(templateBean.getName())) {
            holder.temp1.setSelected(true);
            holder.temp2.setSelected(true);
            holder.temp3.setSelected(true);
            holder.temp4.setSelected(true);
            holder.temp5.setSelected(true);
            holder.tvDataCount.setSelected(true);
            holder.tvFuncCount.setSelected(true);
        } else {
            holder.temp1.setSelected(false);
            holder.temp2.setSelected(false);
            holder.temp3.setSelected(false);
            holder.temp4.setSelected(false);
            holder.temp5.setSelected(false);
            holder.tvDataCount.setSelected(false);
            holder.tvFuncCount.setSelected(false);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_func_count)
        TextView tvFuncCount;
        @BindView(R.id.tv_data_count)
        TextView tvDataCount;
        @BindView(R.id.temp1)
        View temp1;
        @BindView(R.id.temp2)
        View temp2;
        @BindView(R.id.temp3)
        View temp3;
        @BindView(R.id.temp4)
        View temp4;
        @BindView(R.id.temp5)
        View temp5;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 处理权限数量
     *
     * @param permission
     * @return
     */
    private int handCount(String permission) {
        if (TextUtils.isEmpty(permission)) {
            return 0;
        }
        return permission.split(",").length;
    }

}
