package com.ten.tencloud.module.app.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.ExampleBean;
import com.ten.tencloud.bean.ServerBean;
import com.ten.tencloud.bean.ServicePortBean;
import com.ten.tencloud.constants.IntentKey;
import com.ten.tencloud.module.app.adapter.ServiceExampleAdapter;
import com.ten.tencloud.module.app.contract.AppServiceRulesContract;
import com.ten.tencloud.module.app.presenter.AppServiceRulesPresenter;
import com.ten.tencloud.module.image.ui.ImageAddActivity;

import java.util.List;

import butterknife.BindView;

/**
 * 查看实例标签
 */
public class AppServiceExampleActivity extends BaseActivity implements AppServiceRulesContract.View{

    @BindView(R.id.rv_cluster)
    RecyclerView mRvCluster;
    @BindView(R.id.tv_add_server)
    TextView mTvAddServer;
    @BindView(R.id.empty_view)
    FrameLayout mEmptyView;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;
    private ServiceExampleAdapter mServiceExampleAdapter;
    private AppServiceRulesPresenter mAppServiceRulesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.common_refresh_list);
        initTitleBar(true, "查看实例标签");
        mRvCluster.setLayoutManager(new LinearLayoutManager(this));
        mServiceExampleAdapter = new ServiceExampleAdapter();
        mRvCluster.setAdapter(mServiceExampleAdapter);
        mRefresh.setEnableRefresh(false);

        int appId = getIntent().getIntExtra(IntentKey.APP_ID, 0);

        mAppServiceRulesPresenter = new AppServiceRulesPresenter();
        mAppServiceRulesPresenter.attachView(this);

        mAppServiceRulesPresenter.podLabels(appId);

    }

    @Override
    public void showResult(Object o) {

    }

    @Override
    public void servicePortList(List<ServicePortBean> servicePortBeanList) {

    }

    @Override
    public void podLabels(List<ExampleBean> exampleBeans) {
        mServiceExampleAdapter.setNewData(exampleBeans);
    }

}