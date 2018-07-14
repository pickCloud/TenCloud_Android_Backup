package com.ten.tencloud.module.app.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.bean.ServiceBean;
import com.ten.tencloud.broadcast.RefreshBroadCastHandler;
import com.ten.tencloud.listener.OnRefreshListener;
import com.ten.tencloud.module.app.adapter.RvAppServiceAdapter;
import com.ten.tencloud.widget.decoration.ServiceItemDecoration;
import com.ten.tencloud.widget.dialog.AppFilterDialog;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenxh@10.com on 2018/3/27.
 */
public class AppServiceListActivity extends BaseActivity {

    @BindView(R.id.tv_filter)
    TextView mTvFilter;
    @BindView(R.id.rv_app)
    RecyclerView mRvApp;
    @BindView(R.id.empty_view)
    FrameLayout mEmptyView;

    private RefreshBroadCastHandler mAppHandler;
    private ArrayList<AppBean> mAppBeans;
    private RvAppServiceAdapter mServiceAdapter;

    private AppFilterDialog mAppFilterDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_service_app_list);

        initTitleBar(true, "服务列表");

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
        mRvApp.addItemDecoration(new ServiceItemDecoration());
        mServiceAdapter = new RvAppServiceAdapter(this);
        mRvApp.setAdapter(mServiceAdapter);

//        mAppFilterDialog = new AppFilterDialog(this);
//        mAppFilterDialog.setAppFilterListener(new AppFilterDialog.AppFilterListener() {
//            @Override
//            public void getFilterData() {
//
//            }
//
//            @Override
//            public void onOkClick(Map<String, Map<String, Boolean>> select) {
//
//            }
//
//        });
    }

    private void initData() {
        ArrayList<ServiceBean> serviceBeans = new ArrayList<>();
        serviceBeans.add(new ServiceBean("service-example1", 1, "10.23.123.9", "<none>", "xxxx", "80/TCP，443/TCP", "2018-02-15  18:15:12", 0));
        serviceBeans.add(new ServiceBean("service-example2", 2, "10.23.123.9", "<none>", "xxxx", "80/TCP，443/TCP", "2018-02-16  18:15:12", 1));
        serviceBeans.add(new ServiceBean("service-example3", 3, "10.23.123.9", "<none>", "xxxx", "80/TCP，443/TCP", "2018-02-17  18:15:12", 2));
        mServiceAdapter.setDatas(serviceBeans);
    }

    @OnClick({R.id.tv_filter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_filter:
                mAppFilterDialog = new AppFilterDialog(this);
                mAppFilterDialog.show();
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
