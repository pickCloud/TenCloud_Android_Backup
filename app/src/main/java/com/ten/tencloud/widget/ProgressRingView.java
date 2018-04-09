package com.ten.tencloud.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.ten.tencloud.R;
import com.ten.tencloud.utils.UiUtils;

/**
 * 圆环
 * Created by lxq on 2018/3/27.
 */

public class ProgressRingView extends View {

    private Context mContext;

    private float mProgress;
    private float mThreshold = 80f;//阈值

    private int mStrokeWidth;//环形宽带
    private int mColor;//环形颜色
    private int mBackgroundColor;//背景颜色
    private Paint mPaint;
    private RectF mRectF;

    public ProgressRingView(Context context) {
        this(context, null);
    }

    public ProgressRingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressRingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProgressRingView, defStyleAttr, 0);



        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mRectF = new RectF();

        mStrokeWidth = UiUtils.dip2px(mContext, 4);
        mBackgroundColor = getResources().getColor(R.color.bg_2f3543);
        mColor = getResources().getColor(R.color.colorPrimary);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        mPaint.setStyle(Paint.Style.STROKE);
        //绘制底层的环
        mPaint.setColor(mBackgroundColor);
        mRectF.left = mStrokeWidth;
        mRectF.top = mStrokeWidth;
        mRectF.right = getWidth() - mStrokeWidth;
        mRectF.bottom = getHeight() - mStrokeWidth;
        canvas.drawArc(mRectF, -180, 360, false, mPaint);
        //绘制实际进度
        mPaint.setColor(mColor);
        canvas.drawArc(mRectF, -90, 360 * (mProgress / 100f), false, mPaint);
    }

    public void setProgress(int progress) {
        if (progress >= mThreshold) {
            mColor = getResources().getColor(R.color.text_color_ef9a9a);
        } else {
            mColor = getResources().getColor(R.color.colorPrimary);
        }
        ValueAnimator animator = ValueAnimator.ofInt(progress);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }

    public void setThreshold(float threshold) {
        mThreshold = threshold;
    }
}
