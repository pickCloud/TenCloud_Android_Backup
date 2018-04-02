package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.WareHouseBean;
import com.ten.tencloud.module.app.adapter.RvWareHouseBindAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by chenxh@10.com on 2018/3/26.
 */
public class RepositoryActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;

    private RvWareHouseBindAdapter mRvWareHouseBindAdapter;
    private String mWareHouseName;
    private String mWareHouseUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.layout_refresh);
        initTitleBar(true, "绑定github代码仓库", "确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("url", mWareHouseUrl);
                intent.putExtra("name", mWareHouseName);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        initView();
        initData();

    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRvWareHouseBindAdapter = new RvWareHouseBindAdapter(this);
        mRecyclerView.setAdapter(mRvWareHouseBindAdapter);
        mRvWareHouseBindAdapter.setOnItemClickListener(new CJSBaseRecyclerViewAdapter.OnItemClickListener<WareHouseBean>() {
            @Override
            public void onObjectItemClicked(WareHouseBean wareHouseBean, int position) {
                mRvWareHouseBindAdapter.setSelectPos(position);
                mWareHouseName = wareHouseBean.getName();
                mWareHouseUrl = wareHouseBean.getUrl();
            }
        });

        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

            }
        });
    }

    private void initData() {
        ArrayList<WareHouseBean> beans = new ArrayList<>();
        beans.add(new WareHouseBean("不绑定", null));
        for (int i = 0; i < 5; i++) {
            beans.add(new WareHouseBean("phyhhon-" + i, "https://github.com/AIUnicorn/TenCloud_Android" + i));
        }
        mRvWareHouseBindAdapter.setDatas(beans);
    }
}
