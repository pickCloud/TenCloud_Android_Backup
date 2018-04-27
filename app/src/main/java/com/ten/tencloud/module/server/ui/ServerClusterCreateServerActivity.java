package com.ten.tencloud.module.server.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.ServerBean;
import com.ten.tencloud.module.server.adapter.RvServerClusterCreateServerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ServerClusterCreateServerActivity extends BaseActivity {

    @BindView(R.id.rv_server)
    RecyclerView mRvServer;
    private RvServerClusterCreateServerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_server_cluster_create_server);
        initTitleBar(true, "选择主机", "确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initView();
        initData();
    }

    private void initView() {
        mRvServer.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RvServerClusterCreateServerAdapter(this);
        mRvServer.setAdapter(mAdapter);
    }

    private void initData() {
        List<ServerBean> datas = new ArrayList<>();
        datas.add(new ServerBean("@测试1", "54.202.148.94", "阿里云"));
        datas.add(new ServerBean("@测试2", "54.203.148.94", "腾讯云"));
        datas.add(new ServerBean("@测试3", "54.204.148.94", "微软云"));
        datas.add(new ServerBean("@测试4", "54.205.148.94", "亚马逊云"));
        datas.add(new ServerBean("@测试5", "54.206.148.94", "阿里云"));
        mAdapter.setDatas(datas);
    }

}
