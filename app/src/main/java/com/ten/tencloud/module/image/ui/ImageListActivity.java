package com.ten.tencloud.module.image.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.ImageBean;
import com.ten.tencloud.module.image.adapter.RvImagesAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ImageListActivity extends BaseActivity {

    @BindView(R.id.rv_image)
    RecyclerView mRvImage;
    @BindView(R.id.empty_view)
    View mEmptyView;

    private RvImagesAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_image_list);
        initTitleBar(true, "私有镜像列表", R.menu.menu_add_image, new OnMenuItemClickListener() {
            @Override
            public void onItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_add_image) {
                    startActivityNoValue(mContext, ImageAddActivity.class);
                }
            }
        });
        initView();
        initData();
    }

    private void initView() {
        mRvImage.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RvImagesAdapter(this);
        mAdapter.setOnItemClickListener(new CJSBaseRecyclerViewAdapter.OnItemClickListener<ImageBean>() {
            @Override
            public void onObjectItemClicked(ImageBean imageBean, int position) {
                Intent intent = new Intent(mContext, ImageDetailActivity.class);
                intent.putExtra("name", imageBean.getName());
                intent.putExtra("logo", imageBean.getLogo_url());
                intent.putExtra("label", imageBean.getLabel_name());
                intent.putExtra("des", imageBean.getDescription());
                intent.putExtra("type", imageBean.getType());
                startActivity(intent);
            }
        });
        mRvImage.setAdapter(mAdapter);
    }

    private void initData() {
        List<ImageBean> datas = new ArrayList<>();
        datas.add(new ImageBean("Nginx", "V1.13.12", 1, "2018-04-01 12:01:23", "Server"));
        datas.add(new ImageBean("Redis", "V3.2.11", 1, "2018-04-03 11:11:43", "数据库"));
        datas.add(new ImageBean("Ubuntu", "18.04", 1, "2018-04-6 09:21:40", "操作系统"));
        datas.add(new ImageBean("MongoDB", "V3.7.7", 1, "2018-04-7 13:31:13", "数据库"));
        datas.add(new ImageBean("Dao-2048", "V1.0.3", 0, "2018-04-08 17:12:43", "游戏"));
        datas.add(new ImageBean("GoLang", "V1.10.1", 1, "2018-04-09 01:21:15", "编程语言"));
        datas.add(new ImageBean("Python", "V3.7.0b3", 1, "2018-04-09 04:11:52", "编程语言"));
        mAdapter.setDatas(datas);
        mEmptyView.setVisibility(View.GONE);
    }
}
