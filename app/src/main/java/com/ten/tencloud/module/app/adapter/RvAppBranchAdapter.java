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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxh@10.com on 2018/3/27.
 */
public class RvAppBranchAdapter extends CJSBaseRecyclerViewAdapter<String, RvAppBranchAdapter.ViewHolder> {

    private int selectPos = -1;

    public RvAppBranchAdapter(Context context) {
        super(context);
    }

    public void setSelectPos(int pos) {
        selectPos = pos;
        notifyDataSetChanged();
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_app_service_branch, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, int position) {
        holder.mTvName.setText(TextUtils.isEmpty(datas.get(position)) ? "" : datas.get(position));
        holder.mIvSelect.setVisibility(position == selectPos ? View.VISIBLE : View.INVISIBLE);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.iv_select)
        ImageView mIvSelect;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
