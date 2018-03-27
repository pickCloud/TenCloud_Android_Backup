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

    //等级
    public final static int LEVEL_1 = 1;//警告
    public final static int LEVEL_2 = 2;
    public final static int LEVEL_3 = 3;
    public final static int LEVEL_4 = 4;
    public final static int LEVEL_5 = 5;//最低

    //实际的容器
    private View mShadeView;

    //宽高比
    private float mAspectRatio = 3 / 4f;
    private View mClickShadeView;
    public FrameLayout mContent;

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

        mContent = new FrameLayout(context);
        addView(mContent);
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

    /**
     * 设置警告等级
     *
     * @param level
     */
    public void setHeatLevel(int level) {
        if (level == LEVEL_1) {
            setBackgroundResource(R.drawable.fade_server_heat_red);
        } else if (level == LEVEL_2) {
            setBackgroundResource(R.drawable.fade_server_heat_red);
            setAlpha(15f / 100);
        } else if (level == LEVEL_3) {
            setBackgroundResource(R.drawable.fade_server_heat_red);
            setAlpha(30f / 100);
        } else if (level == LEVEL_4) {
            setBackgroundResource(R.drawable.fade_server_heat_green);
            setAlpha(30f / 100);
        } else if (level == LEVEL_5) {
            setBackgroundResource(R.drawable.fade_server_heat_gray);
            setAlpha(10f / 100);
        }
    }
}
