package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

public class APPServiceCreateStep2Activity extends BaseActivity {

    @BindView(R.id.tv_service_type)
    TextView mTvSourceType;
    @BindView(R.id.tv_pod)
    TextView mTvPodLabel;

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

    private int mSourceType;
    private int mServiceType;
    private AppBean mAppBean;
    private OptionsPickerView mPvOptions;
    private List<String> mSelects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_appservice_create_step2);

        mAppBean = getIntent().getParcelableExtra("appBean");
        //服务来源
        mSourceType = getIntent().getIntExtra("sourceType", 0);

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
        mPvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                mServiceType = options1;
                mTvSourceType.setText(mSelects.get(options1));
            }
        })
                .setTitleText("选择方式")
                .setTitleColor(mTextColor899ab6)//标题文字颜色
                .setSubmitColor(mColorPrimary)//确定按钮文字颜色
                .setCancelColor(mColorPrimary)//取消按钮文字颜色
                .setTitleBgColor(mDefaultBg)//标题背景颜色 Night mode
                .setBgColor(mDefaultBg)//滚轮背景颜色 Night mode
                .setTextColorCenter(mTextColor899ab6)
                .setTextColorOut(mTextColor556278)
                .setContentTextSize(14)
                .setTitleSize(14)
                .setSubCalSize(14)
                .setDividerColor(mLineColor2f3543)
                .build();
    }

    private void initData() {
        mSelects = new ArrayList<>();
        // 1 集群内访问，2. 集群内外部可访问，3. 负载均衡器
        mSelects.add("仅集群内访问");//1
        mSelects.add("集群内和外部可访问");//2
        mSelects.add("通过负载均衡器访问");//3
        mPvOptions.setPicker(mSelects);
    }

    private void next() {
        Intent intent = new Intent();
        intent.putExtra("sourceType", mSourceType);
        intent.putExtra("serviceType", mServiceType);
        intent.putExtra("appBean", mAppBean);
        startActivity(intent);
    }

    @OnClick({R.id.ll_select_type, R.id.ll_select_pod})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_select_type:
                mPvOptions.show();
                break;
            case R.id.ll_select_pod:
                Intent intent = new Intent(this, APPServiceCreatePodLabelActivity.class);

                startActivityForResult(intent, 0);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
// TODO: 2018/5/14
    }
}
