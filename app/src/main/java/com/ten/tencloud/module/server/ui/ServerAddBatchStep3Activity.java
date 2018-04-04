package com.ten.tencloud.module.server.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.ten.tencloud.R;
import com.ten.tencloud.TenApp;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.ServerBatchBean;
import com.ten.tencloud.module.server.adapter.RvServerSelectServerAdapter;

import java.util.List;

import butterknife.BindView;

public class ServerAddBatchStep3Activity extends BaseActivity {


    @BindView(R.id.rv_server)
    RecyclerView mRvServer;

    private List<ServerBatchBean> mServers;
    private RvServerSelectServerAdapter mServerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_server_add_batch_step3);
        initTitleBar(true, "批量添加云主机", "导入", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        String data = getIntent().getStringExtra("data");
        mServers = TenApp.getInstance().getGsonInstance().fromJson(data, new TypeToken<List<ServerBatchBean>>() {
        }.getType());
        initView();
        initData();
    }

    private void initView() {
        mRvServer.setLayoutManager(new LinearLayoutManager(this));
        mServerAdapter = new RvServerSelectServerAdapter(mContext);
        mRvServer.setAdapter(mServerAdapter);
    }

    private void initData() {
        mServerAdapter.setDatas(mServers);
    }
}
