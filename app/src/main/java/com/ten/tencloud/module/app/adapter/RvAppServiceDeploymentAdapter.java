package com.ten.tencloud.module.app.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.DeploymentBean;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.utils.UiUtils;
import com.ten.tencloud.widget.NoTouchRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxh@10.com on 2018/3/27.
 */
public class RvAppServiceDeploymentAdapter extends CJSBaseRecyclerViewAdapter<DeploymentBean, RvAppServiceDeploymentAdapter.ViewHolder> {


    public RvAppServiceDeploymentAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_app_service_deployment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, int position) {
        holder.mTvName.setText(datas.get(position).getName());
        switch (datas.get(position).getStatus()) {
            case Constants.APP_STATUS_INIT:
                holder.mTvStatus.setBackgroundResource(R.drawable.shape_app_status_init_round);
                holder.mTvStatus.setCompoundDrawablesWithIntrinsicBounds(UiUtils.getDrawable( R.mipmap.icon_detail_green), null, null, null);
                holder.mTvStatus.setTextColor(UiUtils.getColor(R.color.text_color_09bb07));
                holder.mTvStatus.setText("初创建");
                break;
            case Constants.APP_STATUS_NORMAL:
                holder.mTvStatus.setBackgroundResource(R.drawable.shape_app_status_normal_round);
                holder.mTvStatus.setCompoundDrawablesWithIntrinsicBounds(UiUtils.getDrawable(R.mipmap.icon_detail), null, null, null);
                holder.mTvStatus.setTextColor(UiUtils.getColor(R.color.text_color_48bbc0));
                holder.mTvStatus.setText("运行中");
                break;
            case Constants.APP_STATUS_ERROR:
                holder.mTvStatus.setBackgroundResource(R.drawable.shape_app_status_error_round);
                holder.mTvStatus.setCompoundDrawablesWithIntrinsicBounds(UiUtils.getDrawable( R.mipmap.icon_detail_pink), null, null, null);
                holder.mTvStatus.setTextColor(UiUtils.getColor(R.color.text_color_ef9a9a));
                holder.mTvStatus.setText("异常");
                break;
        }
        holder.mTvLinkApp.setText(datas.get(position).getLinkApp());
        holder.mTvCreateDate.setText(datas.get(position).getCreateDate());

        holder.mRvPod.setLayoutManager(new GridLayoutManager(mContext, 5) {

            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        });
        RvAppPodAdapter rvAppPodAdapter = new RvAppPodAdapter(mContext);
        rvAppPodAdapter.setDatas(datas.get(position).getPodList());
        holder.mRvPod.setAdapter(rvAppPodAdapter);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_status)
        TextView mTvStatus;
        @BindView(R.id.rv_pod)
        NoTouchRecyclerView mRvPod;
        @BindView(R.id.tv_link_app)
        TextView mTvLinkApp;
        @BindView(R.id.tv_create_date)
        TextView mTvCreateDate;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
