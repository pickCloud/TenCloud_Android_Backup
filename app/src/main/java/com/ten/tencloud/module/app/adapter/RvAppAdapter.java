package com.ten.tencloud.module.app.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.flexbox.FlexboxLayout;
import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.utils.UiUtils;
import com.ten.tencloud.utils.glide.GlideUtils;
import com.ten.tencloud.widget.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxh@10.com on 2018/3/27.
 */
public class RvAppAdapter extends BaseQuickAdapter<AppBean, BaseViewHolder> {
    public RvAppAdapter() {
        super(R.layout.item_app_service_app);
    }

    @Override
    protected void convert(BaseViewHolder helper, AppBean item) {

        GlideUtils.getInstance().loadCircleImage(mContext, (ImageView) helper.itemView.findViewById(R.id.iv_logo), item.getLogo_url(), R.mipmap.icon_app_photo);
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.tv_name, item.getName());
        } else {
            helper.setText(R.id.tv_name, "");
        }
        if (!TextUtils.isEmpty(item.getDescription())) {
            helper.setText(R.id.tv_source, item.getDescription());
        } else {
            helper.setText(R.id.tv_source, "");
        }
        if (!TextUtils.isEmpty(item.getCreate_time())) {
            helper.setText(R.id.tv_create_date, item.getCreate_time());
        } else {
            helper.setText(R.id.tv_create_date, "");
        }
        if (!TextUtils.isEmpty(item.getUpdate_time())) {
            helper.setText(R.id.tv_update_date, item.getUpdate_time());
        } else {
            helper.setText(R.id.tv_update_date, "");
        }

        switch (item.getStatus()) {
            case Constants.APP_STATUS_INIT:
                helper.setBackgroundRes(R.id.tv_status, R.drawable.shape_app_status_init);
                helper.setTextColor(R.id.tv_status, UiUtils.getColor(R.color.text_color_09bb07));
                helper.setText(R.id.tv_status, "初创建");
                break;
            case Constants.APP_STATUS_NORMAL:
                helper.setBackgroundRes(R.id.tv_status, R.drawable.shape_app_status_normal);
                helper.setTextColor(R.id.tv_status, UiUtils.getColor(R.color.text_color_48bbc0));
                helper.setText(R.id.tv_status, "正常");
                break;
            case Constants.APP_STATUS_ERROR:
                helper.setBackgroundRes(R.id.tv_status, R.drawable.shape_app_status_error);
                helper.setTextColor(R.id.tv_status, UiUtils.getColor(R.color.text_color_ef9a9a));
                helper.setText(R.id.tv_status, "异常");
                break;
        }

        FlexboxLayout mFblLabel = helper.itemView.findViewById(R.id.fbl_label);
        mFblLabel.removeAllViews();
        String label_name = item.getLabel_name();
        if (!TextUtils.isEmpty(label_name)) {
            String[] labels = label_name.split(",");
            for (String labelBean : labels) {
                View labelView = mLayoutInflater.inflate(R.layout.item_app_service_label, null, false);
                ((TextView) labelView.findViewById(R.id.tv_label_name)).setText(labelBean);
                mFblLabel.addView(labelView);
            }
        }

    }

//    public RvAppAdapter(Context context) {
//        super(context);
//    }
//
//    @Override
//    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = mLayoutInflater.inflate(R.layout.item_app_service_app, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    protected void doOnBindViewHolder(ViewHolder holder, int position) {
//
//        GlideUtils.getInstance().loadCircleImage(mContext, holder.mIvLogo, item.getLogo_url(), R.mipmap.icon_app_photo);
//        if (!TextUtils.isEmpty(item.getName())) {
//            holder.mTvName.setText(item.getName());
//        } else {
//            holder.mTvName.setText("");
//        }
//        if (!TextUtils.isEmpty(item.getDescription())) {
//            holder.mTvSource.setText(item.getDescription());
//        } else {
//            holder.mTvSource.setText("");
//        }
//        if (!TextUtils.isEmpty(item.getCreate_time())) {
//            holder.mTvCreateDate.setText(item.getCreate_time());
//        } else {
//            holder.mTvCreateDate.setText("");
//        }
//        if (!TextUtils.isEmpty(item.getUpdate_time())) {
//            holder.mTvUpdateDate.setText(item.getUpdate_time());
//        } else {
//            holder.mTvUpdateDate.setText("");
//        }
//
//        switch (item.getStatus()) {
//            case Constants.APP_STATUS_INIT:
//                holder.mTvStatus.setBackgroundResource(R.drawable.shape_app_status_init);
//                holder.mTvStatus.setTextColor(UiUtils.getColor(R.color.text_color_09bb07));
//                holder.mTvStatus.setText("初创建");
//                break;
//            case Constants.APP_STATUS_NORMAL:
//                holder.mTvStatus.setBackgroundResource(R.drawable.shape_app_status_normal);
//                holder.mTvStatus.setTextColor(UiUtils.getColor(R.color.text_color_48bbc0));
//                holder.mTvStatus.setText("正常");
//                break;
//            case Constants.APP_STATUS_ERROR:
//                holder.mTvStatus.setBackgroundResource(R.drawable.shape_app_status_error);
//                holder.mTvStatus.setTextColor(UiUtils.getColor(R.color.text_color_ef9a9a));
//                holder.mTvStatus.setText("异常");
//                break;
//        }
//
//        holder.mFblLabel.removeAllViews();
//        String label_name = item.getLabel_name();
//        if (!TextUtils.isEmpty(label_name)) {
//            String[] labels = label_name.split(",");
//            for (String labelBean : labels) {
//                View labelView = mLayoutInflater.inflate(R.layout.item_app_service_label, null, false);
//                ((TextView) labelView.findViewById(R.id.tv_label_name)).setText(labelBean);
//                holder.mFblLabel.addView(labelView);
//            }
//        }
//
//    }
//
//    static class ViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.iv_logo)
//        CircleImageView mIvLogo;
//        @BindView(R.id.tv_name)
//        TextView mTvName;
//        @BindView(R.id.fbl_label)
//        FlexboxLayout mFblLabel;
//        @BindView(R.id.tv_status)
//        TextView mTvStatus;
//        @BindView(R.id.tv_source)
//        TextView mTvSource;
//        @BindView(R.id.tv_create_date)
//        TextView mTvCreateDate;
//        @BindView(R.id.tv_update_date)
//        TextView mTvUpdateDate;
//
//        ViewHolder(View view) {
//            super(view);
//            ButterKnife.bind(this, view);
//        }
//    }
}
