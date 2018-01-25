package com.ten.tencloud.module.user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.PermissionTreeNodeBean;
import com.ten.tencloud.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lxq on 2018/1/24.
 */

public class RvTreeFilterItemOtherAdapter extends CJSBaseRecyclerViewAdapter<PermissionTreeNodeBean, RvTreeFilterItemOtherAdapter.ViewHolder> {

    private List<Integer> selectPos = new ArrayList<>();
    //查看状态
    private boolean isView;

    public RvTreeFilterItemOtherAdapter(Context context, boolean isView) {
        super(context);
        this.isView = isView;
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_permission_other, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, final int position) {
        final PermissionTreeNodeBean bean = datas.get(position);
        holder.tvName.setText(Utils.strIsEmptyForDefault(bean.getName(), bean.getFilename()));
        holder.cbSelect.setChecked(selectPos.contains(bean.getId()));
        holder.tvName.setSelected(selectPos.contains(bean.getId()));
        if (isView) {
            holder.cbSelect.setVisibility(View.INVISIBLE);
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isView) {
                    if (selectPos.contains(bean.getId())) {
                        selectPos.remove((Integer) bean.getId());
                    } else {
                        selectPos.add(bean.getId());
                    }
                    notifyItemChanged(position);
                }
            }
        });
    }

    public List<Integer> getSelectPos() {
        return selectPos;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cb_select)
        CheckBox cbSelect;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.layout)
        View layout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
