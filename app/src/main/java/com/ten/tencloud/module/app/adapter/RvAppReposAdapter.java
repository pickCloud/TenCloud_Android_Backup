package com.ten.tencloud.module.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.ReposBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxh@10.com on 2018/3/27.
 */
public class RvAppReposAdapter extends CJSBaseRecyclerViewAdapter<ReposBean, RvAppReposAdapter.ViewHolder> {

    private int selectPos = -1;

    public RvAppReposAdapter(Context context) {
        super(context);
    }

    public void setSelectPos(int pos) {
        selectPos = pos;
        notifyDataSetChanged();
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_app_service_repos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, int position) {
        holder.mTvReposName.setText(TextUtils.isEmpty(datas.get(position).getRepos_name()) ? "" : datas.get(position).getRepos_name());
        holder.mTvReposUrl.setText(TextUtils.isEmpty(datas.get(position).getRepos_url()) ? "" : datas.get(position).getRepos_url());
        holder.mIvSelect.setVisibility(position == selectPos ? View.VISIBLE : View.INVISIBLE);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_repos_name)
        TextView mTvReposName;
        @BindView(R.id.tv_repos_url)
        TextView mTvReposUrl;
        @BindView(R.id.iv_select)
        ImageView mIvSelect;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
