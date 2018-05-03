package com.ten.tencloud.module.image.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.ImageBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lxq on 2018/5/3.
 */

public class RvImagesAdapter extends CJSBaseRecyclerViewAdapter<ImageBean, RvImagesAdapter.ViewHolder> {
    public RvImagesAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_image, parent, false));
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, int position) {
        ImageBean imageBean = datas.get(position);
        holder.tvName.setText(imageBean.getName());
        holder.tvSource.setText(imageBean.getType() == 0 ? "内部应用" : "外部镜像");
        holder.tvLatestVersion.setText(imageBean.getVersion());
        holder.tvUpdateDate.setText(imageBean.getUpdate_time());
        holder.fblLabel.removeAllViews();
        String label_name = datas.get(position).getLabel_name();
        if (!TextUtils.isEmpty(label_name)) {
            String[] labels = label_name.split(",");
            for (String labelBean : labels) {
                View labelView = mLayoutInflater.inflate(R.layout.item_app_service_label, null, false);
                ((TextView) labelView.findViewById(R.id.tv_label_name)).setText(labelBean);
                holder.fblLabel.addView(labelView);
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.iv_logo)
        ImageView ivLogo;
        @BindView(R.id.fbl_label)
        FlexboxLayout fblLabel;
        @BindView(R.id.tv_source)
        TextView tvSource;
        @BindView(R.id.tv_latest_version)
        TextView tvLatestVersion;
        @BindView(R.id.tv_update_date)
        TextView tvUpdateDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
