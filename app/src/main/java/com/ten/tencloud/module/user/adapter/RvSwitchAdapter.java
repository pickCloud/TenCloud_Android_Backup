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
import com.ten.tencloud.bean.CompanyBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lxq on 2017/12/19.
 */

public class RvSwitchAdapter extends CJSBaseRecyclerViewAdapter<CompanyBean, RvSwitchAdapter.ViewHolder> {

    private int selectPos = -1;//选中

    public RvSwitchAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_role_switch, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, final int position) {
        CompanyBean companyBean = datas.get(position);
        holder.tvRole.setSelected(selectPos == position);
        holder.ivSelect.setVisibility(selectPos == position ? View.VISIBLE : View.INVISIBLE);
        holder.tvName.setText(companyBean.getCompany_name());
        if (companyBean.getCid() == 0) {
            holder.tvRole.setText("个人");
        } else if (companyBean.getIs_admin() != 0) {
            holder.tvRole.setText("管理员");
        } else {
            holder.tvRole.setText("员工");
        }
        holder.tvName.setSelected(selectPos == position);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPos != position) {
                    notifyItemChanged(selectPos);
                    selectPos = position;
                    notifyItemChanged(position);
                    if (mOnSelectListener != null) {
                        mOnSelectListener.onSelect(datas.get(position));
                    }
                }
            }
        });
    }

    public void setSelectCid(int cid) {
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).getCid() == cid) {
                selectPos = i;
                break;
            }
        }
    }

    public interface OnSelectListener {
        void onSelect(CompanyBean company);
    }

    private OnSelectListener mOnSelectListener;

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_role)
        TextView tvRole;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.iv_select)
        ImageView ivSelect;
        @BindView(R.id.ll_layout)
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
