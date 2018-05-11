package com.ten.tencloud.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.ten.tencloud.R;
import com.ten.tencloud.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lxq on 2017/11/24.
 */

public class ServerHistoryTimeDialog extends Dialog {

    private final static long DEFALUT_START_MIN = 1420041600;

    private Context context;
    private long startTime;
    private long endTime;

    private boolean tagStart = true;//标记是谁触发日期选择器

    @BindView(R.id.tv_start_time)
    TextView mTvStartTime;
    @BindView(R.id.tv_end_time)
    TextView mTvEndTime;
    @BindView(R.id.view)
    View mView;

    private TimePickerView mPvStartTime;
    private TimePickerView mPvEndTime;

    //记录截止时间的最小值,不得低于开始时间
    private long endMin;

    private final static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private TimePickerView.OnTimeSelectListener mTimeSelectListener;

    public ServerHistoryTimeDialog(@NonNull Context context) {
        super(context, R.style.RightDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉头
        Window window = getWindow();
        window.setGravity(Gravity.RIGHT);
        window.setContentView(R.layout.dialog_server_history);
        ButterKnife.bind(this);
        mTvStartTime.setText(DateUtils.timestampToString(startTime, TIME_FORMAT));
        mTvEndTime.setText(DateUtils.timestampToString(endTime, TIME_FORMAT));
        mTimeSelectListener = new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                if (tagStart) {
                    startTime = date.getTime() / 1000;
                    mTvStartTime.setText(DateUtils.timestampToString(startTime, TIME_FORMAT));
                    endMin = startTime;
                } else {
                    endTime = date.getTime() / 1000;
                    mTvEndTime.setText(DateUtils.timestampToString(endTime, TIME_FORMAT));
                }
            }
        };
        mPvStartTime = new TimePickerView.Builder(context, mTimeSelectListener)
                .setRangDate(handTime(DEFALUT_START_MIN), handTime(System.currentTimeMillis() / 1000))
                .setDecorView((ViewGroup) window.getDecorView())
                .isCenterLabel(false)
                .build();
        endMin = startTime;
        mView.setAlpha(0.5f);
        window.setBackgroundDrawable(new BitmapDrawable());
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
    }

    @OnClick({R.id.btn_ok, R.id.ll_start_time, R.id.ll_end_time, R.id.view})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                if (onOkListener != null) {
                    onOkListener.onOk(startTime, endTime);
                }
                dismiss();
                break;
            case R.id.ll_start_time:
                mPvStartTime.setDate(handTime(startTime));
                tagStart = true;
                mPvStartTime.show();
                break;
            case R.id.ll_end_time:
                buildEndPicker();
                mPvEndTime.setDate(handTime(endTime));
                tagStart = false;
                mPvEndTime.show();
                break;
            case R.id.view:
                cancel();
                break;
        }
    }

    private void buildEndPicker() {
        mPvEndTime = null;
        mPvEndTime = new TimePickerView.Builder(context, mTimeSelectListener)
                .setRangDate(handTime(endMin), handTime(System.currentTimeMillis() / 1000))
                .setDecorView((ViewGroup) getWindow().getDecorView())
                .isCenterLabel(false)
                .build();
    }


    public interface OnOkListener {
        void onOk(long startTime, long endTime);
    }

    private OnOkListener onOkListener;

    public void setOnOkListener(OnOkListener onOkListener) {
        this.onOkListener = onOkListener;
    }

    public void setTime(long startTime, long endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private Calendar handTime(long time) {
        String string = DateUtils.timestampToString(time, TIME_FORMAT);
        return DateUtils.strToCalendar(string, TIME_FORMAT);
    }


}
