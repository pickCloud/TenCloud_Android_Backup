package com.ten.tencloud.widget.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ten.tencloud.utils.UiUtils;

/**
 * Created by chenxh@10.com on 2018/3/28.
 */
public class ServiceItemDecoration extends Hor16ItemDecoration {

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = UiUtils.dip2px(parent.getContext(), 8);
    }
}
