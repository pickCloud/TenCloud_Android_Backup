package com.ten.tencloud.module.app.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.ImageBean;
import com.ten.tencloud.module.app.adapter.RvAppDetailImageAdapter1;
import com.ten.tencloud.module.app.contract.AppImageContract;
import com.ten.tencloud.module.app.presenter.AppImagePresenter;
import com.ten.tencloud.widget.dialog.AppFilterDialog;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenxh@10.com on 2018/3/27.
 */
public class AppImageListActivity extends BaseActivity implements AppImageContract.View {

    @BindView(R.id.rl_filter)
    RelativeLayout mRlFilter;
    @BindView(R.id.tv_filter)
    TextView mTvFilter;
    @BindView(R.id.rv_app)
    RecyclerView mRvApp;
    @BindView(R.id.empty_view)
    FrameLayout mEmptyView;

    private RvAppDetailImageAdapter1 mImageAdapter;

    private AppFilterDialog mAppFilterDialog;
    private int mAppId;
    private AppImagePresenter mAppImagePresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_service_app_list);

        mRlFilter.setVisibility(View.GONE);
        initTitleBar(true, "镜像列表");

        mAppId = getIntent().getIntExtra("appId", -1);
        mAppImagePresenter = new AppImagePresenter();
        mAppImagePresenter.attachView(this);

        initView();
        initData();
    }

    private void initView() {
        mRvApp.setLayoutManager(new LinearLayoutManager(this));
        mImageAdapter = new RvAppDetailImageAdapter1(this);
        mRvApp.setAdapter(mImageAdapter);

//        mAppFilterDialog = new AppFilterDialog(this);
//        mAppFilterDialog.setAppFilterListener(new AppFilterDialog.AppFilterListener() {
//            @Override
//            public void getFilterData() {
//
//            }
//
//            @Override
//            public void onOkClick(Map<String, Map<String, Boolean>> select) {
//
//            }
//
//        });
    }

    private void initData() {
        mAppImagePresenter.getAppImageById(mAppId + "");
    }

    @OnClick({R.id.tv_filter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_filter:
                mAppFilterDialog = new AppFilterDialog(this);
                mAppFilterDialog.show();
                break;
        }
    }

    @Override
    public void showImages(List<ImageBean> images) {
        mEmptyView.setVisibility(View.GONE);
        mRvApp.setVisibility(View.VISIBLE);
        mImageAdapter.setDatas(images);
    }

    @Override
    public void showImageEmpty() {
        mEmptyView.setVisibility(View.VISIBLE);
        mRvApp.setVisibility(View.GONE);
    }
}
