package com.ten.tencloud.module.image.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

public class ImageAddSelectFileActivity extends BaseActivity {

    @BindView(R.id.ll_local)
    View mLlLocal;
    @BindView(R.id.ll_cloud)
    View mLlCloud;
    @BindView(R.id.tv_select_type)
    TextView mTvSelectType;
    @BindColor(R.color.text_color_899ab6)
    int mTextColor899ab6;
    @BindColor(R.color.text_color_556278)
    int mTextColor556278;
    @BindColor(R.color.colorPrimary)
    int mColorPrimary;
    @BindColor(R.color.default_bg) int mDefaultBg;
    private List<String> mSelects;
    private OptionsPickerView mPvOptions;

    private int option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_image_add_select_file);
        initTitleBar(true, "选择文件来源", "提交", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        initView();
        initData();
    }

    private void initView() {
        mPvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                option = options1;
                mTvSelectType.setText(mSelects.get(options1));
                if (option == 0) {
                    mLlLocal.setVisibility(View.VISIBLE);
                    mLlCloud.setVisibility(View.GONE);
                } else {
                    mLlLocal.setVisibility(View.GONE);
                    mLlCloud.setVisibility(View.VISIBLE);
                }
            }
        })
                .setTitleText("选择方式")
                .setTitleColor(mTextColor899ab6)//标题文字颜色
                .setSubmitColor(mColorPrimary)//确定按钮文字颜色
                .setCancelColor(mColorPrimary)//取消按钮文字颜色
                .setTitleBgColor(mDefaultBg)//标题背景颜色 Night mode
                .setBgColor(mDefaultBg)//滚轮背景颜色 Night mode
                .setTextColorCenter(mTextColor899ab6)
                .setTextColorOut(mTextColor899ab6)
                .setDividerColor(mTextColor899ab6)
                .build();
    }

    private void initData() {
        mSelects = new ArrayList<>();
        mSelects.add("从本地上传镜像到仓库");
        mSelects.add("从云端拉取镜像到仓库");
        mPvOptions.setPicker(mSelects);
    }

    @OnClick({R.id.ll_select_type})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_select_type:
                mPvOptions.setSelectOptions(option);
                mPvOptions.show();
                break;
        }
    }
}
