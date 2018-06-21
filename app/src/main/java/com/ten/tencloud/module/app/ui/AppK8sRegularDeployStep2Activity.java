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
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.bean.AppContainerBean;
import com.ten.tencloud.bean.AppServiceYAMLBean;
import com.ten.tencloud.bean.ContainersBean;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.module.app.contract.AppK8sDeployContract;
import com.ten.tencloud.module.app.presenter.AppK8sDeployPresenter;
import com.ten.tencloud.module.server.ui.ServerClusterListActivity;
import com.ten.tencloud.utils.UiUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 常规部署-2
 */
public class AppK8sRegularDeployStep2Activity extends BaseActivity implements AppK8sDeployContract.View {

    public static final int REQUEST_CODE_SELECT_NODE = 1000;
    public static final int REQUEST_CODE_ADD_CONTAINER = 1001;

    private AppBean mAppBean;
    private String mName;
    private int mMasterServerId = -1;

//    @BindView(R.id.tv_cluster)
//    TextView mTvCluster;
//    @BindView(R.id.tv_container)
//    TextView mTvContainer;
    @BindView(R.id.et_pod_count)
    EditText mEtPodCount;
    @BindView(R.id.et_pod_tag)
    EditText mEtPodTag;
    @BindView(R.id.ll_layout)
    LinearLayout mLlLayout;
    private LayoutInflater mInflater;

    //初始化Key，用于记录动态创建的View
    private int initKey = 1000;
    //保存数据
    private SparseArray<ContainersBean> datas = new SparseArray<>();
    //保存View
    private SparseArray<View> views = new SparseArray<>();

    private AppContainerBean mContainerBean = new AppContainerBean();
    private AppK8sDeployPresenter mAppK8sDeployPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_k8s_regular_deploy_step2);
        mInflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        initTitleBar(true, "常规部署", "下一步", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContainerBean == null || datas.size() == 0) {
                    showMessage("请添加容器");
                    return;
                }
                mContainerBean.setApp_id(mAppBean.getId());
                mContainerBean.setDeployment_name(mName);
                mContainerBean.setApp_name(mAppBean.getName());
                String count = mEtPodCount.getText().toString();
                mContainerBean.setReplica_num(Integer.parseInt(count));
                String podTag = mEtPodTag.getText().toString();

                if (TextUtils.isEmpty(podTag) || !podTag.contains("=")){
                    showMessage("Pod模板标签格式错误");
                    return;
                }
                if (!TextUtils.isEmpty(podTag)) {
                    String[] split = podTag.split(",");
                    Map<String, String> map = new HashMap<>();
                    for (String s : split) {
                        String[] str = s.split("=");
                        map.put(str[0], str[1]);
                    }
                    mContainerBean.setPod_label(map);
                }
                List<ContainersBean> containerBeans = new ArrayList<>();
                for (int i = 1000; i < initKey; i++) {
                    ContainersBean appContainerBean = datas.get(i);

                    View childView = views.get(i);
                    EditText etName = childView.findViewById(R.id.et_name);
                    TextView tvImage = childView.findViewById(R.id.tv_image);
                    TextView tvPort = childView.findViewById(R.id.tv_port);

                    String name = etName.getText().toString();
                    String image = tvImage.getText().toString();

                    if (TextUtils.isEmpty(name)) {
                        showMessage("请输入名称");
                        return;
                    }
                    if (TextUtils.isEmpty(image)) {
                        showMessage("请选择镜像");
                        return;
                    }
                    if (TextUtils.isEmpty(tvPort.getText().toString())) {
                        showMessage("请添加端口");
                        return;
                    }
                    appContainerBean.name = name;

                    containerBeans.add(appContainerBean);
                }
                mContainerBean.containers = containerBeans;
                mAppK8sDeployPresenter.generateYAML(mContainerBean);
            }
        });
        mAppBean = getIntent().getParcelableExtra("appBean");
        mName = getIntent().getStringExtra("name");
        mMasterServerId = getIntent().getIntExtra("masterServerId", -1);
        mAppK8sDeployPresenter = new AppK8sDeployPresenter();
        mAppK8sDeployPresenter.attachView(this);
        UiUtils.addTransitionAnim(mLlLayout);//添加动效

    }

    @OnClick({R.id.ll_add_container})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_add_container: {
//                Intent intent = new Intent(this, AppK8sRegularDeployAddContainerActivity.class);
//                startActivityForResult(intent, REQUEST_CODE_ADD_CONTAINER);
                createLayoutView();

                break;
            }
//            case R.id.ll_select_node: {
//                // TODO: 2018/5/8 选择集群
////                Intent intent = new Intent(this, AppK8sRegularDeployNodeTypeActivity.class);
//                Intent intent = new Intent(this, ServerClusterListActivity.class);
//                intent.putExtra("select", true);
//                startActivityForResult(intent, REQUEST_CODE_SELECT_NODE);
//                break;
//            }
        }
    }

    private void createLayoutView() {
        final int key = initKey;
        ContainersBean value = new ContainersBean();
        datas.put(key, value);
        final View view = mInflater.inflate(R.layout.layout_app_k8s_add_container, mLlLayout, false);
        views.put(key, view);
        View removeContainer = view.findViewById(R.id.ll_remove_container);
        View addPort = view.findViewById(R.id.ll_add_port);
        View btnImage = view.findViewById(R.id.btn_image);

        // TODO: 2018/5/9  暂时移除删除的功能
        removeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLlLayout.removeView(view);
                views.remove(key);
                datas.remove(key);
                initKey -- ;
            }
        });
        addPort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//端口
                Intent intent = new Intent(mContext, AppK8sRegularDeployAddPortActivity.class);
                startActivityForResult(intent, key);
            }
        });
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//镜像
                Intent intent = new Intent(mContext, AppIncludeImageActivity.class);
                intent.putExtra("type", AppIncludeImageActivity.TYPE_ADD_CONTAINER);
                startActivityForResult(intent, key);
            }
        });
        mLlLayout.addView(view);
        initKey++;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == Constants.ACTIVITY_RESULT_CODE_FINISH) {
//            if (requestCode == REQUEST_CODE_SELECT_NODE) {
//                mMasterServerId = data.getIntExtra("master_server_id", -1);
//                String clusterName = data.getStringExtra("clusterName");
//                mTvCluster.setText(clusterName);
//            }
//            else
// if (requestCode == REQUEST_CODE_ADD_CONTAINER) {
//                mContainerBean = data.getParcelableExtra("container");
//                if (mContainerBean != null) {
////                    mTvContainer.setText(mContainerBean.getContainer_name());
//                }
//            }

//        }
        if (resultCode == AppIncludeImageActivity.RESULT_CODE_ADD_CONTAINER) {
            View view = views.get(requestCode);
            TextView tvImage = view.findViewById(R.id.tv_image);
            String imageName = data.getStringExtra("imageName");
            String imageVersion = data.getStringExtra("imageVersion");
            datas.get(requestCode).image = imageName + ":" + imageVersion;
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
            datas.get(requestCode).ports = ports;
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
        finish();
    }
}
