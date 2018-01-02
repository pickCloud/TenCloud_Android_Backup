package com.ten.tencloud.widget.dialog;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ten.tencloud.R;

/**
 * 选择图片时的底部弹窗
 * <p>
 * Created by lxq on 2018/1/2.
 */

public class PhotoSelectDialog extends BottomSheetDialog {

    private Context mContext;

    public PhotoSelectDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_select_photo, null);
        view.findViewById(R.id.ll_take).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBtnClickListener != null) {
                    mOnBtnClickListener.onTake();
                    dismiss();
                }
            }
        });
        view.findViewById(R.id.ll_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBtnClickListener != null) {
                    mOnBtnClickListener.onGallery();
                    dismiss();
                }
            }
        });
        view.findViewById(R.id.ll_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setContentView(view);
        Window window = getWindow();
        window.setBackgroundDrawable(new BitmapDrawable());
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }

    private OnBtnClickListener mOnBtnClickListener;

    public void setOnBtnClickListener(OnBtnClickListener onBtnClickListener) {
        mOnBtnClickListener = onBtnClickListener;
    }

    public interface OnBtnClickListener {
        void onTake();

        void onGallery();
    }
}
