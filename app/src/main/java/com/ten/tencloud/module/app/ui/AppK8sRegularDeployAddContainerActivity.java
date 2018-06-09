package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppContainerBean;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.utils.UiUtils;

import java.util.ArrayList;

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
                submit();
            }
        });
        initView();
        initData();
    }

    private void submit() {
        // TODO: 2018/5/9 目前只有一个容器
        View view = views.get(1000);
        EditText etName = view.findViewById(R.id.et_name);
        datas.get(1000).setContainer_name(etName.getText().toString().toLowerCase());
        Intent data = new Intent();
        data.putExtra("container",datas.get(1000));
        setResult(Constants.ACTIVITY_RESULT_CODE_FINISH, data);
        finish();
    }

    private void initView() {
        UiUtils.addTransitionAnim(mLlLayout);//添加动效
        createLayoutView();

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

        // TODO: 2018/5/9  暂时移除删除的功能
//        removeContainer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mLlLayout.removeView(view);
//                views.remove(key);
//                datas.remove(key);
//            }
//        });
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
            datas.get(requestCode).setImage_name(imageName + ":" + imageVersion);
            tvImage.setText(imageName + ":" + imageVersion);
        }
        if (resultCode == AppK8sRegularDeployAddPortActivity.RESULT_CODE_ADD_PORT) {
            ArrayList<AppContainerBean.Port> ports = data.getParcelableArrayListExtra("ports");
            View view = views.get(requestCode);
            TextView tvPort = view.findViewById(R.id.tv_port);
            String port = "";
            for (int i = 0; i < ports.size(); i++) {
                port = port + "," + ports.get(i).getName();
            }
            port = port.replaceFirst(",", "");
            if (TextUtils.isEmpty(port)) {
                return;
            }
            tvPort.setText(port);
            datas.get(requestCode).setPorts(ports);
        }
    }
}
