package com.ten.tencloud.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by lxq on 2018/3/13.
 */

public class HeatLayout extends FrameLayout {

    //实际的容器
    private LinearLayout mLlContent;

    //宽高比
    private float mAspectRatio = 3 / 4f;

    public HeatLayout(Context context) {
        this(context, null);
    }

    public HeatLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeatLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
                getDefaultSize(0, heightMeasureSpec));
        int measuredWidth = getMeasuredWidth();
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(measuredWidth,
                MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                (int) (measuredWidth * mAspectRatio), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLlContent = new LinearLayout(getContext());
        mLlContent.setBackgroundColor(Color.BLACK);
        mLlContent.setAlpha(0f);
        addView(mLlContent);
    }

    public void setAlpha(float alpha) {
        mLlContent.setAlpha(alpha);
    }
}
