package com.ten.tencloud.module.server.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.module.server.adapter.RvServerClusterCreateTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ServerClusterCreateTypeActivity extends BaseActivity {

    @BindView(R.id.rv_type)
    RecyclerView mRvType;
    private RvServerClusterCreateTypeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_server_cluster_create_type);
        initTitleBar(true, "选择类型", "确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectObj = mAdapter.getSelectObj();
                if (TextUtils.isEmpty(selectObj)) {
                    showMessage("请选择类型");
                    return;
                }
                Intent data = new Intent();
                data.putExtra("type", selectObj);
                setResult(Constants.ACTIVITY_RESULT_CODE_FINISH, data);
                finish();
            }
        });
        initView();
        initData();
    }

    private void initView() {
        mRvType.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RvServerClusterCreateTypeAdapter(this);
        mRvType.setAdapter(mAdapter);
    }

    private void initData() {
        List<String> data = new ArrayList<>();
        data.add("高可用");
        data.add("超级计算能力");
        data.add("Kubernetes集群");
        mAdapter.addData(data);
    }
}
