package com.ten.tencloud.widget.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ten.tencloud.utils.UiUtils;

/**
 * Created by chenxh@10.com on 2018/3/28.
 */
public class ServiceItemDecoration extends RecyclerView.ItemDecoration {
    private int dpSpace;

    public ServiceItemDecoration() {
        this(16);
    }

    public ServiceItemDecoration(int dpSpace) {
        this.dpSpace = dpSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = UiUtils.dip2px(parent.getContext(), dpSpace);
        outRect.right = UiUtils.dip2px(parent.getContext(), dpSpace);
        outRect.bottom = UiUtils.dip2px(parent.getContext(), 8);
    }
}
