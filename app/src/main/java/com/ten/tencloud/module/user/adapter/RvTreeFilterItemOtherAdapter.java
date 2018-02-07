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

        Integer type = bean.getType();
        if (type == null) {
            holder.ivType.setVisibility(View.GONE);
        } else if (type == 1) {
            holder.ivType.setVisibility(View.VISIBLE);
            holder.ivType.setImageResource(selectPos.contains(bean.getId()) ? R.mipmap.icon_folder_on : R.mipmap.icon_folder_off);
        } else if (type == 0) {
            holder.ivType.setVisibility(View.VISIBLE);
            if (bean.getMime().contains("image/")) {
                holder.ivType.setImageResource(selectPos.contains(bean.getId()) ? R.mipmap.icon_pic_on : R.mipmap.icon_pic_off);
            } else {
                holder.ivType.setImageResource(selectPos.contains(bean.getId()) ? R.mipmap.icon_doc_on : R.mipmap.icon_doc_off);
            }
        }

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

    public void setSelectPos(List<Integer> selectPos) {
        this.selectPos = selectPos;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cb_select)
        CheckBox cbSelect;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.iv_type)
        ImageView ivType;// 文件类型
        @BindView(R.id.layout)
        View layout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
