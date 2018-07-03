package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.constants.IntentKey;
import com.ten.tencloud.module.app.contract.AppCheckNameContract;
import com.ten.tencloud.module.app.presenter.AppCheckNamePresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class APPServiceCreateStep1Activity extends BaseActivity implements AppCheckNameContract.View {
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_pod_tag)
    EditText mEtPodTag;

//    @BindView(R.id.et_name)
//    EditText mEtName;
//    @BindView(R.id.tv_source)
//    TextView mTvSource;
//    @BindColor(R.color.text_color_899ab6)
//    int mTextColor899ab6;
//    @BindColor(R.color.text_color_556278)
//    int mTextColor556278;
//    @BindColor(R.color.colorPrimary)
//    int mColorPrimary;
//    @BindColor(R.color.default_bg)
//    int mDefaultBg;
//    @BindColor(R.color.line_color_2f3543)
//    int mLineColor2f3543;

    //    private OptionsPickerView mPvOptions;
//    private ArrayList<String> mSelects;

    private int mSourceType = -1; //（1.内部服务，2.外部服务）
    private AppBean mAppBean;
    private AppCheckNamePresenter mCheckNamePresenter;
    private String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_appservice_create_step1);
        mAppBean = getIntent().getParcelableExtra(IntentKey.APP_ITEM);
        initTitleBar(true, "创建服务", "下一步", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });

        mCheckNamePresenter = new AppCheckNamePresenter();
        mCheckNamePresenter.attachView(this);

        initView();
        initData();

    }

    private void initView() {
//        mPvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
//            @Override
//            public void onOptionsSelect(int options1, int option2, int options3, View v) {
//                mSourceType = options1;
//                mTvSource.setText(mSelects.get(options1));
//            }
//        })
//                .setTitleText("选择方式")
//                .setTitleColor(mTextColor899ab6)//标题文字颜色
//                .setSubmitColor(mColorPrimary)//确定按钮文字颜色
//                .setCancelColor(mColorPrimary)//取消按钮文字颜色
//                .setTitleBgColor(mDefaultBg)//标题背景颜色 Night mode
//                .setBgColor(mDefaultBg)//滚轮背景颜色 Night mode
//                .setTextColorCenter(mTextColor899ab6)
//                .setTextColorOut(mTextColor556278)
//                .setContentTextSize(14)
//                .setTitleSize(14)
//                .setSubCalSize(14)
//                .setDividerColor(mLineColor2f3543)
//                .build();
    }

    private void initData() {
//        mSelects = new ArrayList<>();
//        mSelects.add("集群内服务");//1
//        mSelects.add("集群外服务");//2
//        mPvOptions.setPicker(mSelects);
    }

    //下一步
    private void next() {
//        if (mSourceType == -1) {
//            showMessage("请选择服务来源");
//            return;
//        }
        mName = mEtName.getText().toString();
        if (TextUtils.isEmpty(mName)) {
            showMessage("请输入服务名称");
            return;
        }
        String podTag = mEtPodTag.getText().toString();
        if (TextUtils.isEmpty(podTag)) {
            showMessage("请输入服务标签");
            return;
        }
        //验证名称
        mCheckNamePresenter.checkDeployName(mName, mAppBean.getId());
    }

//    @OnClick({R.id.ll_select_source /*, R.id.tv_yaml*/})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.ll_select_source:
////                mPvOptions.show();
//                break;
////            case R.id.tv_yaml:
////                // TODO: 2018/5/14
////                break;
//        }
//    }

    @Override
    public void checkResult() {
        Intent intent = new Intent(this, APPServiceCreateStep2Activity.class);
        intent.putExtra("sourceType", mSourceType + 1);
        intent.putExtra("appBean", mAppBean);
        intent.putExtra("serviceName", mName);
        startActivity(intent);
    }
}
