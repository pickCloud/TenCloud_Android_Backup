package com.ten.tencloud.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

/**
 * 侧滑菜单
 * Created by lxq on 2018/5/2.
 */

public class SwipeMenuRecycleView extends RecyclerView {

    private Scroller mScroller;

    public SwipeMenuRecycleView(Context context) {
        this(context, null);
    }

    public SwipeMenuRecycleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeMenuRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mScroller = new Scroller(context, new LinearInterpolator());
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int x = (int) e.getX();//获得当前点击的X坐标
        int y = (int) e.getY();//获得当前点击的Y坐标
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return super.onTouchEvent(e);
    }
}