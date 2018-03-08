package com.ten.tencloud.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

/**
 * 解决嵌套RV时当RV加载完自动滚动到RV
 * Created by lxq on 2018/3/8.
 */

public class TenForbidAutoScrollView extends NestedScrollView {
    public TenForbidAutoScrollView(Context context) {
        super(context);
    }

    public TenForbidAutoScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TenForbidAutoScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        return 0;
    }
}
