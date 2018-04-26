package com.ten.tencloud.module.server.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.K8sNodeBean;
import com.ten.tencloud.utils.DateUtils;

import java.util.Map;

import butterknife.BindView;

public class ServerClusterNodeActivity extends BaseActivity {

    //基本信息
    @BindView(R.id.tv_node_name)
    TextView mTvNodeName;
    @BindView(R.id.tv_node_run_time)
    TextView mTvNodeRunTime;
    @BindView(R.id.tv_node_create_time)
    TextView mTvNodeCreateTime;

    //节点标签
    @BindView(R.id.tv_labels)
    TextView mTvNodeLabels;
    @BindView(R.id.ll_node_role)
    LinearLayout mLlNodeRole;
    @BindView(R.id.tv_node_role)
    TextView mTvNodeRole;

    //节点状态
    @BindView(R.id.tv_private_ip)
    TextView mTvPrivateIp;
    @BindView(R.id.tv_hostname)
    TextView mTvHostName;
    @BindView(R.id.tv_node_status)
    TextView mTvNodeStatus;
    @BindView(R.id.tv_capacity_cpu)
    TextView mTvCapacityCpu;
    @BindView(R.id.tv_capacity_memory)
    TextView mTvCapacityMemory;
    @BindView(R.id.tv_capacity_pods)
    TextView mTvCapacityPods;
    @BindView(R.id.tv_allocatable_cpu)
    TextView mTvAllocatableCpu;
    @BindView(R.id.tv_allocatable_memory)
    TextView mTvAllocatableMemory;
    @BindView(R.id.tv_allocatable_pods)
    TextView mTvAllocatablePods;

    @BindView(R.id.tv_os_kernel_version)
    TextView mTvOsKernelVersion;
    @BindView(R.id.tv_os_k8s_version)
    TextView mTvOsK8sVersion;
    @BindView(R.id.tv_os_docker_version)
    TextView mTvOsDockerVersion;
    @BindView(R.id.tv_os_type)
    TextView mTvOsType;
    @BindView(R.id.tv_os_image)
    TextView mTvOsImage;
    @BindView(R.id.tv_os_architecture)
    TextView mTvOsArchitecture;
    @BindView(R.id.tv_os_k8s_proxy_version)
    TextView mTvOsK8sProxyVersion;


    private K8sNodeBean.ItemsBean mBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_server_cluster_node);
        initTitleBar(true, "节点信息");
        String json = getIntent().getStringExtra("json");
        mBean = TenApp.getInstance().getGsonInstance().fromJson(json, K8sNodeBean.ItemsBean.class);
        initView();
    }

    private void initView() {
        setBasicInfo();
        setLabelsInfo();
        setNodeStatusInfo();
    }

    /*
    基本信息
     */
    private void setBasicInfo() {
        mTvNodeName.setText(mBean.getMetadata().getName());
        for (K8sNodeBean.ItemsBean.StatusBean.ConditionsBean conditionsBean : mBean.getStatus().getConditions()) {
            if ("Ready".equalsIgnoreCase(conditionsBean.getType())) {
                long startTime = DateUtils.cstToStamp(conditionsBean.getLastTransitionTime());
                long endTime = DateUtils.cstToStamp(conditionsBean.getLastHeartbeatTime());
                String between = DateUtils.between(startTime, endTime);
                mTvNodeRunTime.setText(between);
            }
        }
        String creationTimestamp = mBean.getMetadata().getCreationTimestamp();
        Long aLong = DateUtils.cstToStamp(creationTimestamp);
        mTvNodeCreateTime.setText(DateUtils.timestampToString(aLong / 1000, "yyyy-MM-dd HH:mm:ss"));
    }

    /*
    节点标签
     */
    private void setLabelsInfo() {
        Map<String, String> labels = mBean.getMetadata().getLabels();
        String labelStr = "";
        String roleStr = "";
        for (Map.Entry<String, String> entry : labels.entrySet()) {
            String key = entry.getKey().toString();
            if (key.contains("role")) {
                String s = key.split("/")[1];
                roleStr = roleStr + "/" + s;
            } else {
                String value = entry.getValue().toString();
                labelStr = labelStr + ("\n" + key + "=" + value);
            }
        }
        labelStr = labelStr.replaceFirst("\n", "");
        roleStr = roleStr.replaceFirst("/", "");
        mTvNodeLabels.setText(labelStr);
        mLlNodeRole.setVisibility(TextUtils.isEmpty(roleStr) ? View.GONE : View.VISIBLE);
        mTvNodeRole.setText(roleStr);
    }

    /*
    节点状态
     */
    private void setNodeStatusInfo() {
        for (K8sNodeBean.ItemsBean.StatusBean.AddressesBean addressesBean : mBean.getStatus().getAddresses()) {
            if ("InternalIP".equalsIgnoreCase(addressesBean.getType())) {
                mTvPrivateIp.setText(addressesBean.getAddress());
            }
            if ("Hostname".equalsIgnoreCase(addressesBean.getType())) {
                mTvHostName.setText(addressesBean.getAddress());
            }
        }
        //节点状态
        String nodeStatus = "";
        for (K8sNodeBean.ItemsBean.StatusBean.ConditionsBean conditionsBean : mBean.getStatus().getConditions()) {
            if ("True".equalsIgnoreCase(conditionsBean.getStatus())) {
                String status = "";
                String type = conditionsBean.getType();
                if ("OutOfDisk".equalsIgnoreCase(type)) {
                    status = "无可用空间";
                } else if ("Ready".equalsIgnoreCase(type)) {
                    status = "准备就绪";
                } else if ("MemoryPressure".equalsIgnoreCase(type)) {
                    status = "内存过低";
                } else if ("DiskPressure".equalsIgnoreCase(type)) {
                    status = "磁盘容量低";
                }
                nodeStatus = nodeStatus + "/" + status;
            }
        }
        nodeStatus = nodeStatus.replaceFirst("/", "");
        mTvNodeStatus.setText(nodeStatus);

        mTvCapacityCpu.setText(mBean.getStatus().getCapacity().getCpu());
        mTvCapacityMemory.setText(mBean.getStatus().getCapacity().getMemory());
        mTvCapacityPods.setText(mBean.getStatus().getCapacity().getPods());
        mTvAllocatableCpu.setText(mBean.getStatus().getAllocatable().getCpu());
        mTvAllocatableMemory.setText(mBean.getStatus().getAllocatable().getMemory());
        mTvAllocatablePods.setText(mBean.getStatus().getAllocatable().getPods());

        mTvOsKernelVersion.setText(mBean.getStatus().getNodeInfo().getKernelVersion());
        mTvOsK8sVersion.setText(mBean.getStatus().getNodeInfo().getKubeletVersion());
        mTvOsDockerVersion.setText(mBean.getStatus().getNodeInfo().getContainerRuntimeVersion());
        mTvOsType.setText(mBean.getStatus().getNodeInfo().getOperatingSystem());
        mTvOsImage.setText(mBean.getStatus().getNodeInfo().getOsImage());
        mTvOsArchitecture.setText(mBean.getStatus().getNodeInfo().getArchitecture());
        mTvOsK8sProxyVersion.setText(mBean.getStatus().getNodeInfo().getKubeProxyVersion());
    }
}
