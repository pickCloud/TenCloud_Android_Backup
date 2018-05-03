package com.ten.tencloud.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.ten.tencloud.R;

/**
 * Created by lxq on 2018/5/2.
 */

public class SwipeMenuViewHolder extends RecyclerView.ViewHolder {
    public FrameLayout swipeMenu;

    public SwipeMenuViewHolder(View itemView) {
        super(itemView);
        swipeMenu = itemView.findViewById(R.id.swipe_menu);
    }
}
