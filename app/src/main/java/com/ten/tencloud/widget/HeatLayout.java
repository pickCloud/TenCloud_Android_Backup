package com.ten.tencloud.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.ten.tencloud.R;

/**
 * Created by lxq on 2018/3/13.
 */

public class HeatLayout extends FrameLayout {

    //实际的容器
    private View mShadeView;

    //宽高比
    private float mAspectRatio = 3 / 4f;
    private View mClickShadeView;

    public HeatLayout(Context context) {
        this(context, null);
    }

    public HeatLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeatLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mShadeView = new View(getContext());
        mShadeView.setBackgroundColor(Color.BLACK);
        mShadeView.setAlpha(0f);
        //点击效果遮罩
        mClickShadeView = new View(getContext());
        mClickShadeView.setBackgroundResource(R.drawable.selector_button_shade);
        addView(mShadeView);
        addView(mClickShadeView);
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

    public void setAlpha(float alpha) {
        mShadeView.setAlpha(alpha);
    }
}
