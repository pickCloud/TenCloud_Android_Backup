package com.ten.tencloud.module.image.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;
import com.ten.tencloud.utils.glide.GlideUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class ImageDetailActivity extends BaseActivity {

    @BindView(R.id.iv_logo)
    ImageView mIvLogo;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.tv_description)
    TextView mTvDes;
    @BindView(R.id.btn_create_app)
    Button mBtnCreateApp;
    @BindView(R.id.btn_update_version)
    Button mBtnUpdateVersion;
    @BindView(R.id.fbl_label)
    FlexboxLayout mFblLabel;
    private String mName;
    private String mLogo;
    private String mLabel;
    private String mDes;
    private int mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_image_detail);
        initTitleBar(true, "镜像详情");
        mName = getIntent().getStringExtra("name");
        mLogo = getIntent().getStringExtra("logo");
        mLabel = getIntent().getStringExtra("label");
        mDes = getIntent().getStringExtra("des");
        mType = getIntent().getIntExtra("type", 0);
        initView();
        initData();
    }

    private void initView() {
        mBtnCreateApp.setVisibility(mType == 0 ? View.GONE : View.VISIBLE);
        mBtnUpdateVersion.setVisibility(mType == 0 ? View.GONE : View.VISIBLE);
        mFblLabel.removeAllViews();
    }

    private void initData() {
        mTvName.setText(mName);
        mTvDes.setText(mDes);
        mTvType.setText(mType == 0 ? "内部应用" : "外部应用");
        mTvName.setText(mName);
        GlideUtils.getInstance().loadCircleImage(this, mIvLogo, mLogo, R.mipmap.icon_mirror_photo);

        if (!TextUtils.isEmpty(mLabel)) {
            String[] labels = mLabel.split(",");
            for (String labelBean : labels) {
                View labelView = getLayoutInflater().inflate(R.layout.item_app_service_label, null, false);
                ((TextView) labelView.findViewById(R.id.tv_label_name)).setText(labelBean);
                mFblLabel.addView(labelView);
            }
        }
    }

    @OnClick({R.id.btn_version_list, R.id.btn_create_app})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_version_list:
                Intent intent = new Intent(this, ImageVersionListActivity.class);
                intent.putExtra("type", mType);
                startActivity(intent);
                break;
            case R.id.btn_create_app:

                break;
        }
    }
}
