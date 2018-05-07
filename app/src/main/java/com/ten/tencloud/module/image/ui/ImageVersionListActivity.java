package com.ten.tencloud.module.image.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.ImageVersionBean;
import com.ten.tencloud.module.image.adapter.RvImageVersionAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ImageVersionListActivity extends BaseActivity {

    @BindView(R.id.rv_version)
    RecyclerView mRvVersion;
    private int mType;
    private RvImageVersionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_image_version_list);
        mType = getIntent().getIntExtra("type", 0);
        initView();
        initData();
    }

    private void initView() {
        mRvVersion.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RvImageVersionAdapter(mContext, mType);
        mRvVersion.setAdapter(mAdapter);
    }

    private void initData() {
        List<ImageVersionBean> datas = new ArrayList<>();
        datas.add(new ImageVersionBean("V3.0.2", "2018-03-16 15:23:13"));
        datas.add(new ImageVersionBean("V3.0.1", "2018-03-14 11:21:11"));
        datas.add(new ImageVersionBean("V2.1.1", "2018-02-23 01:33:33"));
        datas.add(new ImageVersionBean("V2.1.0", "2018-02-16 15:23:13"));
        datas.add(new ImageVersionBean("V1.0.1", "2018-02-11 19:23:10"));
        datas.add(new ImageVersionBean("V0.9.1", "2018-02-08 15:23:13"));
        datas.add(new ImageVersionBean("V0.8.0", "2018-02-05 21:13:43"));
        mAdapter.setDatas(datas);
    }
}
