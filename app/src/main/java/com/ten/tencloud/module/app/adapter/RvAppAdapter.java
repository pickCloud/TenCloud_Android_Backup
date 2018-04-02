package com.ten.tencloud.module.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.socks.library.KLog;
import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.utils.UiUtils;
import com.ten.tencloud.utils.glide.GlideUtils;
import com.ten.tencloud.widget.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxh@10.com on 2018/3/27.
 */
public class RvAppAdapter extends CJSBaseRecyclerViewAdapter<AppBean, RvAppAdapter.ViewHolder> {

    public RvAppAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_app_service_app, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, int position) {

        KLog.e(datas.get(position).getLogo_url());
        if (!TextUtils.isEmpty(datas.get(position).getLogo_url()))
            GlideUtils.getInstance().loadCircleImage(mContext, holder.mIvLogo, datas.get(position).getLogo_url(), R.mipmap.icon_app_photo);
        if (!TextUtils.isEmpty(datas.get(position).getName()))
            holder.mTvName.setText(datas.get(position).getName());
        if (!TextUtils.isEmpty(datas.get(position).getRepos_https_url()))
            holder.mTvSource.setText(datas.get(position).getRepos_https_url());
        if (!TextUtils.isEmpty(datas.get(position).getCreate_time()))
            holder.mTvCreateDate.setText(datas.get(position).getCreate_time());
        if (!TextUtils.isEmpty(datas.get(position).getUpdate_time()))
            holder.mTvUpdateDate.setText(datas.get(position).getUpdate_time());

        switch (datas.get(position).getStatus()) {
            case Constants.APP_STATUS_INIT:
                holder.mTvStatus.setBackgroundResource(R.drawable.shape_app_status_init);
                holder.mTvStatus.setTextColor(UiUtils.getColor(R.color.text_color_09bb07));
                holder.mTvStatus.setText("初创建");
                break;
            case Constants.APP_STATUS_NORMAL:
                holder.mTvStatus.setBackgroundResource(R.drawable.shape_app_status_normal);
                holder.mTvStatus.setTextColor(UiUtils.getColor(R.color.text_color_48bbc0));
                holder.mTvStatus.setText("正常");
                break;
            case Constants.APP_STATUS_ERROR:
                holder.mTvStatus.setBackgroundResource(R.drawable.shape_app_status_error);
                holder.mTvStatus.setTextColor(UiUtils.getColor(R.color.text_color_ef9a9a));
                holder.mTvStatus.setText("异常");
                break;
        }

//        if (datas.get(position).getLabels() != null && datas.get(position).getLabels().size() != 0) {
//            for (int i = 0; i < datas.get(position).getLabels().size(); i++) {
//                View labelView = mLayoutInflater.inflate(R.layout.item_app_service_label, null, false);
//                ((TextView) labelView.findViewById(R.id.tv_label_name)).setText(datas.get(position).getLabels().get(i));
//                holder.mFblLabel.addView(labelView);
//            }
//        }

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_logo)
        CircleImageView mIvLogo;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.fbl_label)
        FlexboxLayout mFblLabel;
        @BindView(R.id.tv_status)
        TextView mTvStatus;
        @BindView(R.id.tv_source)
        TextView mTvSource;
        @BindView(R.id.tv_create_date)
        TextView mTvCreateDate;
        @BindView(R.id.tv_update_date)
        TextView mTvUpdateDate;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
