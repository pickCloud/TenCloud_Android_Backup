package com.ten.tencloud.module.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.DeploymentBean;
import com.ten.tencloud.bean.ImageBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxh@10.com on 2018/3/27.
 */
public class RvAppDetailDeploymentAdapter extends CJSBaseRecyclerViewAdapter<DeploymentBean, RvAppDetailDeploymentAdapter.ViewHolder> {


    public RvAppDetailDeploymentAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_app_service_app_detail_deployment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, int position) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_deploy_name)
        TextView mTvDeployName;
        @BindView(R.id.tv_deploy_status)
        TextView mTvDeployStatus;
        @BindView(R.id.rv_pod)
        RecyclerView mRvPod;
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
