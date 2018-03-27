package com.ten.tencloud.module.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.AppBean;
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
        View view = mLayoutInflater.inflate(R.layout.item_app, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, int position) {

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_logo)
        CircleImageView mIvLogo;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.fbl_label)
        FlexboxLayout mFlLabel;
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
