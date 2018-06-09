package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppContainerBean;
import com.ten.tencloud.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

public class AppK8sRegularDeployAddPortActivity extends BaseActivity {

    public static final int RESULT_CODE_ADD_PORT = 2222;

    @BindView(R.id.ll_layout)
    LinearLayout mLlLayout;
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

    private LayoutInflater mInflater;
    private OptionsPickerView mPvOptions;
    private int mServiceType;
    private List<String> mSelects;
    private int currentPostion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_k8s_regular_deploy_add_port);
        mInflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        initTitleBar(true, "添加端口", "确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
        initView();
        initData();
    }

    private void submit() {
        int childCount = mLlLayout.getChildCount();
        ArrayList<AppContainerBean.Port> ports = new ArrayList<>();
        for (int i = 0; i < childCount; i++) {
            View childView = mLlLayout.getChildAt(i);
            EditText etName = childView.findViewById(R.id.et_name);
            TextView tvProtocol = childView.findViewById(R.id.tv_protocol);
            EditText etPort = childView.findViewById(R.id.et_port);
            AppContainerBean.Port port = new AppContainerBean.Port();
            port.setName(etName.getText().toString().trim().toLowerCase());
            port.setProtocol(tvProtocol.getText().toString().trim().toUpperCase());
            port.setContainerPort(Integer.parseInt(etPort.getText().toString().trim()));
            ports.add(port);
        }
        Intent data = new Intent();
        data.putParcelableArrayListExtra("ports", ports);
        setResult(RESULT_CODE_ADD_PORT, data);
        finish();
    }

    private void initView() {
        mLlLayout.removeAllViews();
        UiUtils.addTransitionAnim(mLlLayout);//添加动效

        mPvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                mServiceType = options1 + 1;
                View view = views.get(currentPostion);
                TextView tvProtocol = view.findViewById(R.id.tv_protocol);

                tvProtocol.setText(mSelects.get(options1));
            }
        })
                .setTitleText("选择协议类型")
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
        mSelects.add("TCP");//2
        mSelects.add("UDP");//3
        mPvOptions.setPicker(mSelects);
    }
    //保存View
    private SparseArray<View> views = new SparseArray<>();
    //初始化Key，用于记录动态创建的View
    private int initKey = 1000;

    private void createLayoutView() {
        final int key = initKey;

        final View view = mInflater.inflate(R.layout.layout_app_k8s_add_port, mLlLayout, false);
        View removePort = view.findViewById(R.id.ll_remove_port);
        View tvProtocol = view.findViewById(R.id.tv_protocol);
        tvProtocol.setTag(initKey);
        tvProtocol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPostion = (int) v.getTag();
                mPvOptions.show();
            }
        });
        views.put(key, view);

        removePort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLlLayout.removeView(view);
            }
        });
        mLlLayout.addView(view);
        initKey++;

    }

    @OnClick({R.id.ll_add_port})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_add_port:
                createLayoutView();
                break;
        }
    }
}
