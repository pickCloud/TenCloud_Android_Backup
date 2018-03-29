package com.ten.tencloud.module.app.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.TaskBean;
import com.ten.tencloud.broadcast.RefreshBroadCastHandler;
import com.ten.tencloud.listener.OnRefreshListener;
import com.ten.tencloud.module.app.adapter.RvAppDetailTaskAdapter;
import com.ten.tencloud.widget.dialog.AppFilterDialog;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenxh@10.com on 2018/3/27.
 */
public class TaskListActivity extends BaseActivity {

    @BindView(R.id.rl_filter)
    RelativeLayout mRlFilter;
    @BindView(R.id.tv_filter)
    TextView mTvFilter;
    @BindView(R.id.rv_app)
    RecyclerView mRvApp;
    @BindView(R.id.tv_add_app)
    TextView mTvAddApp;
    @BindView(R.id.empty_view)
    FrameLayout mEmptyView;

    private RefreshBroadCastHandler mAppHandler;
    private RvAppDetailTaskAdapter mTaskAdapter;

    private AppFilterDialog mAppFilterDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_service_app_list);

        mRlFilter.setVisibility(View.GONE);
        initTitleBar(true, "任务列表");

        mAppHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.APP_LIST_CHANGE_ACTION);
        mAppHandler.registerReceiver(new OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });

        initView();
        initData();
    }

    private void initView() {
        mRvApp.setLayoutManager(new LinearLayoutManager(this));
        mTaskAdapter = new RvAppDetailTaskAdapter(this);
        mRvApp.setAdapter(mTaskAdapter);

        mAppFilterDialog = new AppFilterDialog(this);
        mAppFilterDialog.setAppFilterListener(new AppFilterDialog.AppFilterListener() {
            @Override
            public void getFilterData() {

            }

            @Override
            public void onOkClick(Map<String, Map<String, Boolean>> select) {

            }

        });
    }

    private void initData() {
        ArrayList<TaskBean> mTaskBeans = new ArrayList<>();
        mTaskBeans = new ArrayList<>();
        mTaskBeans.add(new TaskBean("构建镜像 - Djago v1.0.5","80%","2018-03-29  10:00:01","2018-03-29  10:00:11",0));
        mTaskBeans.add(new TaskBean("kubernests部署 - Djago v1.0.5","100%","2018-03-29  11:00:01","2018-03-29  11:00:11",1));
        mTaskBeans.add(new TaskBean("docker原生部署 - Djago v1.0.5","XX%","2018-03-29  12:00:01","2018-03-29  12:00:11",2));
        mTaskAdapter.setDatas(mTaskBeans);
    }

    @OnClick({R.id.tv_filter, R.id.tv_add_app})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_filter:
                mAppFilterDialog = new AppFilterDialog(this);
                mAppFilterDialog.show();
                break;
            case R.id.tv_add_app:
                startActivityNoValue(this, AppAddActivity.class);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAppHandler.unregisterReceiver();
        mAppHandler = null;
    }
}
