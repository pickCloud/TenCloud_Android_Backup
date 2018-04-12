package com.ten.tencloud.module.app.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.utils.FileUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class AppMakeImageStep2Activity extends BaseActivity {

    @BindView(R.id.et_code)
    EditText mEtCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_make_image_step2);
    }

    @OnClick({R.id.btn_start})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                String fileName = "dockerfile_" + System.currentTimeMillis();
                FileUtils.writeTxtToFile(mEtCode.getText().toString().trim(), Constants.TEMP_DIR, fileName);
                showMessage("写入成功");
                break;
        }
    }
}
