package com.ten.tencloud.module.user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.PermissionTreeNodeBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lxq on 2018/1/24.
 */

public class RvTreeFilterItemServerAdapter extends CJSBaseRecyclerViewAdapter<PermissionTreeNodeBean, RvTreeFilterItemServerAdapter.ViewHolder> {

    private List<Integer> selectPos = new ArrayList<>();
    private boolean isView;

    public RvTreeFilterItemServerAdapter(Context context, boolean isView) {
        super(context);
        this.isView = isView;
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_permission_server, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, final int position) {
        final PermissionTreeNodeBean bean = datas.get(position);
        holder.tvName.setText(bean.getName());
        holder.cbSelect.setChecked(selectPos.contains(bean.getSid()));
        if (isView) {
            holder.cbSelect.setVisibility(View.INVISIBLE);
        }
        holder.tvName.setSelected(selectPos.contains(bean.getSid()));
        holder.tvStatus.setText(bean.getStatus());
        holder.tvIp.setText(bean.getPublic_ip());
        String provider = bean.getProvider();
        if ("阿里云".equals(provider)) {
            holder.ivProviderIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_aliyun));
        } else if ("亚马逊云".equals(provider)) {
            holder.ivProviderIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_amazon));
        } else if ("微软云".equals(provider)) {
            holder.ivProviderIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_microyun));
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isView) {
                    if (selectPos.contains(bean.getSid())) {
                        selectPos.remove((Integer) bean.getSid());
                    } else {
                        selectPos.add(bean.getSid());
                    }
                    notifyItemChanged(position);
                }
            }
        });
    }

    public List<Integer> getSelectPos() {
        return selectPos;
    }

    public void setSelectPos(List<Integer> selectPos) {
        this.selectPos = selectPos;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cb_select)
        CheckBox cbSelect;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.iv_provider_icon)
        ImageView ivProviderIcon;
        @BindView(R.id.tv_ip)
        TextView tvIp;
        @BindView(R.id.layout)
        View layout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
