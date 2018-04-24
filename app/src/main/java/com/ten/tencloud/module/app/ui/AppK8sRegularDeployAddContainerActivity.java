package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppContainerBean;
import com.ten.tencloud.utils.UiUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class AppK8sRegularDeployAddContainerActivity extends BaseActivity {

    @BindView(R.id.ll_layout)
    LinearLayout mLlLayout;
    private LayoutInflater mInflater;

    //初始化Key，用于记录动态创建的View
    private int initKey = 1000;

    //保存数据
    private SparseArray<AppContainerBean> datas = new SparseArray<>();
    //保存View
    private SparseArray<View> views = new SparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_k8s_regular_deploy_add_container);
        mInflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        initTitleBar(true, "添加容器", "确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        initView();
        initData();
    }

    private void initView() {
        UiUtils.addTransitionAnim(mLlLayout);//添加动效
    }

    private void initData() {

    }

    private void createLayoutView() {
        final int key = initKey;
        AppContainerBean value = new AppContainerBean();
        datas.put(key, value);
        final View view = mInflater.inflate(R.layout.layout_app_k8s_add_container, mLlLayout, false);
        views.put(key, view);
        View removeContainer = view.findViewById(R.id.ll_remove_container);
        View addPort = view.findViewById(R.id.ll_add_port);
        View btnImage = view.findViewById(R.id.btn_image);
        removeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLlLayout.removeView(view);
                views.remove(key);
                datas.remove(key);
            }
        });
        addPort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AppK8sRegularDeployAddPortActivity.class);
                startActivityForResult(intent, key);
            }
        });
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AppIncludeImageActivity.class);
                intent.putExtra("type", AppIncludeImageActivity.TYPE_ADD_CONTAINER);
                startActivityForResult(intent, key);
            }
        });
        mLlLayout.addView(view);
        initKey++;
    }

    @OnClick({R.id.ll_add_container})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_add_container:
                createLayoutView();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == AppIncludeImageActivity.RESULT_CODE_ADD_CONTAINER) {
            View view = views.get(requestCode);
            TextView tvImage = view.findViewById(R.id.tv_image);
            String imageName = data.getStringExtra("imageName");
            String imageVersion = data.getStringExtra("imageVersion");
            tvImage.setText(imageName + ": " + imageVersion);
        }
    }
}
