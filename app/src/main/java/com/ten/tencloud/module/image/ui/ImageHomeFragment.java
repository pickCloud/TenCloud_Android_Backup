package com.ten.tencloud.module.image.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.adapter.CJSBaseRecyclerViewAdapter;
import com.ten.tencloud.base.view.BaseFragment;
import com.ten.tencloud.bean.ImageBean;
import com.ten.tencloud.module.image.adapter.RvImagesAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lxq on 2018/4/16.
 */

public class ImageHomeFragment extends BaseFragment {

    @BindView(R.id.tv_image_private_count)
    TextView mTvImagePrivateCount;
    @BindView(R.id.tv_image_shop_count)
    TextView mTvImageShopCount;
    @BindView(R.id.empty_view)
    View mEmptyView;
    @BindView(R.id.rv_image)
    RecyclerView mRvImage;

    private RvImagesAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return createView(inflater, container, R.layout.fragment_image_home);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView();
        initData();
    }

    private void initView() {
        mRvImage.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new RvImagesAdapter(mActivity);
        mAdapter.setOnItemClickListener(new CJSBaseRecyclerViewAdapter.OnItemClickListener<ImageBean>() {
            @Override
            public void onObjectItemClicked(ImageBean imageBean, int position) {
                Intent intent = new Intent(mActivity, ImageDetailActivity.class);
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
        mAdapter.setDatas(datas);
        mEmptyView.setVisibility(View.GONE);
    }

    @OnClick({R.id.rl_image_private, R.id.rl_image_shop, R.id.tv_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_image_private:
            case R.id.tv_more:
                startActivity(new Intent(mActivity, ImageListActivity.class));
                break;
            case R.id.rl_image_shop:

                break;
        }
    }
}
