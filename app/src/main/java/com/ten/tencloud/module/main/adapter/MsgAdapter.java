package com.ten.tencloud.module.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.bean.MessageBean;

import butterknife.ButterKnife;

/**
 * Created by lxq on 2018/1/10.
 */

public class MsgAdapter extends CJSBaseRecyclerViewAdapter<MessageBean, MsgAdapter.ViewHolder> {

    public MsgAdapter(Context context) {
        super(context);
    }

    @Override
    protected ViewHolder doOnCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void doOnBindViewHolder(ViewHolder holder, int position) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {



        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
