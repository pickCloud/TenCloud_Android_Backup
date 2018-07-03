package com.ten.tencloud.module.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ten.tencloud.R;
import com.ten.tencloud.base.view.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Ingress详情
 */
public class IngressDetailsActivity extends BaseActivity {

    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_ip)
    TextView mTvIp;
    @BindView(R.id.tv_service)
    TextView mTvService;
    @BindView(R.id.tv_sub_app)
    TextView mTvSubApp;
    @BindView(R.id.rl_kzq)
    RelativeLayout mRlKzq;
    @BindView(R.id.tv_kzq_service)
    TextView mTvKzqService;
    @BindView(R.id.tv_kzq_sub_app)
    TextView mTvKzqSubApp;
    @BindView(R.id.tv_access_rule)
    TextView mTvAccessRule;
    @BindView(R.id.cv_kzq)
    CardView mCvKzq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_ingress_details);
        initTitleBar(true, "Ingress详情");

    }

    @OnClick(R.id.tv_rule_setting)
    public void onViewClicked() {
        Intent intent = new Intent(this, IngressModificationRuleActivity.class);
        startActivity(intent);

    }
}
