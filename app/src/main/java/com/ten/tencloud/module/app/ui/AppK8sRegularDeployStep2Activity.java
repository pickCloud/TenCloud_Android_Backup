package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.module.server.ui.ServerClusterListActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class AppK8sRegularDeployStep2Activity extends BaseActivity {

    public static final int REQUEST_CODE_SELECT_NODE = 1000;
    public static final int REQUEST_CODE_ADD_CONTAINER = 1001;

    private AppBean mAppBean;
    private String mName;
    private int mMasterServerId = -1;

    @BindView(R.id.tv_cluster)
    TextView mTvCluster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_k8s_regular_deploy_step2);
        initTitleBar(true, "kubernetes常规部署", "下一步", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mAppBean = getIntent().getParcelableExtra("appBean");
        mName = getIntent().getStringExtra("name");
    }

    @OnClick({R.id.ll_add_container, R.id.ll_select_node, R.id.tv_yaml})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_add_container: {
                Intent intent = new Intent(this, AppK8sRegularDeployAddContainerActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_CONTAINER);
                break;
            }
            case R.id.ll_select_node: {
                // TODO: 2018/5/8 选择集群
//                Intent intent = new Intent(this, AppK8sRegularDeployNodeTypeActivity.class);
                Intent intent = new Intent(this, ServerClusterListActivity.class);
                intent.putExtra("select", true);
                startActivityForResult(intent, REQUEST_CODE_SELECT_NODE);
                break;
            }
            case R.id.tv_yaml: {
                Intent intent = new Intent(this, AppK8sRegularDeployStep3Activity.class);
                intent.putExtra("appBean", mAppBean);
                intent.putExtra("name", mName);
                startActivity(intent);
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Constants.ACTIVITY_RESULT_CODE_FINISH) {
            if (requestCode == REQUEST_CODE_SELECT_NODE) {
                mMasterServerId = data.getIntExtra("master_server_id", -1);
                String clusterName = data.getStringExtra("clusterName");
                mTvCluster.setText(clusterName);
            }

        }

    }
}
