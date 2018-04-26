package com.ten.tencloud.module.server.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.ClusterBean;
import com.ten.tencloud.bean.K8sNodeBean;
import com.ten.tencloud.module.server.ui.ServerClusterNodeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lxq on 2017/11/30.
 */

public class RvServerClusterAdapter extends CJSBaseRecyclerViewAdapter<ClusterBean, RvServerClusterAdapter.ViewHolder> {

    private final LayoutInflater mInflater;
    private final Gson mGsonInstance;

    public RvServerClusterAdapter(Context context) {
        super(context);
        mGsonInstance = TenApp.getInstance().getGsonInstance();
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_server_cluster, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(final ViewHolder holder, int position) {
        ClusterBean bean = datas.get(position);
        holder.tvName.setText(bean.getName());
        if (bean.getType() == 1) {
            holder.mTvType.setText("Kubernetes集群");
        } else if (bean.getType() == 2) {
            holder.mTvType.setText("超级计算能力");
        } else if (bean.getType() == 3) {
            holder.mTvType.setText("高可用");
        }
        K8sNodeBean k8sNodeBean = bean.getK8sNodeBean();
        holder.mLlNodes2.setVisibility(View.GONE);
        holder.mLlNodes1.removeAllViews();
        holder.mLlNodes2.removeAllViews();
        createNodeView(holder, k8sNodeBean);
    }

    //创建K8s节点视图
    private void createNodeView(final ViewHolder holder, K8sNodeBean bean) {
        holder.mTvExpend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mLlNodes2.setVisibility(View.VISIBLE);
                holder.mTvExpend.setVisibility(View.GONE);
            }
        });
        if (bean.getItems().size() > 3) {
            holder.mTvExpend.setVisibility(View.VISIBLE);
            for (int i = 0; i < 3; i++) {
                createNodeChildView(holder.mLlNodes1, bean.getItems().get(i));
            }
            for (int i = 3; i < bean.getItems().size(); i++) {
                createNodeChildView(holder.mLlNodes2, bean.getItems().get(i));
            }
        } else {
            holder.mTvExpend.setVisibility(View.GONE);
            for (int i = 0; i < bean.getItems().size(); i++) {
                createNodeChildView(holder.mLlNodes1, bean.getItems().get(i));
            }
        }
    }

    private void createNodeChildView(LinearLayout layout, final K8sNodeBean.ItemsBean itemsBean) {
        View view = mInflater.inflate(R.layout.item_server_cluster_k8s_node, layout, false);
        TextView tvName = view.findViewById(R.id.tv_name);
        view.findViewById(R.id.tv_node_des).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ServerClusterNodeActivity.class);
                intent.putExtra("json", mGsonInstance.toJson(itemsBean));
                mContext.startActivity(intent);
            }
        });
        tvName.setText(itemsBean.getMetadata().getName());
        layout.addView(view);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_type)
        TextView mTvType;
        @BindView(R.id.tv_expend)
        TextView mTvExpend;
        @BindView(R.id.ll_nodes1)
        LinearLayout mLlNodes1;
        @BindView(R.id.ll_nodes2)
        LinearLayout mLlNodes2;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
