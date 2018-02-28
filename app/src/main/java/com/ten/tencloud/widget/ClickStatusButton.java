package com.ten.tencloud.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by lxq on 2018/2/27.
 */

public class ClickStatusButton extends android.support.v7.widget.AppCompatButton {
    public ClickStatusButton(Context context) {
        super(context);
    }

    public ClickStatusButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClickStatusButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
