package com.ten.tencloud.module.app.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.module.app.model.AppMakeModel;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class AppMakeImageStep2Activity extends BaseActivity {

    @BindView(R.id.et_code)
    EditText mEtCode;
    private AppMakeModel mAppMakeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_make_image_step2);
        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {
        mAppMakeModel = new AppMakeModel(new AppMakeModel.OnAppMakeListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(String message) {

            }

            @Override
            public void onMessage(String text) {

            }
        });
    }

    @OnClick({R.id.btn_start})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
//                String fileName = "dockerfile_" + System.currentTimeMillis();
//                FileUtils.writeTxtToFile(mEtCode.getText().toString().trim(), Constants.TEMP_DIR, fileName);
//                showMessage("写入成功");
                startMake();
                break;
        }
    }

    private void startMake() {
        Map<String, String> map = new HashMap<>();
        map.put("image_name", "test");
        map.put("version", "1.01");
        map.put("repos_https_url", "http://");
        map.put("branch_name", "");
        map.put("app_id", "");
        map.put("app_name", "name");
        map.put("dockerfile", "dockerfile");
        String json = TenApp.getInstance().getGsonInstance().toJson(map);
        mAppMakeModel.connect();
        mAppMakeModel.send(json);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showMessage("正在构建");
            }
        });
    }


}
