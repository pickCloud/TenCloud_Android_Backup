package com.ten.tencloud.module.app.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class AppK8sRegularDeployNodeTypeActivity extends BaseActivity {

    //选择节点
    public static final int TYPE_NODE_SELECT = 0;
    //特定节点
    public static final int TYPE_NODE_SPECIFIC = 1;

    @BindView(R.id.iv_select)
    ImageView mIvSelect;
    @BindView(R.id.iv_specific)
    ImageView mIvSpecific;

    private int type = TYPE_NODE_SELECT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_k8s_regular_deploy_node_type);
        initTitleBar(true, "设置节点", "下一步", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
        initView();
    }

    private void initView() {
        mIvSelect.setVisibility(View.INVISIBLE);
        mIvSpecific.setVisibility(View.VISIBLE);
        type = TYPE_NODE_SPECIFIC;
    }

    private void next() {
        switch (type) {
            case TYPE_NODE_SELECT: {

                break;
            }
            case TYPE_NODE_SPECIFIC: {

                break;
            }
        }
    }

    @OnClick({R.id.cl_select, R.id.cl_specific})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cl_select:
                showMessage("暂未实现该方式");
//                mIvSelect.setVisibility(View.VISIBLE);
//                mIvSpecific.setVisibility(View.INVISIBLE);
//                type = TYPE_NODE_SELECT;
                break;
            case R.id.cl_specific:
                mIvSelect.setVisibility(View.INVISIBLE);
                mIvSpecific.setVisibility(View.VISIBLE);
                type = TYPE_NODE_SPECIFIC;
                break;
        }
    }
}
