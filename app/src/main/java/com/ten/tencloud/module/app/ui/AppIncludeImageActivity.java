package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.bean.ImageBean;
import com.ten.tencloud.constants.Constants;
import com.ten.tencloud.module.app.adapter.RvAppIncludeImageAdapter;
import com.ten.tencloud.module.app.contract.AppImageContract;
import com.ten.tencloud.module.app.presenter.AppImagePresenter;

import java.util.List;

import butterknife.BindView;

public class AppIncludeImageActivity extends BaseActivity implements AppImageContract.View {

    @BindView(R.id.rv_image)
    RecyclerView mRvImage;
    @BindView(R.id.empty_view)
    View mEmptyView;
    private AppImagePresenter mAppImagePresenter;
    private RvAppIncludeImageAdapter mIncludeImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_app_include_image);
        initTitleBar(true, "镜像仓库", "插入", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                includeAndFinish();
            }
        });

        mAppImagePresenter = new AppImagePresenter();
        mAppImagePresenter.attachView(this);

        initView();
        initData();

    }

    private void includeAndFinish() {
        ImageBean selectObj = mIncludeImageAdapter.getSelectObj();
        if (selectObj == null) {
            showMessage("请选择镜像");
            return;
        }
        Intent data = new Intent();
        data.putExtra("imageName", selectObj.getName());
        data.putExtra("imageVersion", selectObj.getVersion());
        setResult(Constants.ACTIVITY_RESULT_CODE_FINISH, data);
        finish();
    }

    private void initView() {
        mRvImage.setLayoutManager(new LinearLayoutManager(this));
        mIncludeImageAdapter = new RvAppIncludeImageAdapter(this);
        mRvImage.setAdapter(mIncludeImageAdapter);
    }

    private void initData() {
        mAppImagePresenter.getAppImageById(null);
    }

    @Override
    public void showImages(List<ImageBean> images) {
        mIncludeImageAdapter.setDatas(images);
        mRvImage.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showImageEmpty() {
        mRvImage.setVisibility(View.INVISIBLE);
        mEmptyView.setVisibility(View.VISIBLE);
    }
}
