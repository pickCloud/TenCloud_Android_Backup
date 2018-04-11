package com.ten.tencloud.module.app.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class AppMakeImageStep1Activity extends BaseActivity {

    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_version)
    EditText mEtVersion;
    @BindView(R.id.tv_branch)
    TextView mTvBranch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_make_image_step1);
    }

    @OnClick({R.id.ll_select_branch})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_select_branch:

                break;
        }
    }
}
