package com.ten.tencloud.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.ten.tencloud.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lxq on 2018/5/2.
 */

public class SwipeMenuViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.swipe_menu)
    FrameLayout swipeMenu;

    public SwipeMenuViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(itemView);
    }
}
