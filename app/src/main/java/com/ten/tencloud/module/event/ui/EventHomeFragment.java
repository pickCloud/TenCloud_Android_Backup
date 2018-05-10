package com.ten.tencloud.module.event.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseFragment;
import com.ten.tencloud.bean.EventBean;
import com.ten.tencloud.module.event.adapter.RvEventAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lxq on 2018/4/16.
 */

public class EventHomeFragment extends BaseFragment {

    @BindView(R.id.empty_view)
    View mEmptyView;
    @BindView(R.id.rv_docker_content)
    RecyclerView mRvDocker;
    @BindView(R.id.rv_operation_content)
    RecyclerView mRvOperation;
    @BindView(R.id.rv_system_content)
    RecyclerView mRvSystem;
    private RvEventAdapter mDockerAdapter;
    private RvEventAdapter mOperationAdapter;
    private RvEventAdapter mSystemAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return createView(inflater, container, R.layout.fragment_event_home);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView();
        initData();
    }

    private void initView() {
        mRvDocker.setLayoutManager(new LinearLayoutManager(mActivity) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRvOperation.setLayoutManager(new LinearLayoutManager(mActivity) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRvSystem.setLayoutManager(new LinearLayoutManager(mActivity) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mDockerAdapter = new RvEventAdapter(mActivity);
        mRvDocker.setAdapter(mDockerAdapter);
        mOperationAdapter = new RvEventAdapter(mActivity);
        mRvOperation.setAdapter(mOperationAdapter);
        mSystemAdapter = new RvEventAdapter(mActivity);
        mRvSystem.setAdapter(mSystemAdapter);
    }

    private void initData() {
        initDockerData();
        initOperationData();
        initSystemData();
    }

    private void initDockerData() {
        List<EventBean> data = new ArrayList<>();
        {
            LinkedHashMap<String, String> eventTime = new LinkedHashMap<>();
            eventTime.put("第一次发生时间", "2018-04-23 08:54:01");
            eventTime.put("最后一次发生时间", "2018-04-23 08:54:01");
            eventTime.put("发生次数", "1");
            LinkedHashMap<String, String> effectObj = new LinkedHashMap<>();
            effectObj.put("关联应用", "Dao-1024");
            effectObj.put("Kind", "Deployment");
            effectObj.put("Name", "kubernetes-bootcamp");
            effectObj.put("UID", "ddf9100f-46d3-11e8-abf6-0242ac11000b");
            LinkedHashMap<String, String> des = new LinkedHashMap<>();
            des.put("", "Scaled up replica set kubernetes-bootcamp-5dbf48f7d4 to 1");
            data.add(new EventBean().setName("ScalingReplicaSet")
                    .setLevel(0)
                    .setSource("Component:  deployment-controller")
                    .setEventTime(eventTime)
                    .setEffectObj(effectObj)
                    .setDes(des));
        }
        {
            LinkedHashMap<String, String> eventTime = new LinkedHashMap<>();
            eventTime.put("", "2018-04-23 15:30:00");
            LinkedHashMap<String, String> effectObj = new LinkedHashMap<>();
            effectObj.put("关联应用", "Dao-1024");
            effectObj.put("Kind", "ReplicaSet");
            effectObj.put("Name", "kubernetes-bootcamp-5dbf48f7d4");
            effectObj.put("UID", "ddfb0097-46d3-11e8-abf6-0242ac11000b");
            LinkedHashMap<String, String> des = new LinkedHashMap<>();
            des.put("", "Created pod: kubernetes-bootcamp-5dbf48f7d4-4kbkr.");
            data.add(new EventBean().setName("SuccessfulCreate")
                    .setLevel(0)
                    .setSource("Component:  replicaset-controller")
                    .setEventTime(eventTime)
                    .setEffectObj(effectObj)
                    .setDes(des));
        }
        mDockerAdapter.setDatas(data);
    }

    private void initOperationData() {
        List<EventBean> data = new ArrayList<>();
        {
            LinkedHashMap<String, String> eventTime = new LinkedHashMap<>();
            eventTime.put("", "2018-04-23 17:17:01");
            LinkedHashMap<String, String> effectObj = new LinkedHashMap<>();
            effectObj.put("", "主机, aws, 58.13.20.191");
            LinkedHashMap<String, String> des = new LinkedHashMap<>();
            des.put("", "关机成功");
            data.add(new EventBean().setName("关机")
                    .setLevel(1)
                    .setSource("13030115222, 林茹茹")
                    .setEventTime(eventTime)
                    .setEffectObj(effectObj)
                    .setDes(des));
        }
        {
            LinkedHashMap<String, String> eventTime = new LinkedHashMap<>();
            eventTime.put("", "2018-04-23 17:17:01");
            LinkedHashMap<String, String> effectObj = new LinkedHashMap<>();
            effectObj.put("", "集群, K8s集群1");
            LinkedHashMap<String, String> des = new LinkedHashMap<>();
            des.put("", "创建集群成功；节点数量3");
            data.add(new EventBean().setName("创建集群")
                    .setLevel(0)
                    .setSource("13212345678, 王二小")
                    .setEventTime(eventTime)
                    .setEffectObj(effectObj)
                    .setDes(des));
        }
        mOperationAdapter.setDatas(data);
    }

    private void initSystemData() {
        List<EventBean> data = new ArrayList<>();
        {
            LinkedHashMap<String, String> eventTime = new LinkedHashMap<>();
            eventTime.put("", "2018-04-22 14:11:01");
            LinkedHashMap<String, String> effectObj = new LinkedHashMap<>();
            effectObj.put("", "主机, 阿里云, 47.75.155.218");
            LinkedHashMap<String, String> des = new LinkedHashMap<>();
            des.put("状态", "未处理");
            des.put("漏洞名称", "RHSA-2017:3075: wget security update");
            des.put("GNU", "Wget缓冲区溢出漏洞");
            des.put("建议", "生成修复命令 一键修复");
            data.add(new EventBean().setName("安全漏洞")
                    .setLevel(2)
                    .setSource("阿里云，云盾态势感知")
                    .setEventTime(eventTime)
                    .setEffectObj(effectObj)
                    .setDes(des));
        }
        {
            LinkedHashMap<String, String> eventTime = new LinkedHashMap<>();
            eventTime.put("", "2018-04-17 16:39:46");
            LinkedHashMap<String, String> effectObj = new LinkedHashMap<>();
            effectObj.put("", "主机, 阿里云, 47.75.159.100");
            LinkedHashMap<String, String> des = new LinkedHashMap<>();
            des.put("状态", "未处理");
            des.put("到期时间", "2018-05-17 16:39:46");
            des.put("需预交费用", "256.00元");
            data.add(new EventBean().setName("费用到期提醒")
                    .setLevel(1)
                    .setSource("阿里云，费用中心")
                    .setEventTime(eventTime)
                    .setEffectObj(effectObj)
                    .setDes(des));
        }
        mSystemAdapter.setDatas(data);
    }

    @OnClick({R.id.tv_filter, R.id.tv_docker_more, R.id.tv_operation_more, R.id.tv_system_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_filter: {

                break;
            }
            case R.id.tv_docker_more: {

                break;
            }
            case R.id.tv_operation_more: {

                break;
            }
            case R.id.tv_system_more: {

                break;
            }
        }
    }
}
