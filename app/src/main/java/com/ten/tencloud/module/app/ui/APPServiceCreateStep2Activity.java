package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.constants.IntentKey;
import com.ten.tencloud.widget.StatusSelectPopView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

public class APPServiceCreateStep2Activity extends BaseActivity {

    //    @BindView(R.id.tv_service_type)
//    TextView mTvSourceType;
    //    @BindView(R.id.tv_pod)
//    TextView mTvPodLabel;
//    @BindView(R.id.et_pod_tag)
//    EditText metPodTag;

    @BindColor(R.color.text_color_899ab6)
    int mTextColor899ab6;
    @BindColor(R.color.text_color_556278)
    int mTextColor556278;
    @BindColor(R.color.colorPrimary)
    int mColorPrimary;
    @BindColor(R.color.default_bg)
    int mDefaultBg;
    @BindColor(R.color.line_color_2f3543)
    int mLineColor2f3543;
    @BindView(R.id.spv_status)
    StatusSelectPopView mSpvStatus;
    @BindView(R.id.tv_see_example)
    TextView mTvSeeExample;
    @BindView(R.id.ll_one)
    LinearLayout mLlOne;
    @BindView(R.id.et_ip)
    EditText mEtIp;
    @BindView(R.id.et_port)
    EditText mEtPort;
    @BindView(R.id.et_namespace)
    EditText mEtNamespace;
    @BindView(R.id.et_external_name)
    EditText mEtExternalName;
    @BindView(R.id.ll_three)
    LinearLayout mLlThree;
    @BindView(R.id.ll_two)
    LinearLayout mLlTwo;

    private AppBean mAppBean;
    private int mPos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_appservice_create_step2);

        mAppBean = getIntent().getParcelableExtra(IntentKey.APP_ITEM);
        //服务来源
//        mSourceType = getIntent().getIntExtra("sourceType", 0);
//        mServiceName = getIntent().getStringExtra("serviceName");

        initTitleBar(true, "创建服务", "下一步", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
        initView();
        initData();
    }

    private void initView() {

        List<String> statusTitles = new ArrayList<>();
        statusTitles.add("集群内应用，通过标签选择");
        statusTitles.add("集群外，通过映射服务");
        statusTitles.add("集群外，通过别名映射服务");

        mSpvStatus.initData(statusTitles);
        mSpvStatus.setOnSelectListener(new StatusSelectPopView.OnSelectListener() {
            @Override
            public void onSelect(int pos) {
                mPos = pos;
                switch (pos) {
                    case 0:
                        mLlOne.setVisibility(View.VISIBLE);
                        mLlTwo.setVisibility(View.GONE);
                        mLlThree.setVisibility(View.GONE);
                        break;
                    case 1:
                        mLlOne.setVisibility(View.GONE);
                        mLlTwo.setVisibility(View.VISIBLE);
                        mLlThree.setVisibility(View.GONE);
                        break;
                    case 2:
                        mLlOne.setVisibility(View.GONE);
                        mLlTwo.setVisibility(View.GONE);
                        mLlThree.setVisibility(View.VISIBLE);
                        break;
                }

            }
        });

    }

    private void initData() {

    }

    private void next() {
//        Intent intent = new Intent(this, APPServiceCreateStep3Activity.class);
//        intent.putExtra("sourceType", mSourceType);
//        intent.putExtra("serviceType", mServiceType);
//        intent.putExtra("serviceName", mServiceName);
//        intent.putExtra("appBean", mAppBean);
//        intent.putExtra("podTag", metPodTag.getText().toString());
//        startActivity(intent);

        switch (mPos){
            case 0:

                break;
            case 1:

                break;
            case 2:

                break;
        }

        Intent intent = new Intent(this, APPServiceCreateStep3Activity.class);
        startActivity(intent);

    }

    @OnClick({R.id.tv_yaml, R.id.tv_see_example})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_yaml:
                break;
            case R.id.tv_see_example:
                ActivityUtils.startActivity(AppServiceExampleActivity.class);
                break;

        }
    }

}
