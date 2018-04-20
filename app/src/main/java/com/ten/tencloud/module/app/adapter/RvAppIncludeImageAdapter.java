package com.ten.tencloud.module.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.ImageBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxh@10.com on 2018/3/27.
 */
public class RvAppIncludeImageAdapter extends CJSBaseRecyclerViewAdapter<ImageBean, RvAppIncludeImageAdapter.ViewHolder> {

    private int selectPos = -1;

    public void setSelectPos(int pos) {
        selectPos = pos;
        notifyDataSetChanged();
    }

    public ImageBean getSelectObj() {
        if (selectPos == -1) {
            return null;
        }
        return datas.get(selectPos);
    }

    public RvAppIncludeImageAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_app_service_include_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, final int position) {
        holder.mTvImageName.setText(datas.get(position).getName());
        holder.mTvImageVersion.setText(datas.get(position).getVersion());
        holder.mTvUpdateDate.setText(datas.get(position).getUpdate_time());
        holder.mLine.setVisibility(position == datas.size() - 1 ? View.INVISIBLE : View.VISIBLE);
        holder.mIvSelect.setVisibility(selectPos == position ? View.VISIBLE : View.INVISIBLE);
        holder.mLlLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectPos(position);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_image_name)
        TextView mTvImageName;
        @BindView(R.id.tv_image_version)
        TextView mTvImageVersion;
        @BindView(R.id.tv_image_update_time)
        TextView mTvUpdateDate;
        @BindView(R.id.iv_select)
        ImageView mIvSelect;
        @BindView(R.id.ll_layout)
        View mLlLayout;
        @BindView(R.id.line)
        View mLine;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
