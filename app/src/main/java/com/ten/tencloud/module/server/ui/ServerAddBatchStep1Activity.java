package com.ten.tencloud.module.server.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.ServerProviderBean;
import com.ten.tencloud.module.server.adapter.RvServerSelectProviderAdapter;
import com.ten.tencloud.module.server.contract.ServerAddBatchStep1Contract;
import com.ten.tencloud.module.server.presenter.ServerAddBatchStep1Presenter;

import java.util.List;

import butterknife.BindView;

public class ServerAddBatchStep1Activity extends BaseActivity implements ServerAddBatchStep1Contract.View {

    @BindView(R.id.rv_provider)
    RecyclerView mRvProvider;
    private RvServerSelectProviderAdapter mProviderAdapter;
    private ServerAddBatchStep1Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_server_add_batch_step1);
        initTitleBar(true, "批量添加云主机", "下一步", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, ServerAddBatchStep2Activity.class));
            }
        });

        mPresenter = new ServerAddBatchStep1Presenter();
        mPresenter.attachView(this);

        initView();
        initData();
    }

    private void initView() {
        mRvProvider.setLayoutManager(new LinearLayoutManager(this));
        mProviderAdapter = new RvServerSelectProviderAdapter(this);
        mRvProvider.setAdapter(mProviderAdapter);
    }

    private void initData() {
        mPresenter.getServerProviders();
    }

    @Override
    public void showServerProviders(List<ServerProviderBean> providerBeans) {

    }
}
