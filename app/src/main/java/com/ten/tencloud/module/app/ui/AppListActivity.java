package com.ten.tencloud.module.app.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.AppBean;
import com.ten.tencloud.broadcast.RefreshBroadCastHandler;
import com.ten.tencloud.listener.OnRefreshListener;
import com.ten.tencloud.module.app.adapter.RvAppAdapter;
import com.ten.tencloud.widget.dialog.AppFilterDialog;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenxh@10.com on 2018/3/27.
 */
public class AppListActivity extends BaseActivity {

    @BindView(R.id.tv_filter)
    TextView mTvFilter;
    @BindView(R.id.rv_app)
    RecyclerView mRvApp;
    @BindView(R.id.tv_add_app)
    TextView mTvAddApp;
    @BindView(R.id.empty_view)
    FrameLayout mEmptyView;

    private RefreshBroadCastHandler mAppHandler;
    private ArrayList<AppBean> mAppBeans;
    private RvAppAdapter mRvAppAdapter;

    private AppFilterDialog mAppFilterDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_service_app_list);

        initTitleBar(true, "应用列表", R.menu.menu_add_app, new OnMenuItemClickListener() {
            @Override
            public void onItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_add_app:
                        startActivityNoValue(mContext, AppAddActivity.class);
                        break;
                }
            }
        });

        mAppHandler = new RefreshBroadCastHandler(RefreshBroadCastHandler.SERVER_LIST_CHANGE_ACTION);
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
        mRvAppAdapter = new RvAppAdapter(this);
        mRvApp.setAdapter(mRvAppAdapter);

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
        mAppBeans = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            mAppBeans.add(new AppBean());
        }
        mRvAppAdapter.setDatas(mAppBeans);
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
