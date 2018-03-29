package com.ten.tencloud.module.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.DeploymentBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxh@10.com on 2018/3/28.
 */
public class RvPodAdapter extends CJSBaseRecyclerViewAdapter<DeploymentBean.Pod, RvPodAdapter.ViewHolder> {


    public RvPodAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_app_servcie_deployment_pod, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, int position) {
        holder.mTvPodName.setText(datas.get(position).getName());
        holder.mTvCount.setText(String.valueOf(datas.get(position).getCount()));
        holder.mTvUnit.setText(position == datas.size() - 1 ? "s" : "个");
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_count)
        TextView mTvCount;
        @BindView(R.id.tv_unit)
        TextView mTvUnit;
        @BindView(R.id.tv_pod_name)
        TextView mTvPodName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
