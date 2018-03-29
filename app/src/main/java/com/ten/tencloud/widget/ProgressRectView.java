package com.ten.tencloud.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.ten.tencloud.R;

/**
 * 圆环
 * Created by lxq on 2018/3/27.
 */

public class ProgressRectView extends View {

    private Context mContext;

    private int mProgress;
    private float mThreshold = 80f;//阈值

    private int mColor;//环形颜色
    private int mBackgroundColor;//背景颜色
    private Paint mPaint;
    private RectF mRectF;

    private long mDuration = 500;

    public ProgressRectView(Context context) {
        this(context, null);
    }

    public ProgressRectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mRectF = new RectF();
        mBackgroundColor = getResources().getColor(R.color.bg_2f3543);
        mColor = getResources().getColor(R.color.colorPrimary);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, width * 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        mPaint.setStyle(Paint.Style.FILL);
        //绘制底层
        mPaint.setColor(mBackgroundColor);
        mRectF.left = 0;
        mRectF.top = 0;
        mRectF.right = getWidth();
        mRectF.bottom = getHeight();
        canvas.drawRect(mRectF, mPaint);
//        //绘制实际进度
        mPaint.setColor(mColor);
        mRectF.top = getHeight() * (1 - mProgress / 100f);
        canvas.drawRect(mRectF, mPaint);
    }

    public void setProgress(int progress) {
        if (progress >= mThreshold) {
            mColor = getResources().getColor(R.color.text_color_ef9a9a);
        } else {
            mColor = getResources().getColor(R.color.colorPrimary);
        }
        ValueAnimator animator = ValueAnimator.ofInt(progress);
        animator.setDuration(mDuration);
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
