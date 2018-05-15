package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.bean.AppContainerBean;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.module.app.contract.AppK8sDeployContract;
import com.ten.tencloud.module.app.presenter.AppK8sDeployPresenter;
import com.ten.tencloud.module.server.ui.ServerClusterListActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class AppK8sRegularDeployStep2Activity extends BaseActivity implements AppK8sDeployContract.View {

    public static final int REQUEST_CODE_SELECT_NODE = 1000;
    public static final int REQUEST_CODE_ADD_CONTAINER = 1001;

    private AppBean mAppBean;
    private String mName;
    private int mMasterServerId = -1;

    @BindView(R.id.tv_cluster)
    TextView mTvCluster;
    @BindView(R.id.tv_container)
    TextView mTvContainer;
    @BindView(R.id.et_pod_count)
    EditText mEtPodCount;
    @BindView(R.id.et_pod_tag)
    EditText mEtPodTag;

    private AppContainerBean mContainerBean;
    private AppK8sDeployPresenter mAppK8sDeployPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_k8s_regular_deploy_step2);
        initTitleBar(true, "kubernetes常规部署", "下一步", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContainerBean == null) {
                    showMessage("请添加容器");
                    return;
                }
                mContainerBean.setApp_id(mAppBean.getId());
                mContainerBean.setDeployment_name(mName);
                mContainerBean.setApp_name(mAppBean.getName());
                String count = mEtPodCount.getText().toString();
                mContainerBean.setReplica_num(Integer.parseInt(count));
                String podTag = mEtPodTag.getText().toString();

                if (!TextUtils.isEmpty(podTag)) {
                    String[] split = podTag.split(",");
                    Map<String, String> map = new HashMap<>();
                    for (String s : split) {
                        String[] str = s.split("=");
                        map.put(str[0], str[1]);
                    }
                    mContainerBean.setPod_label(map);
                }
                mAppK8sDeployPresenter.generateYAML(mContainerBean);
            }
        });
        mAppBean = getIntent().getParcelableExtra("appBean");
        mName = getIntent().getStringExtra("name");
        mAppK8sDeployPresenter = new AppK8sDeployPresenter();
        mAppK8sDeployPresenter.attachView(this);
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
                if (mMasterServerId == -1) {
                    showMessage("请先选择集群");
                    return;
                }
                Intent intent = new Intent(this, AppK8sRegularDeployStep3Activity.class);
                intent.putExtra("appBean", mAppBean);
                intent.putExtra("serverId", mMasterServerId);
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
            } else if (requestCode == REQUEST_CODE_ADD_CONTAINER) {
                mContainerBean = data.getParcelableExtra("container");
                if (mContainerBean != null) {
                    mTvContainer.setText(mContainerBean.getContainer_name());
                }
            }
        }

    }

    @Override
    public void checkResult() {

    }

    @Override
    public void showYAML(String yaml) {
        if (mMasterServerId == -1) {
            showMessage("请选择集群");
            return;
        }
        Intent intent = new Intent(this, AppK8sRegularDeployStep3Activity.class);
        intent.putExtra("serverId", mMasterServerId);
        intent.putExtra("yaml", yaml);
        intent.putExtra("appBean", mAppBean);
        intent.putExtra("name", mName);
        startActivity(intent);
    }
}
