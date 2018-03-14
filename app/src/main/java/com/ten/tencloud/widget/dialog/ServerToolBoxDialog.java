package com.ten.tencloud.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ten.tencloud.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lxq on 2017/11/24.
 */

public class ServerToolBoxDialog extends Dialog {

    private Context context;

    public ServerToolBoxDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉头
        Window window = getWindow();
        window.setContentView(R.layout.dialog_server_toolbox);
        ButterKnife.bind(this);
        window.setBackgroundDrawable(new BitmapDrawable());
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
    }

    @OnClick({R.id.btn_close, R.id.btn_tool1, R.id.btn_tool2, R.id.btn_tool3,
            R.id.btn_tool4, R.id.btn_tool5, R.id.btn_tool6, R.id.btn_tool7})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_close: {
                cancel();
                break;
            }
            case R.id.btn_tool1: {

                break;
            }
            case R.id.btn_tool2: {

                break;
            }
            case R.id.btn_tool3: {

                break;
            }
            case R.id.btn_tool4: {

                break;
            }
            case R.id.btn_tool5: {

                break;
            }
            case R.id.btn_tool6: {

                break;
            }
            case R.id.btn_tool7: {

                break;
            }

        }
    }
}
